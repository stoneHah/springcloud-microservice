##Spring Cloud Ribbon全解析

要实现http负载均衡，我们只需要在RestTemplate上加上@LoadBalanced就能实现负载均衡了，感觉很magic

### @LoadBalanced是如何启动负载均衡的？

​    通过搜索@LoadBalanced在哪边使用了(idea快捷键alt+f7)，最终可以定位到类`LoadBalancerAutoConfiguration`

```java
@Configuration
@ConditionalOnClass(RestTemplate.class)
@ConditionalOnBean(LoadBalancerClient.class)
@EnableConfigurationProperties(LoadBalancerRetryProperties.class)
public class LoadBalancerAutoConfiguration {

  /**
  ** @LoadBalanced充当了限定符的作用，@see @Qualifier
  ** 所以当我们在RestTemplate中申明了@LoadBalanced就会被注入到此
  */
	@LoadBalanced
	@Autowired(required = false)
	private List<RestTemplate> restTemplates = Collections.emptyList();

  /**
	** SmartInitializingSingleton bean会在容器启动完后调用afterSingletonsInstantiated()
	** AbstractApplicationContext.refresh()  (org.springframework.context.support)
	** 		-> finishBeanFactoryInitialization()
	**				->DefaultListableBeanFactory.preInstantiateSingletons()
							  ->SmartInitializingSingleton.afterSingletonsInstantiated()  
	**/
	@Bean
	public SmartInitializingSingleton loadBalancedRestTemplateInitializerDeprecated(
			final ObjectProvider<List<RestTemplateCustomizer>> restTemplateCustomizers) {
		return () -> restTemplateCustomizers.ifAvailable(customizers -> {
            for (RestTemplate restTemplate : LoadBalancerAutoConfiguration.this.restTemplates) {
                for (RestTemplateCustomizer customizer : customizers) {
                    customizer.customize(restTemplate);
                }
            }
        });
	}
  
}
```

​		@LoadBalanced充当了限定符的作用(通过元数据注解@Qualifier实现)，我们自定义的RestTemplate Bean只要加上@LoadBalanced 就会被注入到LoadBalancerAutoConfiguration.restTemplates中。接下来就通过RestTemplateCustomizer来定制化RestTemplate使其具有负载均衡功能(通过添加拦截器的方式)，再次膜拜下设计之精妙:pray:

```java
@Configuration
	@ConditionalOnMissingClass("org.springframework.retry.support.RetryTemplate")
	static class LoadBalancerInterceptorConfig {
		@Bean
		public LoadBalancerInterceptor ribbonInterceptor(
				LoadBalancerClient loadBalancerClient,
				LoadBalancerRequestFactory requestFactory) {
			return new LoadBalancerInterceptor(loadBalancerClient, requestFactory);
		}

		@Bean
		@ConditionalOnMissingBean
		public RestTemplateCustomizer restTemplateCustomizer(
				final LoadBalancerInterceptor loadBalancerInterceptor) {
			return restTemplate -> {
                List<ClientHttpRequestInterceptor> list = new ArrayList<>(
                        restTemplate.getInterceptors());
                list.add(loadBalancerInterceptor);
                restTemplate.setInterceptors(list);
            };
		}
	}
```

### 服务名是如何转换为ip:port 的形式的

```sequence
RestTemplate->LoadBalancerInterceptor: intercept()进行拦截处理
LoadBalancerInterceptor->LoadBalancerClient: execute()
LoadBalancerRequestFactory->LoadBalancerRequestFactory: createRequest()
LoadBalancerRequestFactory-->LoadBalancerClient: 返回LoadBalancerRequest
LoadBalancerClient->LoadBalancerRequest:apply(ServiceInstance)在这里进行服务名的转换
LoadBalancerRequest-->LoadBalancerClient:返回ClientHttpResponse
LoadBalancerClient-->LoadBalancerInterceptor:返回ClientHttpResponse
LoadBalancerInterceptor-->RestTemplate:返回ClientHttpResponse
```



让我们把焦点聚集在LoadBalancerRequestFactory.createRequest()中，可以看到其中new 了一个ServiceRequestWrapper()对象来对HttpRequest进行装饰.在getURI()方法出进行的重写

```java
public class LoadBalancerRequestFactory {

	public LoadBalancerRequest<ClientHttpResponse> createRequest(final HttpRequest request,
			final byte[] body, final ClientHttpRequestExecution execution) {
		return instance -> {
      			//使用装饰器模式对HttpRequest进行装饰
            HttpRequest serviceRequest = new ServiceRequestWrapper(request, instance, loadBalancer);
            if (transformers != null) {
                for (LoadBalancerRequestTransformer transformer : transformers) {
                    serviceRequest = transformer.transformRequest(serviceRequest, instance);
                }
            }
            return execution.execute(serviceRequest, body);
        };
	}

}

public class ServiceRequestWrapper extends HttpRequestWrapper {
	private final ServiceInstance instance;
	private final LoadBalancerClient loadBalancer;

	public ServiceRequestWrapper(HttpRequest request, ServiceInstance instance,
								 LoadBalancerClient loadBalancer) {
		super(request);
		this.instance = instance;
		this.loadBalancer = loadBalancer;
	}

	@Override
	public URI getURI() {
		URI uri = this.loadBalancer.reconstructURI(
				this.instance, getRequest().getURI());
		return uri;
	}
}
```



### 服务名转换为ip:port 过程中涉及到哪些负载均衡组件

### ribbon是如何获取注册中心的服务的



