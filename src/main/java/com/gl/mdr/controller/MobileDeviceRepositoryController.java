package com.gl.mdr.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.mdr.model.app.MobileDeviceRepository;
import com.gl.mdr.model.oam.MobileDeviceRepositoryHistory;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.filter.MobileDeviceRepositoryFilterRequest;
import com.gl.mdr.model.filter.RepositoryHistoryFilterRequest;
import com.gl.mdr.model.generic.DashboardData;
import com.gl.mdr.model.generic.MDRGenricResponse;
import com.gl.mdr.model.generic.UserDetails;
import com.gl.mdr.service.impl.MobileDeviceRepositoryServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
public class MobileDeviceRepositoryController {

	private static final Logger logger = LogManager.getLogger(MobileDeviceRepositoryController.class);

	@Autowired
	MobileDeviceRepositoryServiceImpl mdrServiceImpl;
	
	@ApiOperation(value = "get list of devices with details", response = MobileDeviceRepository.class)
	@PostMapping("/getDevicesDetails")
	public MappingJacksonValue getDevicesDetails(@RequestBody MobileDeviceRepositoryFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("MDR filter request:["+filterRequest.toString()+"]");
		Page<MobileDeviceRepository>  mdrs =  mdrServiceImpl.getDevicesDetails(filterRequest, pageNo, pageSize);
		mapping = new MappingJacksonValue(mdrs);
		return mapping;
	}
	
	@ApiOperation(value = "Add a device in Mobile Device Repository", response = MDRGenricResponse.class)
	@RequestMapping(path = "/addDevice", method = {RequestMethod.POST})
	public MDRGenricResponse addDevice(@RequestBody MobileDeviceRepository deviceInfo) {
		MDRGenricResponse genricResponse = mdrServiceImpl.addDevice(deviceInfo);
		return genricResponse;
	}
	
	@ApiOperation(value = "Update devices in Mobile Device Repository", response = MDRGenricResponse.class)
	@RequestMapping(path = "/updateDevices", method = {RequestMethod.POST})
	public MDRGenricResponse updateDevices(@RequestBody List<MobileDeviceRepository> devicesInfo) {
		MDRGenricResponse genricResponse = mdrServiceImpl.updateDevices(devicesInfo);
		return genricResponse;
	}
	
	@ApiOperation(value = "Delete a device in Mobile Device Repository", response = MDRGenricResponse.class)
	@RequestMapping(path = "/deleteDevice", method = {RequestMethod.POST})
	public MDRGenricResponse deleteDevice(@RequestParam("userId") Integer userId,
			@RequestParam("deviceId") String deviceId,
			@RequestParam(value = "remark", defaultValue="NA") String remark,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value = "featureId", defaultValue = "0") Integer featureId
			) {
		MDRGenricResponse genricResponse = mdrServiceImpl.deleteDevice(deviceId, remark, publicIp,
				browser, userId, userType, userTypeId, featureId);
		return genricResponse;
	}
	
	@ApiOperation(value = "Get a single device details", response = MobileDeviceRepository.class)
	@RequestMapping(path = "/getDeviceInfo", method = {RequestMethod.POST})
	public MappingJacksonValue getDeviceInfo(
			@RequestParam("userId") Integer userId,
			@RequestParam("deviceId") String deviceId,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value = "featureId", defaultValue = "0") Integer featureId
			) {
		MappingJacksonValue mapping = null;
		MobileDeviceRepositoryFilterRequest filterRequest = new MobileDeviceRepositoryFilterRequest();
		filterRequest.setUserId(userId);
		filterRequest.setDeviceId(deviceId);
		filterRequest.setPublicIp(publicIp);
		filterRequest.setBrowser(browser);
		filterRequest.setUserType(userType);
		filterRequest.setUserTypeId(userTypeId);
		filterRequest.setFeatureId(featureId);
		MobileDeviceRepository  mdr =  (MobileDeviceRepository) mdrServiceImpl.getDevicesDetails(filterRequest, 0, 10).getContent().get(0);
		mapping = new MappingJacksonValue(mdr);
		return mapping;
	}
	
	@ApiOperation(value = "View history of device", response = MobileDeviceRepositoryHistory.class)
	@RequestMapping(path = "/getDeviceHistory", method = {RequestMethod.POST})
	public MappingJacksonValue getDevieHistory(@RequestBody RepositoryHistoryFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "50") Integer pageSize) {
		MappingJacksonValue mapping = null;
		Page<MobileDeviceRepositoryHistory> mdrs = mdrServiceImpl.getFilterMDRHistory(filterRequest, pageNo, pageSize);
		mapping = new MappingJacksonValue(mdrs);
		return mapping;
	}
	
	@ApiOperation(value = "Get a device update history details", response = MobileDeviceRepositoryHistory.class)
	@RequestMapping(path = "/getDeviceHistoryInfo", method = {RequestMethod.POST})
	public MappingJacksonValue getDeviceHistoryInfo(
			@RequestParam("userId") Integer userId,
			@RequestParam("deviceId") String deviceId,
			@RequestParam("rowId") Integer id,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value = "featureId", defaultValue = "0") Integer featureId
			) {
		MappingJacksonValue mapping = null;
		RepositoryHistoryFilterRequest filterRequest = new RepositoryHistoryFilterRequest();
		filterRequest.setUserId(userId);
		filterRequest.setId(id);
		filterRequest.setDeviceId(deviceId);
		filterRequest.setPublicIp(publicIp);
		filterRequest.setBrowser(browser);
		filterRequest.setUserType(userType);
		filterRequest.setUserTypeId(userTypeId);
		filterRequest.setFeatureId(featureId);
		MobileDeviceRepositoryHistory  mdr =  (MobileDeviceRepositoryHistory) mdrServiceImpl.getFilterMDRHistoryRowInfo(filterRequest);
		mapping = new MappingJacksonValue(mdr);
		return mapping;
	}
	
	@ApiOperation(value = "Export filtered devices", response = MobileDeviceRepository.class)
	@PostMapping("/exportData")
	public MappingJacksonValue exportData(@RequestBody MobileDeviceRepositoryFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("MDR export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = mdrServiceImpl.exportData(filterRequest, pageNo, pageSize);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("MDR export response:["+fileDetails.toString()+"]");
		return mapping;
	}
	
	@ApiOperation(value = "Get dashboard data", response = DashboardData.class)
	@RequestMapping(path = "/getMDRDashboardData", method = {RequestMethod.GET})
	public MappingJacksonValue getMDRDashboardData(
			@RequestParam("userId") Integer userId,
			@RequestParam(value = "publicIp", defaultValue="NA") String publicIp,
			@RequestParam(value = "browser", defaultValue="NA") String browser,
			@RequestParam(value = "userType", defaultValue="NA") String userType,
			@RequestParam(value = "userTypeId", defaultValue = "0") Integer userTypeId,
			@RequestParam(value = "featureId", defaultValue = "0") Integer featureId
			) {
		MappingJacksonValue response = new MappingJacksonValue(mdrServiceImpl.getMDRDashboardData(publicIp, browser,
				userId, userType, userTypeId, featureId));
		return response;
	}
	
	@ApiOperation(value = "get User Details")
	@GetMapping("/getDistinctUserName")
	public ResponseEntity<?> getDistinctUserName() {
		List<UserDetails>  list = mdrServiceImpl.getUserData();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "get User Device Type")
	@GetMapping("/getDistinctDeviceType")
	public ResponseEntity<?> getDistinctDeviceType() {
		List<String>  list = mdrServiceImpl.getDeviceType();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}