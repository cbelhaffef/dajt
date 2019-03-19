package com.cbelhaffef.dajt.api.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "role", path = "/roles")
public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(@Param("code") RoleCodeEnum roleCode);
}
