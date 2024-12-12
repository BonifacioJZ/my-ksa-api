package com.bonifacio.my_ksa_api.persistence.reporsitory;

import com.bonifacio.my_ksa_api.persistence.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, UUID> {
    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
