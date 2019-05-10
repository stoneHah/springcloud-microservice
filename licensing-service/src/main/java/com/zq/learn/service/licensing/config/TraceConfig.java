package com.zq.learn.service.licensing.config;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qun.zheng
 * @description: TODO
 * @date 2019/4/30下午4:39
 */
//@Configur ation
public class TraceConfig {

    @Bean
    Sampler sleuthTraceSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
