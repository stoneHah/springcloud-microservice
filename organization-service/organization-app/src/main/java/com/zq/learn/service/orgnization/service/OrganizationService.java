package com.zq.learn.service.orgnization.service;

import com.zq.learn.service.orgnization.api.entity.Organization;
import com.zq.learn.service.orgnization.event.source.SimpleSourceBean;
import com.zq.learn.service.orgnization.repository.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId).orElse(null);
    }

    public void saveOrg(Organization org){
        org.setId( UUID.randomUUID().toString());

        orgRepository.save(org);

        simpleSourceBean.publishOrgChange("SAVE", org.getId());

    }

    public void updateOrg(Organization org){
        orgRepository.save(org);

        simpleSourceBean.publishOrgChange("UPDATE", org.getId());
    }

    public void deleteOrg(Organization org){
        orgRepository.deleteById( org.getId());

        simpleSourceBean.publishOrgChange("DELETE", org.getId());
    }
}
