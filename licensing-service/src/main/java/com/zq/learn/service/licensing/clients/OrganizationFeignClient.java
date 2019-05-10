package com.zq.learn.service.licensing.clients;


import com.zq.learn.service.orgnization.api.OrganizationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("organizationservice")
public interface OrganizationFeignClient extends OrganizationApi{
}
