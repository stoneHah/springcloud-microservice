package com.zq.learn.service.orgnization.provider;

import com.zq.learn.service.orgnization.api.OrganizationApi;
import com.zq.learn.service.orgnization.api.entity.Organization;
import com.zq.learn.service.orgnization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationApiProvider implements OrganizationApi {

    @Autowired
    private OrganizationService organizationService;

    @Override
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        return organizationService.getOrg(organizationId);
    }
}
