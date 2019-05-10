package com.zq.learn.service.orgnization.api;


import com.zq.learn.service.orgnization.api.entity.Organization;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/api/v1/organizations")
public interface OrganizationApi {


    @GetMapping(value="/{organizationId}")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);


}
