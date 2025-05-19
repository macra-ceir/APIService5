package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.MobileDeviceRepositoryNoFile;

@Repository
public interface MobileDeviceRepoNoFileRepository extends 
JpaRepository<MobileDeviceRepositoryNoFile, Long>, JpaSpecificationExecutor<MobileDeviceRepositoryNoFile>{
	
}
