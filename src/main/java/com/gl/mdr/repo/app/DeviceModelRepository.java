package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.DeviceModel;

@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Integer>{
	
	public DeviceModel save(DeviceModel deviceModel);
	
	public DeviceModel findByBrandNameIdAndModelName(Integer brandNameId, String modelName);

}
