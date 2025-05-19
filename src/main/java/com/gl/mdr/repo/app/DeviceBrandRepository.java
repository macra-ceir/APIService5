package com.gl.mdr.repo.app;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.DeviceBrand;

@Repository
public interface DeviceBrandRepository extends JpaRepository<DeviceBrand, Integer>{
	
	public DeviceBrand save(DeviceBrand deviceBrand);
	
	public List<DeviceBrand> findByBrandNameIn(Set<String> brandName);
	
	public DeviceBrand findByBrandName(String brandName);

}
