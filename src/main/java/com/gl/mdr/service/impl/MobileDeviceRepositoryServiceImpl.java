package com.gl.mdr.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.app.AttachedFileInfo;
import com.gl.mdr.model.app.MobileDeviceRepository;
import com.gl.mdr.model.app.MobileDeviceRepositoryNoFile;
import com.gl.mdr.model.app.StatesInterpretationDb;
import com.gl.mdr.model.app.SystemConfigListDb;
import com.gl.mdr.model.app.UploadedFileToSync;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.oam.MobileDeviceRepositoryHistory;
import com.gl.mdr.repo.app.AttachedFileInfoRepository;
import com.gl.mdr.repo.app.MobileDeviceRepoNoFileRepository;
import com.gl.mdr.repo.app.MobileDeviceRepoRepository;
import com.gl.mdr.repo.app.StatesInterpretaionRepository;
import com.gl.mdr.repo.app.SystemConfigListRepository;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.gl.mdr.repo.app.UploadedFileToSyncRepository;
import com.gl.mdr.repo.app.UserProfileRepository;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.repo.oam.MobileDeviceRepoHistoryRepository;
import com.gl.mdr.model.generic.DashboardData;
import com.gl.mdr.model.generic.MDRGenricResponse;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.model.generic.UserDetails;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.OrderColumnMapping;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.constants.Tags;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.file.MobileDeviceRepoFileModel;
import com.gl.mdr.model.filter.MobileDeviceRepositoryFilterRequest;
import com.gl.mdr.model.filter.RepositoryHistoryFilterRequest;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.CustomMappingStrategy;
import com.gl.mdr.util.Utility;

@Service
public class MobileDeviceRepositoryServiceImpl<T>{

	private static final Logger logger = LogManager.getLogger(MobileDeviceRepositoryServiceImpl.class);
	
	@Autowired
	MobileDeviceRepoRepository mdrRepository;
	
	@Autowired
	MobileDeviceRepoNoFileRepository mdrNoFileRepository;
	
	@Autowired
	MobileDeviceRepoHistoryRepository mdrHistoryRepository;
	
	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;
	
	@Autowired
	AttachedFileInfoRepository attachedFileInfoRepository;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	StatesInterpretaionRepository statesInterpretaionRepository;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	SystemConfigListRepository systemConfigListRepository;
	
	@Autowired
	Utility utility;
	
	@Autowired
	ModelsServiceImpl modelService;
	
	@Autowired
	BrandServiceImpl brandService;
	
//	@Autowired
//	UploadedFileToSyncRepository fileSyncRepository;
	
