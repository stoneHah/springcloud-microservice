package com.zq.learn.service.orgnization.controller;


import com.zq.learn.service.orgnization.api.entity.Organization;
import com.zq.learn.service.orgnization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping(value="v1/organizations")
public class OrganizationServiceController {
    @Autowired
    private OrganizationService orgService;


    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId,
                                        HttpServletRequest request) {
        log.info("get Organization from organizationId={}", organizationId);
        /*Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            log.info("headName={},headValue={}",headName,request.getHeader(headName));
        }*/

        return orgService.getOrg(organizationId);
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        orgService.updateOrg( org );
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
       orgService.saveOrg( org );
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("orgId") String orgId, @RequestBody Organization org) {
        orgService.deleteOrg( org );
    }
}
