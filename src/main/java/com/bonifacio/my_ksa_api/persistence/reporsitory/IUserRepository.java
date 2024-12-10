package com.bonifacio.my_ksa_api.persistence.reporsitory;

import com.bonifacio.my_ksa_api.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
}