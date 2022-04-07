package com.company.repository;

import com.company.entity.BaseEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>,JpaSpecificationExecutor<ProfileEntity> {
}
