package com.zq.learn.service.orgnization.repository;

import com.zq.learn.service.orgnization.api.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String> {
}
