package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.MobileDeviceRepository;
import com.gl.mdr.model.generic.DashboardData;
import com.gl.mdr.model.generic.UserDetails;

@Repository
public interface MobileDeviceRepoRepository extends 
JpaRepository<MobileDeviceRepository, Long>, JpaSpecificationExecutor<MobileDeviceRepository>{
	
	public MobileDeviceRepository save(MobileDeviceRepository mobileDeviceRepository);
	public MobileDeviceRepository getByDeviceId(String deviceId);
	public MobileDeviceRepository findByDeviceIdAndDeviceStateNot(String deviceId, Integer deviceState);
	public MobileDeviceRepository getById(Integer id);
	
//	@Query(value="select new com.gl.mdr.model.generic.DashboardData(count(m.id) as totalDevices, "
//			+ "SUM(CASE WHEN m.deviceState=0 THEN 1 ELSE 0 END) as newDevices, SUM(CASE WHEN m.deviceState=1 THEN 1 ELSE 0 END)"
//			+ " as updatedDevices) from MobileDeviceRepository m where m.deviceState!=2") 
//	public DashboardData getDashboardData();
	@Query(value="select new com.gl.mdr.model.generic.DashboardData(count(m.id) as totalDevices, "
			+ "SUM(CASE WHEN m.deviceState=0 THEN 1 ELSE 0 END) as newDevices, SUM(CASE WHEN m.deviceState=1 THEN 1 ELSE 0 END)"
			+ " as updatedDevices, SUM(CASE WHEN m.deviceState=3 THEN 1 ELSE 0 END) as completedDevices) from MobileDeviceRepository m where m.deviceState!=2") 
	public DashboardData getDashboardData();
	
	
	@Query(value="SELECT DISTINCT u.user_id FROM app.mobile_device_repository u",nativeQuery = true)
	public List<String> findDistinctUserId();
	
	@Query(value="SELECT DISTINCT u.device_type FROM app.mobile_device_repository u where u.device_type<>''",nativeQuery = true)
	public List<String> findDistinctDeviceType();
	
	
	
	
}
