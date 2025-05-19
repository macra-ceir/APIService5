package com.gl.mdr.controller;

import com.gl.mdr.model.app.DuplicateDeviceDetail;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.filter.DuplicateDeviceFilterRequest;
import com.gl.mdr.service.impl.DuplicateDeviceServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DuplicateDeviceController {
	private static final Logger logger = LogManager.getLogger(DuplicateDeviceController.class);

	@Autowired
	DuplicateDeviceServiceImpl duplicateDeviceServiceImpl;

	@ApiOperation(value = "get list of duplicate devices", response = DuplicateDeviceDetail.class)
	@PostMapping("/getDuplicateDeviceDetails")
	public MappingJacksonValue getDuplicateDevicesDetails(@RequestBody DuplicateDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Duplicate Device filter request:["+filterRequest.toString()+"]");
		Page<DuplicateDeviceDetail> duplicateDataResponse =  duplicateDeviceServiceImpl.getDuplicateDevicesDetails(filterRequest, pageNo, pageSize,"View");
		mapping = new MappingJacksonValue(duplicateDataResponse);
		return mapping;
	}

	
	@ApiOperation(value = "Export duplicate devices", response = DuplicateDeviceDetail.class)
	@PostMapping("/exportDuplicateData")
	public MappingJacksonValue exportData(@RequestBody DuplicateDeviceFilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		MappingJacksonValue mapping = null;
		logger.info("Duplicate export request:["+filterRequest.toString()+"]");
		FileDetails fileDetails = duplicateDeviceServiceImpl.exportData(filterRequest);
		mapping = new MappingJacksonValue(fileDetails);
		logger.info("Duplicate export response:["+fileDetails.toString()+"]");
		return mapping;
	}
	
	
	@ApiOperation(value="get Approved Device")
	@PostMapping("/getApprovedDeviceData")
	public ResponseEntity<?> getApprovedDeviceData(@RequestBody DuplicateDeviceFilterRequest duplicateRequest ){
		return duplicateDeviceServiceImpl.viewApprovedDevice(duplicateRequest);
	}
	
	
	@ApiOperation(value="Approve Device" , response = DuplicateDeviceDetail.class)
	@PostMapping("/approveDuplicateDevice")
	public ResponseEntity<?> approveDuplicateDevice(@RequestBody DuplicateDeviceFilterRequest duplicateRequest) {
		logger.info("in approveDuplicateDevice Controller with request : "+duplicateRequest);
	    return duplicateDeviceServiceImpl.update(duplicateRequest);

	}
	
	
}