	@Transactional
	public MDRGenricResponse addDevice(MobileDeviceRepository mdr) {
		List<SystemConfigListDb> testIMEIList = null;
		List<StatesInterpretationDb> states = null;
		MobileDeviceRepository newMDR = null;
		String txnId = null;
		User user  = null;
		int status = 0;
		int deleteStatus = 0;
		int completedStatus = 0;
		String fileUploadPath = null;
		try {
			fileUploadPath = systemConfigurationDbRepository.getByTag("user_upload_filepath").getValue();
			states = statesInterpretaionRepository.findByFeatureId( mdr.getFeatureId() );
            
			for( StatesInterpretationDb state : states ) {
            	if( state.getInterpretation().trim().equalsIgnoreCase(Tags.NEW))
            		status = state.getState();
            	else if( state.getInterpretation().trim().equalsIgnoreCase(Tags.DELETED))
            		deleteStatus = state.getState();
            	else if( state.getInterpretation().trim().equalsIgnoreCase(Tags.COMPLETED))
            		completedStatus = state.getState();
            }
			
            if(mdr.getDeviceId().equals("0")) {
            	logger.info("Device Id is missing.");
            	return new MDRGenricResponse(500,"Device Id is missing.",
    					(long)0);
            }
            newMDR = mdrRepository.getByDeviceId(mdr.getDeviceId());
            
            
            if(Objects.nonNull(newMDR) && !newMDR.getDeviceState().equals(deleteStatus)) {
            	logger.info("Request to add new device ["+mdr.getDeviceId()+"] already exist.");
            	return new MDRGenricResponse(500,"Device already exists",
    					(long)0);
            }
            logger.info("Add new device request:["+mdr.toString()+"]");
			testIMEIList = systemConfigListRepository.findByTag("TEST_IMEI_SERIES");
            
			if( Objects.isNull(newMDR)) {
				newMDR = new MobileDeviceRepository();
				for(SystemConfigListDb series: testIMEIList) {
	            	if(mdr.getDeviceId().startsWith(series.getInterpretation())) {
	            		newMDR.setIsTestImei(1);
	            		break;
	            	}
	            }
			} else {
				if(Objects.nonNull(newMDR.getAttachedFiles())) {
					newMDR.getAttachedFiles().clear();
				}
			}
			
			newMDR.setDeviceState(mdr.getDeviceState());
			//newMDR.setDeviceState(status);
			
            newMDR.setDeviceId(mdr.getDeviceId());
            
            
            if(Objects.nonNull(mdr.getNetworkSpecificIdentifier()))
				newMDR.setNetworkSpecificIdentifier(mdr.getNetworkSpecificIdentifier());
            
			if(Objects.nonNull(mdr.getMarketingName()))
				newMDR.setMarketingName(mdr.getMarketingName());
			
			if(Objects.nonNull(mdr.getManufacturer()))
				newMDR.setManufacturer(mdr.getManufacturer());
			
			if(Objects.nonNull(mdr.getManufacturingLocation()))
				newMDR.setManufacturingLocation(mdr.getManufacturingLocation());
			
			if(Objects.nonNull(mdr.getBrandName()))
				newMDR.setBrandName(mdr.getBrandName().trim());
			
			if(Objects.nonNull(mdr.getModelName()))
				newMDR.setModelName(mdr.getModelName().trim());
			
			if(Objects.nonNull(mdr.getOem()))
				newMDR.setOem(mdr.getOem());
			
			if(Objects.nonNull(mdr.getOrganizationId()))
				newMDR.setOrganizationId(mdr.getOrganizationId());
			
			if(Objects.nonNull(mdr.getDeviceType()))
				newMDR.setDeviceType(mdr.getDeviceType());
			
			if(Objects.nonNull(mdr.getAllocationDate()))
				newMDR.setAllocationDate(mdr.getAllocationDate());
			
			if(Objects.nonNull(mdr.getImeiQuantity()))
				newMDR.setImeiQuantity(mdr.getImeiQuantity());
			
			if(Objects.nonNull(mdr.getSimSlot()))
				newMDR.setSimSlot(mdr.getSimSlot());
			
			if(Objects.nonNull(mdr.getEsimSupport()))
				newMDR.setEsimSupport(mdr.getEsimSupport());
			
			if(Objects.nonNull(mdr.getSoftsimSupport()))
				newMDR.setSoftsimSupport(mdr.getSoftsimSupport());
			
			if(Objects.nonNull(mdr.getSimType()))
				newMDR.setSimType(mdr.getSimType());
			
			if(Objects.nonNull(mdr.getOs()))
				newMDR.setOs(mdr.getOs());
			
			if(Objects.nonNull(mdr.getOsCurrentVersion()))
				newMDR.setOsCurrentVersion(mdr.getOsCurrentVersion());
			
			if(Objects.nonNull(mdr.getOsBaseVersion()))
				newMDR.setOsBaseVersion(mdr.getOsBaseVersion());
			
			if(Objects.nonNull(mdr.getAnnounceDate()))
				newMDR.setAnnounceDate(mdr.getAnnounceDate());
			
			if(Objects.nonNull(mdr.getLaunchDate()))
				newMDR.setLaunchDate(mdr.getLaunchDate());
			
			if(Objects.nonNull(mdr.getDeviceStatus()))
				newMDR.setDeviceStatus(mdr.getDeviceStatus());
			
			if(Objects.nonNull(mdr.getDiscontinueDate()))
				newMDR.setDiscontinueDate(mdr.getDiscontinueDate());
			
			if(Objects.nonNull(mdr.getNetworkTechnologyGSM()))
				newMDR.setNetworkTechnologyGSM(mdr.getNetworkTechnologyGSM());
			
			if(Objects.nonNull(mdr.getNetworkTechnologyCDMA()))
				newMDR.setNetworkTechnologyCDMA(mdr.getNetworkTechnologyCDMA());
			
			if(Objects.nonNull(mdr.getNetworkTechnologyEVDO()))
				newMDR.setNetworkTechnologyEVDO(mdr.getNetworkTechnologyEVDO());
			
			if(Objects.nonNull(mdr.getNetworkTechnologyLTE()))
				newMDR.setNetworkTechnologyLTE(mdr.getNetworkTechnologyLTE());
			
			if(Objects.nonNull(mdr.getNetworkTechnology5G()))
				newMDR.setNetworkTechnology5G(mdr.getNetworkTechnology5G());
			
			if(Objects.nonNull(mdr.getNetworkTechnology6G()))
				newMDR.setNetworkTechnology6G(mdr.getNetworkTechnology6G());
			
			if(Objects.nonNull(mdr.getNetworkTechnology7G()))
				newMDR.setNetworkTechnology7G(mdr.getNetworkTechnology7G());
			
			if(Objects.nonNull(mdr.getNetwork2GBand()))
				newMDR.setNetwork2GBand(mdr.getNetwork2GBand());
			
			if(Objects.nonNull(mdr.getNetwork3GBand()))
				newMDR.setNetwork3GBand(mdr.getNetwork3GBand());
			
			if(Objects.nonNull(mdr.getNetwork4GBand()))
				newMDR.setNetwork4GBand(mdr.getNetwork4GBand());
			
			if(Objects.nonNull(mdr.getNetwork5GBand()))
				newMDR.setNetwork5GBand(mdr.getNetwork5GBand());
			
			if(Objects.nonNull(mdr.getNetwork6GBand()))
				newMDR.setNetwork6GBand(mdr.getNetwork6GBand());
			
			if(Objects.nonNull(mdr.getNetwork7GBand()))
				newMDR.setNetwork7GBand(mdr.getNetwork7GBand());
			
			if(Objects.nonNull(mdr.getNetworkSpeed()))
				newMDR.setNetworkSpeed(mdr.getNetworkSpeed());
			
			if(Objects.nonNull(mdr.getBodyDimension()))
				newMDR.setBodyDimension(mdr.getBodyDimension());
			
			if(Objects.nonNull(mdr.getBodyWeight()))
				newMDR.setBodyWeight(mdr.getBodyWeight());
			
			if(Objects.nonNull(mdr.getDisplayType()))
				newMDR.setDisplayType(mdr.getDisplayType());
			
			if(Objects.nonNull(mdr.getDisplaySize()))
				newMDR.setDisplaySize(mdr.getDisplaySize());
			
			if(Objects.nonNull(mdr.getDisplayResolution()))
				newMDR.setDisplayResolution(mdr.getDisplayResolution());
			
			if(Objects.nonNull(mdr.getDisplayProtection()))
				newMDR.setDisplayProtection(mdr.getDisplayProtection());
			
			if(Objects.nonNull(mdr.getPlatformChipset()))
				newMDR.setPlatformChipset(mdr.getPlatformChipset());
			
			if(Objects.nonNull(mdr.getPlatformCPU()))
				newMDR.setPlatformCPU(mdr.getPlatformCPU());
			
			if(Objects.nonNull(mdr.getPlatformGPU()))
				newMDR.setPlatformGPU(mdr.getPlatformGPU());
			
			if(Objects.nonNull(mdr.getMemoryCardSlot()))
				newMDR.setMemoryCardSlot(mdr.getMemoryCardSlot());
			
			if(Objects.nonNull(mdr.getMemoryInternal()))
				newMDR.setMemoryInternal(mdr.getMemoryInternal());
			
			if(Objects.nonNull(mdr.getRam()))
				newMDR.setRam(mdr.getRam());
			
			if(Objects.nonNull(mdr.getMainCameraType()))
				newMDR.setMainCameraType(mdr.getMainCameraType());
			
			if(Objects.nonNull(mdr.getMainCameraSpec()))
				newMDR.setMainCameraSpec(mdr.getMainCameraSpec());
			
			if(Objects.nonNull(mdr.getMainCameraFeature()))
				newMDR.setMainCameraFeature(mdr.getMainCameraFeature());
			
			if(Objects.nonNull(mdr.getMainCameraVideo()))
				newMDR.setMainCameraVideo(mdr.getMainCameraVideo());
			
			if(Objects.nonNull(mdr.getSelfieCameraType()))
				newMDR.setSelfieCameraType(mdr.getSelfieCameraType());
			
			if(Objects.nonNull(mdr.getSelfieCameraSpec()))
				newMDR.setSelfieCameraSpec(mdr.getSelfieCameraSpec());
			
			if(Objects.nonNull(mdr.getSelfieCameraFeature()))
				newMDR.setSelfieCameraFeature(mdr.getSelfieCameraFeature());
			
			if(Objects.nonNull(mdr.getSelfieCameraVideo()))
				newMDR.setSelfieCameraVideo(mdr.getSelfieCameraVideo());
			
			if(Objects.nonNull(mdr.getSoundLoudspeaker()))
				newMDR.setSoundLoudspeaker(mdr.getSoundLoudspeaker());
			
			if(Objects.nonNull(mdr.getSound35mmJack()))
				newMDR.setSound35mmJack(mdr.getSound35mmJack());
			
			if(Objects.nonNull(mdr.getCommsWLAN()))
				newMDR.setCommsWLAN(mdr.getCommsWLAN());
			
			if(Objects.nonNull(mdr.getCommsBluetooth()))
				newMDR.setCommsBluetooth(mdr.getCommsBluetooth());
			
			if(Objects.nonNull(mdr.getCommsGPS()))
				newMDR.setCommsGPS(mdr.getCommsGPS());
			
			if(Objects.nonNull(mdr.getCommsNFC()))
				newMDR.setCommsNFC(mdr.getCommsNFC());
			
			if(Objects.nonNull(mdr.getCommsRadio()))
				newMDR.setCommsRadio(mdr.getCommsRadio());
			
			if(Objects.nonNull(mdr.getCommsUSB()))
				newMDR.setCommsUSB(mdr.getCommsUSB());
			
			if(Objects.nonNull(mdr.getSensor()))
				newMDR.setSensor(mdr.getSensor());
			
			if(Objects.nonNull(mdr.getBatteryType()))
				newMDR.setBatteryType(mdr.getBatteryType());
			
			if(Objects.nonNull(mdr.getBatteryCapacity()))
				newMDR.setBatteryCapacity(mdr.getBatteryCapacity());
			
			if(Objects.nonNull(mdr.getBatteryCharging()))
				newMDR.setBatteryCharging(mdr.getBatteryCharging());
			
			if(Objects.nonNull(mdr.getColors()))
				newMDR.setColors(mdr.getColors());
			
			if(Objects.nonNull(mdr.getRemovableUICC()))
				newMDR.setRemovableUICC(mdr.getRemovableUICC());
			
			if(Objects.nonNull(mdr.getRemovableEUICC()))
				newMDR.setRemovableEUICC(mdr.getRemovableEUICC());
			
			if(Objects.nonNull(mdr.getNonremovableUICC()))
				newMDR.setNonremovableUICC(mdr.getNonremovableUICC());
			
			if(Objects.nonNull(mdr.getNonremovableEUICC()))
				newMDR.setNonremovableEUICC(mdr.getNonremovableEUICC());
			
			if(Objects.nonNull(mdr.getBandDetail()))
				newMDR.setBandDetail(mdr.getBandDetail());
			
			if(Objects.nonNull(mdr.getLaunchPriceAsianMarket()))
				newMDR.setLaunchPriceAsianMarket(mdr.getLaunchPriceAsianMarket());
			
			if(Objects.nonNull(mdr.getLaunchPriceUSMarket()))
				newMDR.setLaunchPriceUSMarket(mdr.getLaunchPriceUSMarket());
			
			if(Objects.nonNull(mdr.getLaunchPriceEuropeMarket()))
				newMDR.setLaunchPriceEuropeMarket(mdr.getLaunchPriceEuropeMarket());
			
			if(Objects.nonNull(mdr.getLaunchPriceInternationalMarket()))
				newMDR.setLaunchPriceInternationalMarket(mdr.getLaunchPriceInternationalMarket());
			
			if(Objects.nonNull(mdr.getLaunchPriceCambodiaMarket()))
				newMDR.setLaunchPriceCambodiaMarket(mdr.getLaunchPriceCambodiaMarket());
			
			if(Objects.nonNull(mdr.getCustomPriceOfDevice()))
				newMDR.setCustomPriceOfDevice(mdr.getCustomPriceOfDevice());
			
			if(Objects.nonNull(mdr.getSourceOfCambodiaMarketPrice()))
				newMDR.setSourceOfCambodiaMarketPrice(mdr.getSourceOfCambodiaMarketPrice());
			
			if(Objects.nonNull(mdr.getReportedGlobalIssue()))
				newMDR.setReportedGlobalIssue(mdr.getReportedGlobalIssue());
			
			if(Objects.nonNull(mdr.getReportedLocalIssue()))
				newMDR.setReportedLocalIssue(mdr.getReportedLocalIssue());
			
			if(Objects.nonNull(mdr.getUserId()))
				newMDR.setUserId(mdr.getUserId());
			
			if(Objects.nonNull(mdr.getRemark()))
				newMDR.setRemark(mdr.getRemark());
			
			if(Objects.nonNull(mdr.getNetworkSpecificIdentifier()))
				newMDR.setNetworkSpecificIdentifier(mdr.getNetworkSpecificIdentifier());
			
			logger.info("Going to save new device :["+newMDR.toString()+"]");
			//Save new device
			newMDR = mdrRepository.save(newMDR);
			//Save history of device
			mdrHistoryRepository.save(new MobileDeviceRepositoryHistory(newMDR));
			
			if(Objects.nonNull(mdr.getAttachedFiles())) {
				fileUploadPath = fileUploadPath+newMDR.getDeviceId()+"/MDR/";
				List<AttachedFileInfo> attachedFiles = mdr.getAttachedFiles();
//				List<UploadedFileToSync> fileSyncList = new ArrayList<UploadedFileToSync>();
				for(AttachedFileInfo file: attachedFiles) {
					file.setMdrId(newMDR.getId());
//					fileSyncList.add(new UploadedFileToSync(file.getFileName(), fileUploadPath,
//							propertiesReader.serverId, newMDR.getDeviceId()));
				}
				attachedFileInfoRepository.saveAll(attachedFiles);
//				fileSyncRepository.saveAll(fileSyncList);
			}
			
			if(Objects.nonNull(newMDR.getBrandName()) && newMDR.getBrandName() != "")
				modelService.saveBrandModel(newMDR.getBrandName(), newMDR.getModelName());
			
			txnId = mdr.getDeviceId();
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Mobile Device Repository");
			auditTrail.setSubFeature("Created");
			auditTrail.setFeatureId( mdr.getFeatureId());
			if( Objects.nonNull(mdr.getPublicIp()))
				auditTrail.setPublicIp(mdr.getPublicIp());
			if( Objects.nonNull(mdr.getBrowser()))
				auditTrail.setBrowser(mdr.getBrowser());
			
			user = userRepository.getByid(mdr.getUserId());
			auditTrail.setUserType(user.getUsertype().getUsertypeName());
			auditTrail.setRoleType(user.getUsertype().getUsertypeName());
			auditTrail.setUserId(user.getId());
			auditTrail.setUserName(user.getUsername());
			auditTrail.setTxnId(txnId);
			logger.info("Audit trail:["+auditTrail.toString()+"]");
			auditTrailRepository.save(auditTrail);
			return new MDRGenricResponse(0,"Device information successfuly saved in Mobile Device Repository",
					(long)newMDR.getId());

		}catch (Exception e) {
			logger.error("Mobile Device Repository addition failed="+e.getMessage());
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	// Revisit update method
	@Transactional
	public MDRGenricResponse updateDevices(List<MobileDeviceRepository> mdrs) {
		HashMap<String, String> modelBrandMap = new HashMap<String, String>();
//		List<UploadedFileToSync> uploadFileList = null;
		Set<String> brands = new HashSet<String>();
		List<StatesInterpretationDb> states = null;
		MobileDeviceRepository newMDR = null;
		AttachedFileInfo attachedFile = null;
		String brandName = "";
		String modelName = "";
		String txnId = "";
		User user  = null;
		int status = 0;
		try {
//			states = statesInterpretaionRepository.findByFeatureId(mdrs.get(0).getFeatureId());
//			for( StatesInterpretationDb state : states ) {
//            	if( state.getInterpretation().trim().equalsIgnoreCase(Tags.UPDATED)) {
//            		status = state.getState();
//            		break;
//            	}
//            	if( state.getInterpretation().trim().equalsIgnoreCase(Tags.COMPLETED)) {
//            		status = state.getState();
//            		break;
//            	}
//            }
			
			user = userRepository.getByid( mdrs.get(0).getUserId());
			for(MobileDeviceRepository mdr : mdrs) {
				brandName = "";
				modelName = "";
				logger.info("MDR id:"+mdr.getId()+", and Device Id:"+mdr.getDeviceId());
				logger.info("Request to update mdr:["+mdr.toString()+"]");
				if(Objects.isNull(mdr.getId()) && (Objects.isNull(mdr.getDeviceId()) || mdr.getDeviceId().equals("0")))
	            	return new MDRGenricResponse(500,"Device Id is missing.",
	    					(long)0);
				if(Objects.nonNull(mdr.getId()))
					newMDR = mdrRepository.getById(mdr.getId());
				else
					newMDR = mdrRepository.getByDeviceId(mdr.getDeviceId());
		
				//newMDR.setDeviceState(status);
				newMDR.setDeviceState(mdr.getDeviceState());
				newMDR.setCreatedOn(newMDR.getCreatedOn());
				newMDR.setModifiedOn(LocalDateTime.now());
				
				if(Objects.nonNull(mdr.getMarketingName()))
					newMDR.setMarketingName(mdr.getMarketingName());
				
				if(Objects.nonNull(mdr.getManufacturer()))
					newMDR.setManufacturer(mdr.getManufacturer());
				
				if(Objects.nonNull(mdr.getManufacturingLocation()))
					newMDR.setManufacturingLocation(mdr.getManufacturingLocation());
				
				if(Objects.nonNull(mdr.getBrandName())) {
					brandName = mdr.getBrandName().trim();
					newMDR.setBrandName(brandName);
					if( brandName != "")
						brands.add(brandName);
				}
				
				if(Objects.nonNull(mdr.getModelName())) {
					modelName = mdr.getModelName().trim();
					newMDR.setModelName(modelName);
					if( brandName != "" && modelName != "")
						modelBrandMap.put(modelName, brandName);
				}
				
				if(Objects.nonNull(mdr.getOem()))
					newMDR.setOem(mdr.getOem());
				
				if(Objects.nonNull(mdr.getOrganizationId()))
					newMDR.setOrganizationId(mdr.getOrganizationId());
				
				if(Objects.nonNull(mdr.getDeviceType()))
					newMDR.setDeviceType(mdr.getDeviceType());
				
				if(Objects.nonNull(mdr.getAllocationDate()))
					newMDR.setAllocationDate(mdr.getAllocationDate());
				
				if(Objects.nonNull(mdr.getImeiQuantity()))
					newMDR.setImeiQuantity(mdr.getImeiQuantity());
				
				if(Objects.nonNull(mdr.getSimSlot()))
					newMDR.setSimSlot(mdr.getSimSlot());
				
				if(Objects.nonNull(mdr.getEsimSupport()))
					newMDR.setEsimSupport(mdr.getEsimSupport());
				
				if(Objects.nonNull(mdr.getSoftsimSupport()))
					newMDR.setSoftsimSupport(mdr.getSoftsimSupport());
				
				if(Objects.nonNull(mdr.getSimType()))
					newMDR.setSimType(mdr.getSimType());
				
				if(Objects.nonNull(mdr.getOs()))
					newMDR.setOs(mdr.getOs());
				
				if(Objects.nonNull(mdr.getOsCurrentVersion()))
					newMDR.setOsCurrentVersion(mdr.getOsCurrentVersion());
				
				if(Objects.nonNull(mdr.getOsBaseVersion()))
					newMDR.setOsBaseVersion(mdr.getOsBaseVersion());
				
				if(Objects.nonNull(mdr.getAnnounceDate()))
					newMDR.setAnnounceDate(mdr.getAnnounceDate());
				
				if(Objects.nonNull(mdr.getLaunchDate()))
					newMDR.setLaunchDate(mdr.getLaunchDate());
				
				if(Objects.nonNull(mdr.getDeviceStatus()))
					newMDR.setDeviceStatus(mdr.getDeviceStatus());
				
				if(Objects.nonNull(mdr.getDiscontinueDate()))
					newMDR.setDiscontinueDate(mdr.getDiscontinueDate());
				
				if(Objects.nonNull(mdr.getNetworkTechnologyGSM()))
					newMDR.setNetworkTechnologyGSM(mdr.getNetworkTechnologyGSM());
				
				if(Objects.nonNull(mdr.getNetworkTechnologyCDMA()))
					newMDR.setNetworkTechnologyCDMA(mdr.getNetworkTechnologyCDMA());
				
				if(Objects.nonNull(mdr.getNetworkTechnologyEVDO()))
					newMDR.setNetworkTechnologyEVDO(mdr.getNetworkTechnologyEVDO());
				
				if(Objects.nonNull(mdr.getNetworkTechnologyLTE()))
					newMDR.setNetworkTechnologyLTE(mdr.getNetworkTechnologyLTE());
				
				if(Objects.nonNull(mdr.getNetworkTechnology5G()))
					newMDR.setNetworkTechnology5G(mdr.getNetworkTechnology5G());
				
				if(Objects.nonNull(mdr.getNetworkTechnology6G()))
					newMDR.setNetworkTechnology6G(mdr.getNetworkTechnology6G());
				
				if(Objects.nonNull(mdr.getNetworkTechnology7G()))
					newMDR.setNetworkTechnology7G(mdr.getNetworkTechnology7G());
				
				if(Objects.nonNull(mdr.getNetwork2GBand()))
					newMDR.setNetwork2GBand(mdr.getNetwork2GBand());
				
				if(Objects.nonNull(mdr.getNetwork3GBand()))
					newMDR.setNetwork3GBand(mdr.getNetwork3GBand());
				
				if(Objects.nonNull(mdr.getNetwork4GBand()))
					newMDR.setNetwork4GBand(mdr.getNetwork4GBand());
				
				if(Objects.nonNull(mdr.getNetwork5GBand()))
					newMDR.setNetwork5GBand(mdr.getNetwork5GBand());
				
				if(Objects.nonNull(mdr.getNetwork6GBand()))
					newMDR.setNetwork6GBand(mdr.getNetwork6GBand());
				
				if(Objects.nonNull(mdr.getNetwork7GBand()))
					newMDR.setNetwork7GBand(mdr.getNetwork7GBand());
				
				if(Objects.nonNull(mdr.getNetworkSpeed()))
					newMDR.setNetworkSpeed(mdr.getNetworkSpeed());
				
				if(Objects.nonNull(mdr.getBodyDimension()))
					newMDR.setBodyDimension(mdr.getBodyDimension());
				
				if(Objects.nonNull(mdr.getBodyWeight()))
					newMDR.setBodyWeight(mdr.getBodyWeight());
				
				if(Objects.nonNull(mdr.getDisplayType()))
					newMDR.setDisplayType(mdr.getDisplayType());
				
				if(Objects.nonNull(mdr.getDisplaySize()))
					newMDR.setDisplaySize(mdr.getDisplaySize());
				
				if(Objects.nonNull(mdr.getDisplayResolution()))
					newMDR.setDisplayResolution(mdr.getDisplayResolution());
				
				if(Objects.nonNull(mdr.getDisplayProtection()))
					newMDR.setDisplayProtection(mdr.getDisplayProtection());
				
				if(Objects.nonNull(mdr.getPlatformChipset()))
					newMDR.setPlatformChipset(mdr.getPlatformChipset());
				
				if(Objects.nonNull(mdr.getPlatformCPU()))
					newMDR.setPlatformCPU(mdr.getPlatformCPU());
				
				if(Objects.nonNull(mdr.getPlatformGPU()))
					newMDR.setPlatformGPU(mdr.getPlatformGPU());
				
				if(Objects.nonNull(mdr.getMemoryCardSlot()))
					newMDR.setMemoryCardSlot(mdr.getMemoryCardSlot());
				
				if(Objects.nonNull(mdr.getMemoryInternal()))
					newMDR.setMemoryInternal(mdr.getMemoryInternal());
				
				if(Objects.nonNull(mdr.getRam()))
					newMDR.setRam(mdr.getRam());
				
				if(Objects.nonNull(mdr.getMainCameraType()))
					newMDR.setMainCameraType(mdr.getMainCameraType());
				
				if(Objects.nonNull(mdr.getMainCameraSpec()))
					newMDR.setMainCameraSpec(mdr.getMainCameraSpec());
				
				if(Objects.nonNull(mdr.getMainCameraFeature()))
					newMDR.setMainCameraFeature(mdr.getMainCameraFeature());
				
				if(Objects.nonNull(mdr.getMainCameraVideo()))
					newMDR.setMainCameraVideo(mdr.getMainCameraVideo());
				
				if(Objects.nonNull(mdr.getSelfieCameraType()))
					newMDR.setSelfieCameraType(mdr.getSelfieCameraType());
				
				if(Objects.nonNull(mdr.getSelfieCameraSpec()))
					newMDR.setSelfieCameraSpec(mdr.getSelfieCameraSpec());
				
				if(Objects.nonNull(mdr.getSelfieCameraFeature()))
					newMDR.setSelfieCameraFeature(mdr.getSelfieCameraFeature());
				
				if(Objects.nonNull(mdr.getSelfieCameraVideo()))
					newMDR.setSelfieCameraVideo(mdr.getSelfieCameraVideo());
				
				if(Objects.nonNull(mdr.getSoundLoudspeaker()))
					newMDR.setSoundLoudspeaker(mdr.getSoundLoudspeaker());
				
				if(Objects.nonNull(mdr.getSound35mmJack()))
					newMDR.setSound35mmJack(mdr.getSound35mmJack());
				
				if(Objects.nonNull(mdr.getCommsWLAN()))
					newMDR.setCommsWLAN(mdr.getCommsWLAN());
				
				if(Objects.nonNull(mdr.getCommsBluetooth()))
					newMDR.setCommsBluetooth(mdr.getCommsBluetooth());
				
				if(Objects.nonNull(mdr.getCommsGPS()))
					newMDR.setCommsGPS(mdr.getCommsGPS());
				
				if(Objects.nonNull(mdr.getCommsNFC()))
					newMDR.setCommsNFC(mdr.getCommsNFC());
				
				if(Objects.nonNull(mdr.getCommsRadio()))
					newMDR.setCommsRadio(mdr.getCommsRadio());
				
				if(Objects.nonNull(mdr.getCommsUSB()))
					newMDR.setCommsUSB(mdr.getCommsUSB());
				
				if(Objects.nonNull(mdr.getSensor()))
					newMDR.setSensor(mdr.getSensor());
				
				if(Objects.nonNull(mdr.getBatteryType()))
					newMDR.setBatteryType(mdr.getBatteryType());
				
				if(Objects.nonNull(mdr.getBatteryCapacity()))
					newMDR.setBatteryCapacity(mdr.getBatteryCapacity());
				
				if(Objects.nonNull(mdr.getBatteryCharging()))
					newMDR.setBatteryCharging(mdr.getBatteryCharging());
				
				if(Objects.nonNull(mdr.getColors()))
					newMDR.setColors(mdr.getColors());
				
				if(Objects.nonNull(mdr.getRemovableUICC()))
					newMDR.setRemovableUICC(mdr.getRemovableUICC());
				
				if(Objects.nonNull(mdr.getRemovableEUICC()))
					newMDR.setRemovableEUICC(mdr.getRemovableEUICC());
				
				if(Objects.nonNull(mdr.getNonremovableUICC()))
					newMDR.setNonremovableUICC(mdr.getNonremovableUICC());
				
				if(Objects.nonNull(mdr.getNonremovableEUICC()))
					newMDR.setNonremovableEUICC(mdr.getNonremovableEUICC());
				
				if(Objects.nonNull(mdr.getBandDetail()))
					newMDR.setBandDetail(mdr.getBandDetail());
				
				if(Objects.nonNull(mdr.getLaunchPriceAsianMarket()))
					newMDR.setLaunchPriceAsianMarket(mdr.getLaunchPriceAsianMarket());
				
				if(Objects.nonNull(mdr.getLaunchPriceUSMarket()))
					newMDR.setLaunchPriceUSMarket(mdr.getLaunchPriceUSMarket());
				
				if(Objects.nonNull(mdr.getLaunchPriceEuropeMarket()))
					newMDR.setLaunchPriceEuropeMarket(mdr.getLaunchPriceEuropeMarket());
				
				if(Objects.nonNull(mdr.getLaunchPriceInternationalMarket()))
					newMDR.setLaunchPriceInternationalMarket(mdr.getLaunchPriceInternationalMarket());
				
				if(Objects.nonNull(mdr.getLaunchPriceCambodiaMarket()))
					newMDR.setLaunchPriceCambodiaMarket(mdr.getLaunchPriceCambodiaMarket());
				
				if(Objects.nonNull(mdr.getCustomPriceOfDevice()))
					newMDR.setCustomPriceOfDevice(mdr.getCustomPriceOfDevice());
				
				if(Objects.nonNull(mdr.getSourceOfCambodiaMarketPrice()))
					newMDR.setSourceOfCambodiaMarketPrice(mdr.getSourceOfCambodiaMarketPrice());
				
				if(Objects.nonNull(mdr.getReportedGlobalIssue()))
					newMDR.setReportedGlobalIssue(mdr.getReportedGlobalIssue());
				
				if(Objects.nonNull(mdr.getReportedLocalIssue()))
					newMDR.setReportedLocalIssue(mdr.getReportedLocalIssue());
				
				if(Objects.nonNull(mdr.getUserId()))
					newMDR.setUserId(mdr.getUserId());
				
				if(Objects.nonNull(mdr.getRemark()))
					newMDR.setRemark(mdr.getRemark());
				
				if(Objects.nonNull(mdr.getNetworkSpecificIdentifier()))
					newMDR.setNetworkSpecificIdentifier(mdr.getNetworkSpecificIdentifier());
				 
				if(Objects.nonNull(mdr.getAttachedFiles())) {
					String fileUploadPath = systemConfigurationDbRepository.getByTag("user_upload_filepath").getValue();
					fileUploadPath = fileUploadPath+newMDR.getDeviceId()+"/MDR/";
//					uploadFileList = new ArrayList<UploadedFileToSync>();
					if(Objects.isNull(newMDR.getAttachedFiles())) {
						newMDR.setAttachedFiles(new ArrayList<AttachedFileInfo>());
					}
					for(AttachedFileInfo fileInfo: mdr.getAttachedFiles()) {
						if(Objects.nonNull(fileInfo.getId())) {
							List<AttachedFileInfo> deletedFilesObjs = new ArrayList<AttachedFileInfo>();
							int i = 0;
							for(AttachedFileInfo savedFileInfo: newMDR.getAttachedFiles()) {
								if(savedFileInfo.getId().equals(fileInfo.getId())) {
									if(Objects.nonNull(fileInfo.getFileName()) && !fileInfo.getFileName().equals("")) {
										savedFileInfo.setFileName(fileInfo.getFileName());
										if(Objects.nonNull(fileInfo.getDocType()))
											savedFileInfo.setDocType(fileInfo.getDocType());
//										uploadFileList.add(new UploadedFileToSync(fileInfo.getFileName(),
//												fileUploadPath, propertiesReader.serverId, newMDR.getDeviceId()));
									} else
										deletedFilesObjs.add(savedFileInfo);
//										deletedFilesId.add(i);
									i++;
								}
							}
							if(deletedFilesObjs.size() > 0) {
								logger.info("File ids to delete:{"+deletedFilesObjs.toString()+"}");
								logger.info("Before removeing file:{"+newMDR.getAttachedFiles().toString()+"}");
								for(AttachedFileInfo fileObj: deletedFilesObjs)
									newMDR.getAttachedFiles().remove(fileObj);
//								newMDR.getAttachedFiles().removeAll(deletedFilesId);
								logger.info("After removeing file:{"+newMDR.getAttachedFiles().toString()+"}");
							}
						}else {
							attachedFile = new AttachedFileInfo();
							if(Objects.nonNull(fileInfo.getDocType()))
								attachedFile.setDocType(fileInfo.getDocType());
							if(Objects.nonNull(fileInfo.getFileName()))
								attachedFile.setFileName(fileInfo.getFileName());
							attachedFile.setMdrId(newMDR.getId());
							newMDR.getAttachedFiles().add(attachedFile);
//							uploadFileList.add(new UploadedFileToSync(fileInfo.getFileName(),
//									fileUploadPath, propertiesReader.serverId, newMDR.getDeviceId()));
						}
					}
				}
				newMDR = mdrRepository.save(newMDR);
				logger.info("Modified MDR: "+newMDR.toString());
				mdrHistoryRepository.save(new MobileDeviceRepositoryHistory(newMDR));
//				if(Objects.nonNull(uploadFileList))
//					fileSyncRepository.saveAll(uploadFileList);
			}
			
			if(brands.size() > 0) {
				logger.info("Saving all new brands.");
				brandService.updateNewBrands(brands);
				logger.info("Saving all new models.");
				modelService.updateNewModels(brands, modelBrandMap);
			}
			
			// Audit trail entry
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Mobile Device Repository");
			auditTrail.setSubFeature("Update");
			auditTrail.setFeatureId( mdrs.get(0).getFeatureId());
			if( Objects.nonNull(mdrs.get(0).getPublicIp()))
				auditTrail.setPublicIp(mdrs.get(0).getPublicIp());
			if( Objects.nonNull(mdrs.get(0).getBrowser()))
				auditTrail.setBrowser(mdrs.get(0).getBrowser());
			txnId = mdrs.get(0).getDeviceId();
			auditTrail.setUserType(user.getUsertype().getUsertypeName());
			auditTrail.setRoleType(user.getUsertype().getUsertypeName());
			auditTrail.setUserId(user.getId());
			auditTrail.setUserName(user.getUsername());
			auditTrail.setTxnId(txnId);
			auditTrailRepository.save(auditTrail);
			return new MDRGenricResponse(0,"All devices information successfuly updated.", (long)newMDR.getId());

		}catch (Exception e) {
			logger.error("Mobile Device Repository addition failed="+e.getMessage());
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	@Transactional
	public MDRGenricResponse deleteDevice(String deviceId, String remark, String publicIp, String browser,
			Integer userId, String userType, Integer userTypeId, Integer featureId) {
		List<StatesInterpretationDb> states = null;
		MobileDeviceRepository mdr = null;
		String txnId = "";
		User user  = null;
		int status = 0;
		try {
			states = statesInterpretaionRepository.findByFeatureId(featureId);
			for( StatesInterpretationDb state : states ) {
            	if( state.getInterpretation().trim().equalsIgnoreCase(Tags.DELETED))
            		status = state.getState();
            }
			// Mark device as deleted
			mdr = mdrRepository.getByDeviceId(deviceId);
			mdr.setDeviceState(status);
			mdr.setRemark(remark);
			mdr.setModifiedOn(LocalDateTime.now());
			mdr.setUserId(userId);
			mdr = mdrRepository.save(mdr);
			mdrHistoryRepository.save(new MobileDeviceRepositoryHistory(mdr));
			txnId = deviceId.toString();
			user = userRepository.getByid(userId);
			// Audit trail entry
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Mobile Device Repository");
			auditTrail.setSubFeature("Delete");
			auditTrail.setFeatureId(featureId);
			auditTrail.setPublicIp(publicIp);
			auditTrail.setBrowser(browser);
			auditTrail.setUserType(userType);
			auditTrail.setRoleType(userType);
			auditTrail.setUserId(user.getId());
			auditTrail.setUserName(user.getUsername());
			auditTrail.setTxnId(txnId);
			auditTrailRepository.save(auditTrail);
			return new MDRGenricResponse(0,"The device is successfuly deleted.", (long)mdr.getId());
		}catch (Exception e) {
			logger.error("The device deletion is failed="+e.getMessage());
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	
	public Page<MobileDeviceRepository> getDevicesDetails(
			MobileDeviceRepositoryFilterRequest mdrRequest, Integer pageNo, Integer pageSize) {
		boolean isDefaultFilter = this.mdrDefaultFilter(mdrRequest);
		Pageable pageable = null;
		List<StatesInterpretationDb> states = null;
		Page<MobileDeviceRepository> results  = null;
		try {
			logger.info("[MACRA_MDR] Start try Block");
			AuditTrail auditTrail = new AuditTrail();
			logger.info("[MACRA_MDR] try blok mdrRequest.getOrder()"+mdrRequest.getOrder());
			Sort.Direction direction;
			if (mdrRequest.getOrder()!=null && mdrRequest.getOrder().equalsIgnoreCase("asc") ) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;				
			}
			if(mdrRequest.getOrderColumnName()!=null) {
				logger.info("[MACRA_MDR] getOrderColumnName : "+mdrRequest.getOrderColumnName()+ " pageable : "+pageable);
			//if(Objects.nonNull(mdrRequest.getOrderColumnName()))
				try {
					pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction,
							OrderColumnMapping.getColumnMapping(mdrRequest.getOrderColumnName()).name()));
				} catch (Exception e) {
					// TODO: handle exception
					pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, "modifiedOn").and(Sort.by(direction, "id")));
					//pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, "modifiedOn"));
				}
				
				logger.info("[MACRA_MDR] Final getOrderColumnName : "+mdrRequest.getOrderColumnName()+ " pageable : "+pageable);
			}
			else
				pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, "modifiedOn").and(Sort.by(direction, "id")));
			
			
			
			states = statesInterpretaionRepository.findByFeatureId(mdrRequest.getFeatureId());
			
			logger.info("[MACRA_MDR] states : "+states+ " pageable : "+pageable);
			GenericSpecificationBuilder<MobileDeviceRepository> gsb = 
					(GenericSpecificationBuilder<MobileDeviceRepository>) this.createFilter(mdrRequest, states,
							isDefaultFilter);
			try {
				results =  this.setInterps(mdrRepository.findAll(gsb.build(), pageable), states);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			logger.info("[MACRA_MDR] states : "+states+ " pageable : "+pageable +" results "+results);
			
			auditTrail.setFeatureName("Mobile Device Repository");
			if( !isDefaultFilter )
				auditTrail.setSubFeature("Filter");
			else
				auditTrail.setSubFeature("View All");
			auditTrail.setFeatureId( mdrRequest.getFeatureId());
			if( mdrRequest.getPublicIp()!=null)
				auditTrail.setPublicIp(mdrRequest.getPublicIp());
			if( mdrRequest.getBrowser()!=null)
				auditTrail.setBrowser(mdrRequest.getBrowser());
			if( mdrRequest.getUserId()!=null ) {
				User user = userRepository.getByid( mdrRequest.getUserId());
				auditTrail.setUserId( mdrRequest.getUserId() );
				auditTrail.setUserName( user.getUsername());
			}else {
				auditTrail.setUserName( "NA");
			}
			if( mdrRequest.getUserType()!=null ) {
				auditTrail.setUserType( mdrRequest.getUserType());
				auditTrail.setRoleType( mdrRequest.getUserType() );
			}else {
				auditTrail.setUserType( "NA" );
				auditTrail.setRoleType( "NA" );
			}
//			if(Objects.nonNull(mdrRequest.getDeviceId()) && mdrRequest.getDeviceId() != -1) {
//				auditTrail.setTxnId(String.valueOf(mdrRequest.getDeviceId()));
//			}else {
//				auditTrail.setTxnId("NA");
//			}
			auditTrail.setTxnId("NA");
			auditTrailRepository.save(auditTrail);
			
			return results;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	public boolean mdrDefaultFilter( MobileDeviceRepositoryFilterRequest mdrRequest ) {
		try {
			if(Objects.nonNull(mdrRequest.getStartDate()) && !mdrRequest.getStartDate().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getEndDate()) && !mdrRequest.getEndDate().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getModifiedOn()) && !mdrRequest.getModifiedOn().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getDeviceId()) && !mdrRequest.getDeviceId().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getDeviceType()) && !mdrRequest.getDeviceType().equals("") && !mdrRequest.getDeviceType().equals("-1"))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getDeviceStatus()) && !mdrRequest.getDeviceStatus().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getMdrStatus()) && mdrRequest.getMdrStatus() != -1)
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getMarketingName()) && !mdrRequest.getMarketingName().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getModelName()) && !mdrRequest.getModelName().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getManufacturer()) && !mdrRequest.getManufacturer().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getBrandName()) && !mdrRequest.getBrandName().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getOs()) && !mdrRequest.getOs().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getOrganizationId()) && !mdrRequest.getOrganizationId().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getNetworkTechnologyGSM()) && mdrRequest.getNetworkTechnologyGSM() != -1)
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getNetworkTechnologyCDMA()) && mdrRequest.getNetworkTechnologyCDMA() != -1)
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getNetworkTechnologyLTE()) && mdrRequest.getNetworkTechnologyLTE() != -1)
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getNetworkTechnology5G()) && mdrRequest.getNetworkTechnology5G() != -1)
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getSearchString()) && mdrRequest.getSearchString().equals(""))
				return Boolean.FALSE;
			if(Objects.nonNull(mdrRequest.getUserId()) && mdrRequest.getUserId() != -1)
				return Boolean.FALSE;
		}catch( Exception ex) {
			logger.error(ex.getMessage(), ex);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	public GenericSpecificationBuilder<T> createFilter(MobileDeviceRepositoryFilterRequest mdrRequest,
			List<StatesInterpretationDb> states, boolean isDefaultFilter
	){
		GenericSpecificationBuilder<T> gsb = 
				new GenericSpecificationBuilder<T>(propertiesReader.dialect);
		try {
			logger.info("Default filter:["+isDefaultFilter+"]");
			if(!isDefaultFilter) {
				logger.info("[MACRA_MDR] isDefaultFilter start "+isDefaultFilter);
				if(Objects.nonNull(mdrRequest.getStartDate()) && (!mdrRequest.getStartDate().equals("") || !mdrRequest.getStartDate().equalsIgnoreCase("Null")))
					gsb.with(new SearchCriteria("createdOn", mdrRequest.getStartDate() , SearchOperation.GREATER_THAN, Datatype.DATE));
				
				if(Objects.nonNull(mdrRequest.getEndDate()) && (!mdrRequest.getEndDate().equals("")  || !mdrRequest.getEndDate().equalsIgnoreCase("Null")))
					gsb.with(new SearchCriteria("createdOn",mdrRequest.getEndDate() , SearchOperation.LESS_THAN, Datatype.DATE));
				
				if(Objects.nonNull(mdrRequest.getModifiedOn()) && (!mdrRequest.getModifiedOn().equals("") || !mdrRequest.getModifiedOn().equalsIgnoreCase("Null")))
					gsb.with(new SearchCriteria("modifiedOn", mdrRequest.getModifiedOn() , SearchOperation.EQUALITY, Datatype.DATE));
					logger.info("[MACRA_MDR] modifiedOn start "+isDefaultFilter);
				
				if(Objects.nonNull(mdrRequest.getDeviceId()) && !mdrRequest.getDeviceId().equals("")) {
					gsb.with(new SearchCriteria("deviceId", mdrRequest.getDeviceId(), SearchOperation.LIKE, Datatype.STRING));
				}
				
				if(Objects.nonNull(mdrRequest.getDeviceType()) && !mdrRequest.getDeviceType().equals("") && !mdrRequest.getDeviceType().equals("-1")) {
					logger.info("filter deviceType:["+mdrRequest.getDeviceType()+"]");
					gsb.with(new SearchCriteria("deviceType", mdrRequest.getDeviceType(), SearchOperation.LIKE, Datatype.STRING));
				} /*else {
					gsb.addSpecification(gsb.inString("deviceType", Arrays.asList(new String[] {"Mobile Phone/Feature phone",
							"Smartphone"})));
				} */
				
				if(Objects.nonNull(mdrRequest.getDeviceStatus()) && !mdrRequest.getDeviceStatus().equals("")) {
					logger.info("filter deviceStatus:["+mdrRequest.getDeviceStatus()+"]");
					gsb.with(new SearchCriteria("deviceStatus", mdrRequest.getDeviceStatus(), SearchOperation.LIKE, Datatype.STRING));
				}
				
				if(Objects.nonNull(mdrRequest.getMdrStatus()) && mdrRequest.getMdrStatus() != -1)
					gsb.with(new SearchCriteria("deviceState", mdrRequest.getMdrStatus(), SearchOperation.EQUALITY, Datatype.INT));
				else {
					for( StatesInterpretationDb state : states ) {
		            	if( state.getInterpretation().trim().equalsIgnoreCase(Tags.DELETED))
		            		gsb.with(new SearchCriteria("deviceState", state.getState(),
		            				SearchOperation.NEGATION, Datatype.INT));
		            }
				}
				
				if(Objects.nonNull(mdrRequest.getMarketingName()) && !mdrRequest.getMarketingName().equals("")) {
					logger.info("filter marketingName:["+mdrRequest.getMarketingName()+"]");
					gsb.with(new SearchCriteria("marketingName", mdrRequest.getMarketingName(), SearchOperation.LIKE,
							Datatype.STRING));
				}
				if( Objects.nonNull(mdrRequest.getManufacturer()) && !mdrRequest.getManufacturer().equals("")) {
					logger.info("filter manufacturer:["+mdrRequest.getManufacturer()+"]");
					gsb.with(new SearchCriteria("manufacturer", mdrRequest.getManufacturer(), SearchOperation.LIKE, Datatype.STRING));
				}
				
				if( Objects.nonNull(mdrRequest.getModelName()) && !mdrRequest.getModelName().equals("")) {
					logger.info("filter modelName:["+mdrRequest.getModelName()+"]");
					gsb.with(new SearchCriteria("modelName", mdrRequest.getModelName(), SearchOperation.LIKE, Datatype.STRING));
				}
				
				if(Objects.nonNull(mdrRequest.getBrandName()) && !mdrRequest.getBrandName().equals("")) {
					logger.info("filter brandName:["+mdrRequest.getBrandName()+"]");
					gsb.with(new SearchCriteria("brandName", mdrRequest.getBrandName(), SearchOperation.LIKE, Datatype.STRING));
				}
				
				if(Objects.nonNull(mdrRequest.getOs()) && !mdrRequest.getOs().equals("")) {
					logger.info("filter os:["+mdrRequest.getOs()+"]");
					gsb.with(new SearchCriteria("os", mdrRequest.getOs(), SearchOperation.LIKE, Datatype.STRING));
				}
				
				if(Objects.nonNull(mdrRequest.getOrganizationId()) && !mdrRequest.getOrganizationId().equals("")) {
					logger.info("filter organizationId:["+mdrRequest.getOrganizationId()+"]");
					gsb.with(new SearchCriteria("organizationId", mdrRequest.getOrganizationId(), SearchOperation.LIKE,
							Datatype.STRING));
				}
				
				if(Objects.nonNull(mdrRequest.getNetworkTechnologyGSM()) && mdrRequest.getNetworkTechnologyGSM() != -1)
					gsb.with(new SearchCriteria("networkTechnologyGSM", mdrRequest.getNetworkTechnologyGSM(),
							SearchOperation.EQUALITY, Datatype.INT));
				
				if(Objects.nonNull(mdrRequest.getNetworkTechnologyCDMA()) && mdrRequest.getNetworkTechnologyCDMA() != -1)
					gsb.with(new SearchCriteria("networkTechnologyCDMA", mdrRequest.getNetworkTechnologyCDMA(),
							SearchOperation.EQUALITY, Datatype.INT));
				
				if(Objects.nonNull(mdrRequest.getNetworkTechnologyLTE()) && mdrRequest.getNetworkTechnologyLTE() != -1)
					gsb.with(new SearchCriteria("networkTechnologyLTE", mdrRequest.getNetworkTechnologyLTE(),
							SearchOperation.EQUALITY, Datatype.INT));
				
				if(Objects.nonNull(mdrRequest.getNetworkTechnology5G()) && mdrRequest.getNetworkTechnology5G() != -1)
					gsb.with(new SearchCriteria("networkTechnology5G", mdrRequest.getNetworkTechnology5G(),
							SearchOperation.EQUALITY, Datatype.INT));
				
				if(Objects.nonNull(mdrRequest.getSearchString()) && !mdrRequest.getSearchString().equals("")) {
					gsb.with(new SearchCriteria("deviceType", mdrRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
					gsb.with(new SearchCriteria("deviceStatus", mdrRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
					gsb.with(new SearchCriteria("marketingName", mdrRequest.getSearchString(), SearchOperation.LIKE,
							Datatype.STRING));
					gsb.with(new SearchCriteria("modelName", mdrRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
					gsb.with(new SearchCriteria("brandName", mdrRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
					gsb.with(new SearchCriteria("os", mdrRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
					gsb.with(new SearchCriteria("organizationId", mdrRequest.getSearchString(), SearchOperation.LIKE,
							Datatype.STRING));
				}
				if(Objects.nonNull(mdrRequest.getUserDisplayName()) && !mdrRequest.getUserDisplayName().equals("")) {
					logger.info("filter getUserDisplayName:["+mdrRequest.getUserDisplayName()+"]");
					gsb.with(new SearchCriteria("userDisplayName", mdrRequest.getUserDisplayName(), SearchOperation.LIKE,
							Datatype.STRING));
				}
				
			}else {
				for( StatesInterpretationDb state : states ) {
	            	if(state.getInterpretation().trim().equalsIgnoreCase(Tags.DELETED))
	            		gsb.with(new SearchCriteria("deviceState", state.getState(),
	            				SearchOperation.NEGATION, Datatype.INT));
	            }
//				gsb.addSpecification(gsb.inString("deviceType", Arrays.asList(new String[] {"Mobile Phone/Feature phone",
//				"Smartphone"})));
			}
		}catch(Exception ex) {
			logger.error("Exception get Data from filter"+ex.getMessage(), ex);
			return gsb;
		}
		logger.info("[MACRA_MDR] end filter  "+gsb.toString());
		return gsb;
	}
	
	public Page<MobileDeviceRepository> setInterps(Page<MobileDeviceRepository> results, List<StatesInterpretationDb> states){
		List<SystemConfigListDb> esimSupports = null;
		List<SystemConfigListDb> softsimSupports = null;
		List<SystemConfigListDb> networkTechnologyGSMs  = null;
		List<SystemConfigListDb> networkTechnologyCDMAs = null;
		List<SystemConfigListDb> networkTechnologyEVDOs = null;
		List<SystemConfigListDb> networkTechnologyLTEs  = null;
		List<SystemConfigListDb> networkTechnology5Gs = null;
		List<SystemConfigListDb> networkTechnology6Gs = null;
		List<SystemConfigListDb> networkTechnology7Gs = null;
		List<SystemConfigListDb> memoryCardSlots   = null;
		List<SystemConfigListDb> mainCameraTypes   = null;
		List<SystemConfigListDb> selfieCameraTypes = null;
		List<SystemConfigListDb> soundLoudspeakers = null;
		List<SystemConfigListDb> sound35mmJacks = null;
		List<SystemConfigListDb> commsNFCs   = null;
		List<SystemConfigListDb> commsRadios = null;
		User user = null;
		String fileUploadPath = systemConfigurationDbRepository.getByTag("user_upload_filepath").getValue();
		
		esimSupports    = systemConfigListRepository.findByTag("ESIM_SUPPORT");
		softsimSupports = systemConfigListRepository.findByTag("SOFTSIM_SUPPORT");
		networkTechnologyGSMs  = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_GSM");
		networkTechnologyCDMAs = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_CDMA");
		networkTechnologyEVDOs = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_EVDO");
		networkTechnologyLTEs  = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_LTE");
		networkTechnology5Gs   = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_5G");
		networkTechnology6Gs   = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_6G");
		networkTechnology7Gs   = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_7G");
		memoryCardSlots   = systemConfigListRepository.findByTag("MEMORY_CARD_SLOT");
		mainCameraTypes   = systemConfigListRepository.findByTag("MAIN_CAMERA_TYPE");
		selfieCameraTypes = systemConfigListRepository.findByTag("SELFIE_CAMERA_TYPE");
		soundLoudspeakers = systemConfigListRepository.findByTag("SOUND_LOUDSPEAKER");
		sound35mmJacks = systemConfigListRepository.findByTag("SOUND_3.5MM_JACK");
		commsNFCs   = systemConfigListRepository.findByTag("COMMS_NFC");
		commsRadios = systemConfigListRepository.findByTag("COMMS_RADIO");
		for( MobileDeviceRepository result : results) {
            for( StatesInterpretationDb state : states ) {
            	if( state.getState().equals( result.getDeviceState())) {
            		result.setStateInterp( state.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: esimSupports) {
            	if( interp.getValue().equals(result.getEsimSupport())) {
            		result.setEsimSupportInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: softsimSupports) {
            	if( interp.getValue().equals(result.getSoftsimSupport())) {
            		result.setSoftsimSupportInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyGSMs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyGSM())) {
            		result.setNetworkTechnologyGSMInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyCDMAs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyCDMA())) {
            		result.setNetworkTechnologyCDMAInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyEVDOs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyEVDO())) {
            		result.setNetworkTechnologyEVDOInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyLTEs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyLTE())) {
            		result.setNetworkTechnologyLTEInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnology5Gs) {
            	if( interp.getValue().equals(result.getNetworkTechnology5G())) {
            		result.setNetworkTechnology5GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnology6Gs) {
            	if( interp.getValue().equals(result.getNetworkTechnology6G())) {
            		result.setNetworkTechnology6GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnology7Gs) {
            	if( interp.getValue().equals(result.getNetworkTechnology7G())) {
            		result.setNetworkTechnology7GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: memoryCardSlots) {
            	if( interp.getValue().equals(result.getMemoryCardSlot())) {
            		result.setMemoryCardSlotInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: mainCameraTypes) {
            	if( interp.getValue().equals(result.getMainCameraType())) {
            		result.setMainCameraTypeInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: selfieCameraTypes) {
            	if( interp.getValue().equals(result.getSelfieCameraType())) {
            		result.setSelfieCameraTypeInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: soundLoudspeakers) {
            	if( interp.getValue().equals(result.getSoundLoudspeaker())) {
            		result.setSoundLoudspeakerInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: sound35mmJacks) {
            	if( interp.getValue().equals(result.getSound35mmJack())) {
            		result.setSound35mmJackInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: commsNFCs) {
            	if( interp.getValue().equals(result.getCommsNFC())) {
            		result.setCommsNFCInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: commsRadios) {
            	if( interp.getValue().equals(result.getCommsRadio())) {
            		result.setCommsRadioInterp(interp.getInterpretation());
            		break;
            	}
            }
            user = userRepository.getByid(result.getUserId());
            //result.setUserDisplayName(user.getUserProfile().getDisplayName());
            if(user!=null) {
            	result.setUserDisplayName(user.getUserProfile().getFirstName() +" "+user.getUserProfile().getLastName());
            }else {
            	result.setUserDisplayName("");
            }
//            try {
//            	 result.setUserDisplayName(user.getUserProfile().getFirstName() +" "+user.getUserProfile().getLastName());
//			} catch (Exception e) {
//				// TODO: handle exception
//				result.setUserDisplayName("");
//			}
           
            for(AttachedFileInfo fileDetails: result.getAttachedFiles()) {
            	fileDetails.setFilePath(fileUploadPath+result.getDeviceId()+"/MDR/"+fileDetails.getFileName());
            }
		}
		return results;
	}
	
	public Page<MobileDeviceRepositoryHistory> setHistoryInterps(Page<MobileDeviceRepositoryHistory> results,
			List<StatesInterpretationDb> states){
		List<SystemConfigListDb> esimSupports = null;
		List<SystemConfigListDb> softsimSupports = null;
		List<SystemConfigListDb> networkTechnologyGSMs  = null;
		List<SystemConfigListDb> networkTechnologyCDMAs = null;
		List<SystemConfigListDb> networkTechnologyEVDOs = null;
		List<SystemConfigListDb> networkTechnologyLTEs  = null;
		List<SystemConfigListDb> networkTechnology5Gs = null;
		List<SystemConfigListDb> networkTechnology6Gs = null;
		List<SystemConfigListDb> networkTechnology7Gs = null;
		List<SystemConfigListDb> memoryCardSlots   = null;
		List<SystemConfigListDb> mainCameraTypes   = null;
		List<SystemConfigListDb> selfieCameraTypes = null;
		List<SystemConfigListDb> soundLoudspeakers = null;
		List<SystemConfigListDb> sound35mmJacks = null;
		List<SystemConfigListDb> commsNFCs   = null;
		List<SystemConfigListDb> commsRadios = null;
		User user = null;
		
		esimSupports    = systemConfigListRepository.findByTag("ESIM_SUPPORT");
		softsimSupports = systemConfigListRepository.findByTag("SOFTSIM_SUPPORT");
		networkTechnologyGSMs  = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_GSM");
		networkTechnologyCDMAs = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_CDMA");
		networkTechnologyEVDOs = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_EVDO");
		networkTechnologyLTEs  = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_LTE");
		networkTechnology5Gs   = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_5G");
		networkTechnology6Gs   = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_6G");
		networkTechnology7Gs   = systemConfigListRepository.findByTag("NETWORK_TECHNOLOGY_7G");
		memoryCardSlots   = systemConfigListRepository.findByTag("MEMORY_CARD_SLOT");
		mainCameraTypes   = systemConfigListRepository.findByTag("MAIN_CAMERA_TYPE");
		selfieCameraTypes = systemConfigListRepository.findByTag("SELFIE_CAMERA_TYPE");
		soundLoudspeakers = systemConfigListRepository.findByTag("SOUND_LOUDSPEAKER");
		sound35mmJacks = systemConfigListRepository.findByTag("SOUND_3.5MM_JACK");
		commsNFCs   = systemConfigListRepository.findByTag("COMMS_NFC");
		commsRadios = systemConfigListRepository.findByTag("COMMS_RADIO");
		
		int i = 0;
		int resultSize = results.getContent().size();
		
		for( MobileDeviceRepositoryHistory result : results) {
            for( StatesInterpretationDb state : states ) {
            	if( state.getState().equals( result.getDeviceState())) {
            		result.setStateInterp( state.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: esimSupports) {
            	if( interp.getValue().equals(result.getEsimSupport())) {
            		result.setEsimSupportInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: softsimSupports) {
            	if( interp.getValue().equals(result.getSoftsimSupport())) {
            		result.setSoftsimSupportInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyGSMs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyGSM())) {
            		result.setNetworkTechnologyGSMInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyCDMAs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyCDMA())) {
            		result.setNetworkTechnologyCDMAInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyEVDOs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyEVDO())) {
            		result.setNetworkTechnologyEVDOInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnologyLTEs) {
            	if( interp.getValue().equals(result.getNetworkTechnologyLTE())) {
            		result.setNetworkTechnologyLTEInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnology5Gs) {
            	if( interp.getValue().equals(result.getNetworkTechnology5G())) {
            		result.setNetworkTechnology5GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnology6Gs) {
            	if( interp.getValue().equals(result.getNetworkTechnology6G())) {
            		result.setNetworkTechnology6GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: networkTechnology7Gs) {
            	if( interp.getValue().equals(result.getNetworkTechnology7G())) {
            		result.setNetworkTechnology7GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: memoryCardSlots) {
            	if( interp.getValue().equals(result.getMemoryCardSlot())) {
            		result.setMemoryCardSlotInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: mainCameraTypes) {
            	if( interp.getValue().equals(result.getMainCameraType())) {
            		result.setMainCameraTypeInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: selfieCameraTypes) {
            	if( interp.getValue().equals(result.getSelfieCameraType())) {
            		result.setSelfieCameraTypeInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: soundLoudspeakers) {
            	if( interp.getValue().equals(result.getSoundLoudspeaker())) {
            		result.setSoundLoudspeakerInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: sound35mmJacks) {
            	if( interp.getValue().equals(result.getSound35mmJack())) {
            		result.setSound35mmJackInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: commsNFCs) {
            	if( interp.getValue().equals(result.getCommsNFC())) {
            		result.setCommsNFCInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: commsRadios) {
            	if( interp.getValue().equals(result.getCommsRadio())) {
            		result.setCommsRadioInterp(interp.getInterpretation());
            		break;
            	}
            }
            user = userRepository.getByid(result.getUserId());
            //result.setUserDisplayName( user.getUsername());
            if(user!=null) {
            	result.setUserDisplayName(user.getUserProfile().getFirstName() +" "+user.getUserProfile().getLastName());
            }else {
            	result.setUserDisplayName("");
            }
//            try {
//           	 result.setUserDisplayName(user.getUserProfile().getFirstName() +" "+user.getUserProfile().getLastName());
//			} catch (Exception e) {
//				// TODO: handle exception
//				result.setUserDisplayName("");
//			}
           // result.setUserDisplayName(user.getUserProfile().getFirstName() +" "+user.getUserProfile().getLastName());
            if(resultSize > 1 && i < resultSize-1) {
            	result.setUpdateColumns(mdrHistoryRepository.getUpdatedColumns(
            			result.getId(), results.getContent().get(i+1).getId()).split(","));
            }
            i++;
		}
		return results;
	}
	
	public FileDetails exportData(MobileDeviceRepositoryFilterRequest mdrRequest, Integer pageNo, Integer pageSize) {
		int maxFileRecord = 0;
		boolean isDefaultFilter = this.mdrDefaultFilter(mdrRequest);
		String fileName = null;
		Writer writer   = null;
		Pageable pageable = null;
		List<StatesInterpretationDb> states = null;
		List<MobileDeviceRepositoryNoFile> mdrs  = null;
		DateTimeFormatter dtf2  = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		String filePath  = systemConfigurationDbRepository.getByTag("file.download-dir").getValue();
		StatefulBeanToCsvBuilder<MobileDeviceRepoFileModel> builder = null;
		StatefulBeanToCsv<MobileDeviceRepoFileModel> csvWriter      = null;
		List< MobileDeviceRepoFileModel> fileRecords       = null;
		GenericSpecificationBuilder<MobileDeviceRepositoryNoFile> gsb = null;
		CustomMappingStrategy<MobileDeviceRepoFileModel> mappingStrategy = new CustomMappingStrategy<MobileDeviceRepoFileModel>();
		List<SystemConfigListDb> interp = null;
		String[] tags = new String[] {"ESIM_SUPPORT", "SOFTSIM_SUPPORT", "NETWORK_TECHNOLOGY_GSM", "NETWORK_TECHNOLOGY_CDMA",
				"NETWORK_TECHNOLOGY_EVDO", "NETWORK_TECHNOLOGY_LTE", "NETWORK_TECHNOLOGY_5G", "NETWORK_TECHNOLOGY_6G",
				"NETWORK_TECHNOLOGY_7G", "MEMORY_CARD_SLOT", "MAIN_CAMERA_TYPE", "SELFIE_CAMERA_TYPE", "SOUND_LOUDSPEAKER",
				"SOUND_3.5MM_JACK", "COMMS_NFC", "COMMS_RADIO"};
		List<SystemConfigListDb>[] interpsConfig = new List[tags.length];
		try {
			for(int i=0; i<tags.length; i++) {
				interp = systemConfigListRepository.findByTag(tags[i]);
				interpsConfig[i] = interp;
				interp = null;
			}
			//File writer configuration start
			fileName = LocalDateTime.now().format(dtf2).replace(" ", "_")+"_Mobile_Device_Repo.csv";
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
			mappingStrategy.setType(MobileDeviceRepoFileModel.class);
			builder = new StatefulBeanToCsvBuilder<MobileDeviceRepoFileModel>(writer);
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(
					CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			//File writer configuration end
			
			
			pageNo = 0;
			pageSize = 10000;
			maxFileRecord = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
			if( pageSize > maxFileRecord)
				pageSize = maxFileRecord;
			Sort.Direction direction;
			if ( Objects.nonNull(mdrRequest.getOrder()) && mdrRequest.getOrder().equalsIgnoreCase("asc") ) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;				
			}

			states = statesInterpretaionRepository.findByFeatureId(mdrRequest.getFeatureId());
			gsb = (GenericSpecificationBuilder<MobileDeviceRepositoryNoFile>) this.createFilter(mdrRequest, states,
					isDefaultFilter);
			fileRecords = new ArrayList<MobileDeviceRepoFileModel>();
			while(true) {
				if(Objects.nonNull(mdrRequest.getOrderColumnName()))
					pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction,
							OrderColumnMapping.getColumnMapping(mdrRequest.getOrderColumnName()).name()));
				else
					pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, "modifiedOn"));
				mdrs =  this.setExportDataInterps(mdrNoFileRepository.findAll(gsb.build(), pageable), states, interpsConfig).getContent();
				if( mdrs.size() > 0 ) { 
					for( MobileDeviceRepositoryNoFile mdr : mdrs ) {
						fileRecords.add(new MobileDeviceRepoFileModel(mdr));
					}
				}else
					break;
				pageNo++;
				if(pageSize*pageNo >= maxFileRecord)
					break;
			}
			
			if(fileRecords.size() > 0)
				csvWriter.write(fileRecords);
			else
				csvWriter.write( new MobileDeviceRepoFileModel());
			
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Mobile Device Repository");
			auditTrail.setSubFeature("Export");
			auditTrail.setFeatureId(mdrRequest.getFeatureId());
			if( Objects.nonNull(mdrRequest.getPublicIp()))
				auditTrail.setPublicIp(mdrRequest.getPublicIp());
			if( Objects.nonNull(mdrRequest.getBrowser()))
				auditTrail.setBrowser(mdrRequest.getBrowser());
			if( Objects.nonNull(mdrRequest.getUserId()) ) {
				User user = userRepository.getByid( mdrRequest.getUserId());
				auditTrail.setUserId( mdrRequest.getUserId() );
				auditTrail.setUserName( user.getUsername());
			}else {
				auditTrail.setUserName( "NA");
			}
			if( Objects.nonNull(mdrRequest.getUserType()) ) {
				auditTrail.setUserType( mdrRequest.getUserType());
				auditTrail.setRoleType( mdrRequest.getUserType() );
			}else {
				auditTrail.setUserType( "NA" );
				auditTrail.setRoleType( "NA" );
			}
			auditTrail.setTxnId("NA");
			auditTrailRepository.save(auditTrail);
			return new FileDetails( fileName, filePath,
					systemConfigurationDbRepository.getByTag("file.download-link").getValue().replace(
							"$LOCAL_IP",
							propertiesReader.localIp)+fileName );
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}finally {
			try {
				if( writer != null )
					writer.close();
			} catch (IOException e) {}
		}
	}
	
	public Page<MobileDeviceRepositoryNoFile> setExportDataInterps(Page<MobileDeviceRepositoryNoFile> results,
			List<StatesInterpretationDb> states, List<SystemConfigListDb>[] interpsConfig){
//		String[] tags = new String[] {"ESIM_SUPPORT", "SOFTSIM_SUPPORT", "NETWORK_TECHNOLOGY_GSM", "NETWORK_TECHNOLOGY_CDMA",
//				"NETWORK_TECHNOLOGY_EVDO", "NETWORK_TECHNOLOGY_LTE", "NETWORK_TECHNOLOGY_5G", "NETWORK_TECHNOLOGY_6G",
//				"NETWORK_TECHNOLOGY_7G", "MEMORY_CARD_SLOT", "MAIN_CAMERA_TYPE", "SELFIE_CAMERA_TYPE", "SOUND_LOUDSPEAKER",
//				"SOUND_3.5MM_JACK", "COMMS_NFC", "COMMS_RADIO"};
		for( MobileDeviceRepositoryNoFile result : results) {
            for( StatesInterpretationDb state : states ) {
            	if( state.getState().equals( result.getDeviceState())) {
            		result.setStateInterp( state.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[0]) {
            	if( interp.getValue().equals(result.getEsimSupport())) {
            		result.setEsimSupportInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[1]) {
            	if( interp.getValue().equals(result.getSoftsimSupport())) {
            		result.setSoftsimSupportInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[2]) {
            	if( interp.getValue().equals(result.getNetworkTechnologyGSM())) {
            		result.setNetworkTechnologyGSMInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[3]) {
            	if( interp.getValue().equals(result.getNetworkTechnologyCDMA())) {
            		result.setNetworkTechnologyCDMAInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[4]) {
            	if( interp.getValue().equals(result.getNetworkTechnologyEVDO())) {
            		result.setNetworkTechnologyEVDOInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[5]) {
            	if( interp.getValue().equals(result.getNetworkTechnologyLTE())) {
            		result.setNetworkTechnologyLTEInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[6]) {
            	if( interp.getValue().equals(result.getNetworkTechnology5G()))
            		result.setNetworkTechnology5GInterp(interp.getInterpretation());
            }
            for(SystemConfigListDb interp: interpsConfig[7]) {
            	if( interp.getValue().equals(result.getNetworkTechnology6G())) {
            		result.setNetworkTechnology6GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[8]) {
            	if( interp.getValue().equals(result.getNetworkTechnology7G())) {
            		result.setNetworkTechnology7GInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[9]) {
            	if( interp.getValue().equals(result.getMemoryCardSlot())) {
            		result.setMemoryCardSlotInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[10]) {
            	if( interp.getValue().equals(result.getMainCameraType())) {
            		result.setMainCameraTypeInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[11]) {
            	if( interp.getValue().equals(result.getSelfieCameraType())) {
            		result.setSelfieCameraTypeInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[12]) {
            	if( interp.getValue().equals(result.getSoundLoudspeaker())) {
            		result.setSoundLoudspeakerInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[13]) {
            	if( interp.getValue().equals(result.getSound35mmJack())) {
            		result.setSound35mmJackInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[14]) {
            	if( interp.getValue().equals(result.getCommsNFC())) {
            		result.setCommsNFCInterp(interp.getInterpretation());
            		break;
            	}
            }
            for(SystemConfigListDb interp: interpsConfig[15]) {
            	if( interp.getValue().equals(result.getCommsRadio())) {
            		result.setCommsRadioInterp(interp.getInterpretation());
            		break;
            	}
            }
		}
		return results;
	}
	
	public Page<MobileDeviceRepositoryHistory> getFilterMDRHistory(
			RepositoryHistoryFilterRequest mdrRequest,
			Integer pageNo, Integer pageSize) {
		
		Pageable pageable = null;
		Page<MobileDeviceRepositoryHistory> results = null;
		List<StatesInterpretationDb> states = null;
		try {
			Sort.Direction direction;
			if ( Objects.nonNull(mdrRequest.getOrder()) && mdrRequest.getOrder().equalsIgnoreCase("asc") ) {
				direction = Sort.Direction.ASC;
			} else {
				direction = Sort.Direction.DESC;				
			}
			pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, "updatedOn"));
			
			GenericSpecificationBuilder<MobileDeviceRepositoryHistory> gsb = 
					new GenericSpecificationBuilder<MobileDeviceRepositoryHistory>(propertiesReader.dialect);
			states = statesInterpretaionRepository.findByFeatureId( mdrRequest.getFeatureId() );
			
			if(Objects.nonNull(mdrRequest.getStartDate()) && !mdrRequest.getStartDate().equals(""))
				gsb.with(new SearchCriteria("updatedOn", mdrRequest.getStartDate() , SearchOperation.GREATER_THAN, Datatype.DATE));
			
			if(Objects.nonNull(mdrRequest.getEndDate()) && !mdrRequest.getEndDate().equals(""))
				gsb.with(new SearchCriteria("updatedOn",mdrRequest.getEndDate() , SearchOperation.LESS_THAN, Datatype.DATE));
			
			if( Objects.nonNull(mdrRequest.getManufacturer()) && !mdrRequest.getManufacturer().equals("")) {
				logger.info("filter manufacturer:["+mdrRequest.getManufacturer()+"]");
				gsb.with(new SearchCriteria("manufacturer", mdrRequest.getManufacturer(), SearchOperation.LIKE, Datatype.STRING));
			}
			
			if( Objects.nonNull(mdrRequest.getModelName()) && !mdrRequest.getModelName().equals("")) {
				gsb.with(new SearchCriteria("modelName", mdrRequest.getModelName(), SearchOperation.LIKE, Datatype.STRING));
			}
			
			if(Objects.nonNull(mdrRequest.getBrandName()) && !mdrRequest.getBrandName().equals("")) {
				gsb.with(new SearchCriteria("brandName", mdrRequest.getBrandName(), SearchOperation.LIKE, Datatype.STRING));
			}
			
			if(Objects.nonNull(mdrRequest.getDeviceId()) && !mdrRequest.getDeviceId().equals("")) {
				logger.info("Device Id filter:["+mdrRequest.getDeviceId()+"]");
				gsb.with(new SearchCriteria("deviceId", mdrRequest.getDeviceId(), SearchOperation.LIKE, Datatype.STRING));
			}
			
			if(Objects.nonNull(mdrRequest.getOs()) && !mdrRequest.getOs().equals(""))
				gsb.with(new SearchCriteria("os", mdrRequest.getOs(), SearchOperation.LIKE, Datatype.STRING));
			
			if(Objects.nonNull(mdrRequest.getId()) && !mdrRequest.getId().equals(-1)) {
				logger.info("Id filter:["+mdrRequest.getId()+"]");
				gsb.with(new SearchCriteria("id", mdrRequest.getId(), SearchOperation.EQUALITY, Datatype.INT));
			}
			
			if(Objects.nonNull(mdrRequest.getMarketingName()) && !mdrRequest.getMarketingName().equals("")) {
				gsb.with(new SearchCriteria("marketingName", mdrRequest.getMarketingName(), SearchOperation.LIKE, Datatype.STRING));
			}
//			if(Objects.nonNull(mdrRequest.getUserDisplayName()) && !mdrRequest.getUserDisplayName().equals("")) {
//				gsb.with(new SearchCriteria("userDisplayName", mdrRequest.getUserDisplayName(), SearchOperation.LIKE, Datatype.STRING));
//			}
//			if(Objects.nonNull(mdrRequest.getUserDisplayName()) && !mdrRequest.getUserDisplayName().equals("")) {
//				logger.info("filter getUserDisplayName:["+mdrRequest.getUserDisplayName()+"]");
//				gsb.with(new SearchCriteria("userDisplayName", mdrRequest.getUserDisplayName(), SearchOperation.LIKE,
//						Datatype.STRING));
//			}
			
			if(Objects.nonNull(mdrRequest.getDeviceType()) && !mdrRequest.getDeviceType().equals("") && !mdrRequest.getDeviceType().equals("-1")) {
				gsb.with(new SearchCriteria("deviceType", mdrRequest.getDeviceType(), SearchOperation.LIKE, Datatype.STRING));
			}
			
			if(Objects.nonNull(mdrRequest.getMdrStatus()) && mdrRequest.getMdrStatus() != -1)
				gsb.with(new SearchCriteria("deviceState", mdrRequest.getMdrStatus(), SearchOperation.EQUALITY, Datatype.INT));
			else {
				for( StatesInterpretationDb state : states ) {
	            	if( state.getInterpretation().trim().equalsIgnoreCase(Tags.DELETED))
	            		gsb.with(new SearchCriteria("deviceState", state.getState(),
	            				SearchOperation.NEGATION, Datatype.INT));
	            }
			}
			
			
			results =  mdrHistoryRepository.findAll(gsb.build(), pageable);
			results =  this.setHistoryInterps(results, states);
			
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Mobile Device Repository");
			auditTrail.setSubFeature("History");
			auditTrail.setFeatureId(mdrRequest.getFeatureId());
			if( Objects.nonNull(mdrRequest.getPublicIp()))
				auditTrail.setPublicIp(mdrRequest.getPublicIp());
			if( Objects.nonNull(mdrRequest.getBrowser()))
				auditTrail.setBrowser(mdrRequest.getBrowser());
			if( Objects.nonNull(mdrRequest.getUserId()) ) {
				User user = userRepository.getByid( mdrRequest.getUserId());
				auditTrail.setUserId( mdrRequest.getUserId() );
				auditTrail.setUserName( user.getUsername());
			}else {
				auditTrail.setUserName( "NA");
			}
			if( Objects.nonNull(mdrRequest.getUserType()) ) {
				auditTrail.setUserType( mdrRequest.getUserType());
				auditTrail.setRoleType( mdrRequest.getUserType() );
			}else {
				auditTrail.setUserType( "NA" );
				auditTrail.setRoleType( "NA" );
			}
			auditTrail.setTxnId("NA");
			auditTrailRepository.save(auditTrail);
			return results;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}
	
	public MobileDeviceRepositoryHistory getFilterMDRHistoryRowInfo(RepositoryHistoryFilterRequest filterRequest) {
		Page<MobileDeviceRepositoryHistory> results = null;
		MobileDeviceRepositoryHistory history = null;
		List<Integer> lastHisIdPage = null;
		Integer preUpdateId = null;
		Pageable pageable = null;
		try {
			logger.info("History info request: {"+filterRequest.toString()+"}");
			results = this.getFilterMDRHistory(filterRequest, 0, 10);
			if(results.getNumberOfElements() > 0) {
				history = results.getContent().get(0);
				pageable = PageRequest.of(0, 1);
				lastHisIdPage = mdrHistoryRepository.getPreHistoryId(history.getId(), history.getDeviceId(), pageable);
				if(Objects.nonNull(lastHisIdPage) && lastHisIdPage.size() > 0) {
					preUpdateId = lastHisIdPage.get(0);
					if(Objects.nonNull(preUpdateId))
						history.setUpdateColumns(mdrHistoryRepository.getUpdatedColumns(
		            			preUpdateId, history.getId()).split(","));
				}
			}
			return history;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	public DashboardData getMDRDashboardData(String publicIp, String browser, Integer userId, String userType, Integer userTypeId,
			Integer featureId) {
		try {
			DashboardData result = mdrRepository.getDashboardData();
			
			AuditTrail auditTrail = new AuditTrail();
			auditTrail.setFeatureName("Mobile Device Repository");
			auditTrail.setSubFeature("Dashbaord");
			auditTrail.setFeatureId(featureId);
			auditTrail.setPublicIp(publicIp);
			auditTrail.setBrowser(browser);
			User user = userRepository.getByid(userId);
			auditTrail.setUserId(userId);
			auditTrail.setUserName( user.getUsername());
			auditTrail.setUserType(userType);
			auditTrail.setRoleType(userType);
			auditTrail.setTxnId("NA");
			auditTrailRepository.save(auditTrail);
			
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public List<UserDetails> getUserData() {
		// TODO Auto-generated method stub
		try {
			User user;
			
			List<String> uId=mdrRepository.findDistinctUserId();
			
			List<UserDetails> idList= new ArrayList<>();
			
			UserDetails idObj;
			
			for(String usrId:uId) {
				
				user = userRepository.getByid(Integer.parseInt(usrId));
				
				idObj =new UserDetails();
				idObj.setUserId(Long.parseLong(usrId));
				idObj.setUserName(user.getUserProfile().getFirstName() +" "+user.getUserProfile().getLastName());
				
				idList.add(idObj);
				
			}
			return idList;	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public List<String> getDeviceType() {
		// TODO Auto-generated method stub
		List<String> deviceType=mdrRepository.findDistinctDeviceType();
		return deviceType;
	}

}