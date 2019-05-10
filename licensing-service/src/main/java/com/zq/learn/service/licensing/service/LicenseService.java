package com.zq.learn.service.licensing.service;

import com.zq.learn.service.licensing.clients.OrganizationDiscoveryClient;
import com.zq.learn.service.licensing.clients.OrganizationFeignClient;
import com.zq.learn.service.licensing.clients.OrganizationRestTemplateClient;
import com.zq.learn.service.licensing.config.ServiceConfig;
import com.zq.learn.service.licensing.model.License;
import com.zq.learn.service.licensing.repository.LicenseRepository;
import com.zq.learn.service.orgnization.api.entity.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(String organizationId){
        return licenseRepository.findByOrganizationId( organizationId );
    }

    public void saveLicense(License license){
        license.withId( UUID.randomUUID().toString());

        licenseRepository.save(license);

    }

    public License getLicense(String organizationId,String licenseId, String clientType) {
        log.info("call getLicense method,organizationId={},licenseId={},clientType={}", organizationId,
                licenseId, clientType);

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName( org.getName())
                .withContactName( org.getContactName())
                .withContactEmail( org.getContactEmail() )
                .withContactPhone( org.getContactPhone() )
                .withComment(config.getExampleProperty());
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType){
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    public void updateLicense(License license){
      licenseRepository.save(license);
    }

    public void deleteLicense(License license){
        licenseRepository.deleteById( license.getLicenseId());
    }

}
