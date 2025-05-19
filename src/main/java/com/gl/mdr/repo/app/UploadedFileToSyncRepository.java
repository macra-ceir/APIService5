package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.UploadedFileToSync;


@Repository
public interface UploadedFileToSyncRepository extends JpaRepository< UploadedFileToSync, Integer>,
JpaSpecificationExecutor<UploadedFileToSync> {
	
}
