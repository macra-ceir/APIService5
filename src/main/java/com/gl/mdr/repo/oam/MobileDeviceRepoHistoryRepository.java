package com.gl.mdr.repo.oam;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.oam.MobileDeviceRepositoryHistory;

@Repository
public interface MobileDeviceRepoHistoryRepository extends 
JpaRepository<MobileDeviceRepositoryHistory, Integer>, JpaSpecificationExecutor<MobileDeviceRepositoryHistory>{
	
	public MobileDeviceRepositoryHistory save(MobileDeviceRepositoryHistory mobileDeviceRepositoryHistory);
	
	public List<MobileDeviceRepositoryHistory> getByDeviceId(String deviceId);
	
	@Query(value="select CONCAT(CASE WHEN (a.allocationDate IS NULL and b.allocationDate IS NOT NULL) or "
			+ "(a.allocationDate <> b.allocationDate) THEN 'allocationDate' ELSE 0 END, ',',"
			+ " CASE WHEN (a.announceDate IS NULL and b.announceDate IS NOT NULL) or "
			+ "(a.announceDate <> b.announceDate) THEN 'announceDate' ELSE 0 END, ',',"
			+ " CASE WHEN (a.bandDetail IS NULL and b.bandDetail IS NOT NULL) or "
			+ "(a.bandDetail <> b.bandDetail) THEN 'bandDetail' ELSE 0 END, ',',"
			+ " CASE WHEN (a.batteryCapacity <> b.batteryCapacity) THEN 'batteryCapacity' ELSE 0 END, ',',"
			+ " CASE WHEN (a.batteryCharging <> b.batteryCharging) THEN 'batteryCharging' ELSE 0 END, ',',"
			+ " CASE WHEN (a.batteryType <> b.batteryType) THEN 'batteryType' ELSE 0 END, ',',"
			+ " CASE WHEN (a.bodyDimension <> b.bodyDimension) THEN 'bodyDimension' ELSE 0 END, ',',"
			+ " CASE WHEN (a.bodyWeight <> b.bodyWeight) THEN 'bodyWeight' ELSE 0 END, ',',"
			+ " CASE WHEN (a.brandName <> b.brandName) THEN 'brandName' ELSE 0 END, ',',"
			+ " CASE WHEN (a.colors <> b.colors) THEN 'colors' ELSE 0 END, ',',"
			+ " CASE WHEN (a.commsBluetooth <> b.commsBluetooth) THEN 'commsBluetooth' ELSE 0 END, ',',"
			+ " CASE WHEN (a.commsGPS <> b.commsGPS) THEN 'commsGPS' ELSE 0 END, ',',"
			+ " CASE WHEN (a.commsNFC <> b.commsNFC) THEN 'commsNFC,commsNFCInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.commsRadio <> b.commsRadio) THEN 'commsRadio,commsRadioInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.commsUSB <> b.commsUSB) THEN 'commsUSB' ELSE 0 END, ',',"
			+ " CASE WHEN (a.commsWLAN <> b.commsWLAN) THEN 'commsWLAN' ELSE 0 END, ',',"
			+ " CASE WHEN (a.modifiedOn <> b.modifiedOn) THEN 'modifiedOn' ELSE 0 END, ',',"
			+ " CASE WHEN (a.marketingName <> b.marketingName) THEN 'marketingName' ELSE 0 END, ',',"
			//+ " CASE WHEN (a.userDisplayName <> b.userDisplayName) THEN 'userDisplayName' ELSE 0 END, ',',"
			+ " CASE WHEN (a.manufacturer <> b.manufacturer) THEN 'manufacturer' ELSE 0 END, ',',"
			+ " CASE WHEN (a.manufacturingLocation <> b.manufacturingLocation) THEN 'manufacturingLocation' ELSE 0 END, ',',"
			+ " CASE WHEN (a.modelName <> b.modelName) THEN 'modelName' ELSE 0 END, ',',"
			+ " CASE WHEN (a.oem <> b.oem) THEN 'oem' ELSE 0 END, ',',"
			+ " CASE WHEN (a.organizationId <> b.organizationId) THEN 'organizationId' ELSE 0 END, ',',"
			+ " CASE WHEN (a.deviceType <> b.deviceType) THEN 'deviceType' ELSE 0 END, ',',"
			+ " CASE WHEN (a.imeiQuantity <> b.imeiQuantity) THEN 'imeiQuantity' ELSE 0 END, ',',"
			+ " CASE WHEN (a.simSlot <> b.simSlot) THEN 'simSlot' ELSE 0 END, ',',"
			+ " CASE WHEN (a.esimSupport <> b.esimSupport) THEN 'esimSupport,esimSupportInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.softsimSupport <> b.softsimSupport) THEN 'softsimSupport,softsimSupportInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.simType <> b.simType) THEN 'simType' ELSE 0 END, ',',"
			+ " CASE WHEN (a.os <> b.os) THEN 'os' ELSE 0 END, ',',"
			+ " CASE WHEN (a.osCurrentVersion <> b.osCurrentVersion) THEN 'osCurrentVersion' ELSE 0 END, ',',"
			+ " CASE WHEN (a.osBaseVersion <> b.osBaseVersion) THEN 'osBaseVersion' ELSE 0 END, ',',"
			+ " CASE WHEN (a.launchDate IS NULL and b.launchDate IS NOT NULL) or "
			+ "(a.launchDate <> b.launchDate) THEN 'launchDate' ELSE 0 END, ',',"
			+ " CASE WHEN (a.deviceStatus <> b.deviceStatus) THEN 'deviceStatus' ELSE 0 END, ',',"
			+ " CASE WHEN (a.discontinueDate IS NULL and b.discontinueDate IS NOT NULL) or "
			+ "(a.discontinueDate <> b.discontinueDate) THEN 'discontinueDate' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkTechnologyGSM <> b.networkTechnologyGSM) THEN "
			+ "'networkTechnologyGSM,networkTechnologyGSMInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkTechnologyCDMA <> b.networkTechnologyCDMA) THEN "
			+ "'networkTechnologyCDMA,networkTechnologyCDMAInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkTechnologyEVDO <> b.networkTechnologyEVDO) THEN "
			+ "'networkTechnologyEVDO,networkTechnologyEVDOInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkTechnologyLTE <> b.networkTechnologyLTE) THEN "
			+ "'networkTechnologyLTE,networkTechnologyLTEInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkTechnology5G <> b.networkTechnology5G) THEN "
			+ "'networkTechnology5G,networkTechnology5GInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkTechnology6G <> b.networkTechnology6G) THEN "
			+ "'networkTechnology6G,networkTechnology6GInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkTechnology7G <> b.networkTechnology7G) THEN "
			+ "'networkTechnology7G,networkTechnology7GInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.network2GBand <> b.network2GBand) THEN 'network2GBand' ELSE 0 END, ',',"
			+ " CASE WHEN (a.network3GBand <> b.network3GBand) THEN 'network3GBand' ELSE 0 END, ',',"
			+ " CASE WHEN (a.network4GBand <> b.network4GBand) THEN 'network4GBand' ELSE 0 END, ',',"
			+ " CASE WHEN (a.network5GBand <> b.network5GBand) THEN 'network5GBand' ELSE 0 END, ',',"
			+ " CASE WHEN (a.network6GBand <> b.network6GBand) THEN 'network6GBand' ELSE 0 END, ',',"
			+ " CASE WHEN (a.network7GBand <> b.network7GBand) THEN 'network7GBand' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkSpeed <> b.networkSpeed) THEN 'networkSpeed' ELSE 0 END, ',',"
			+ " CASE WHEN (a.displayType <> b.displayType) THEN 'displayType' ELSE 0 END, ',',"
			+ " CASE WHEN (a.displaySize <> b.displaySize) THEN 'displaySize' ELSE 0 END, ',',"
			+ " CASE WHEN (a.displayResolution <> b.displayResolution) THEN 'displayResolution' ELSE 0 END, ',',"
			+ " CASE WHEN (a.displayProtection <> b.displayProtection) THEN 'displayProtection' ELSE 0 END, ',',"
			+ " CASE WHEN (a.platformChipset <> b.platformChipset) THEN 'platformChipset' ELSE 0 END, ',',"
			+ " CASE WHEN (a.platformCPU <> b.platformCPU) THEN 'platformCPU' ELSE 0 END, ',',"
			+ " CASE WHEN (a.platformGPU <> b.platformGPU) THEN 'platformGPU' ELSE 0 END, ',',"
			+ " CASE WHEN (a.memoryCardSlot <> b.memoryCardSlot) THEN 'memoryCardSlot,memoryCardSlotInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.memoryInternal <> b.memoryInternal) THEN 'memoryInternal' ELSE 0 END, ',',"
			+ " CASE WHEN (a.ram <> b.ram) THEN 'ram' ELSE 0 END, ',',"
			+ " CASE WHEN (a.mainCameraType <> b.mainCameraType) THEN 'mainCameraType,mainCameraTypeInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.mainCameraSpec <> b.mainCameraSpec) THEN 'mainCameraSpec' ELSE 0 END, ',',"
			+ " CASE WHEN (a.mainCameraFeature <> b.mainCameraFeature) THEN 'mainCameraFeature' ELSE 0 END, ',',"
			+ " CASE WHEN (a.mainCameraVideo <> b.mainCameraVideo) THEN 'mainCameraVideo' ELSE 0 END, ',',"
			+ " CASE WHEN (a.selfieCameraType <> b.selfieCameraType) THEN "
			+ "'selfieCameraType,selfieCameraTypeInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.selfieCameraSpec <> b.selfieCameraSpec) THEN 'selfieCameraSpec' ELSE 0 END, ',',"
			+ " CASE WHEN (a.selfieCameraFeature <> b.selfieCameraFeature) THEN 'selfieCameraFeature' ELSE 0 END, ',',"
			+ " CASE WHEN (a.selfieCameraVideo <> b.selfieCameraVideo) THEN 'selfieCameraVideo' ELSE 0 END, ',',"
			+ " CASE WHEN (a.soundLoudspeaker <> b.soundLoudspeaker) THEN "
			+ "'soundLoudspeaker,soundLoudspeakerInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.sound35mmJack <> b.sound35mmJack) THEN 'sound35mmJack,sound35mmJackInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.sensor <> b.sensor) THEN 'sensor' ELSE 0 END, ',',"
			+ " CASE WHEN (a.removableUICC <> b.removableUICC) THEN 'removableUICC' ELSE 0 END, ',',"
			+ " CASE WHEN (a.removableEUICC <> b.removableEUICC) THEN 'removableEUICC' ELSE 0 END, ',',"
			+ " CASE WHEN (a.nonremovableUICC <> b.nonremovableUICC) THEN 'nonremovableUICC' ELSE 0 END, ',',"
			+ " CASE WHEN (a.nonremovableEUICC <> b.nonremovableEUICC) THEN 'nonremovableEUICC' ELSE 0 END, ',',"
			+ " CASE WHEN (a.launchPriceAsianMarket <> b.launchPriceAsianMarket) THEN 'launchPriceAsianMarket' ELSE 0 END, ',',"
			+ " CASE WHEN (a.launchPriceUSMarket <> b.launchPriceUSMarket) THEN 'launchPriceUSMarket' ELSE 0 END, ',',"
			+ " CASE WHEN (a.launchPriceEuropeMarket <> b.launchPriceEuropeMarket) THEN 'launchPriceEuropeMarket' ELSE 0 END, ',',"
			+ " CASE WHEN (a.launchPriceInternationalMarket <> b.launchPriceInternationalMarket) THEN"
			+ " 'launchPriceInternationalMarket' ELSE 0 END, ',',"
			+ " CASE WHEN (a.launchPriceCambodiaMarket <> b.launchPriceCambodiaMarket) THEN "
			+ "'launchPriceCambodiaMarket' ELSE 0 END, ',',"
			+ " CASE WHEN (a.customPriceOfDevice <> b.customPriceOfDevice) THEN 'customPriceOfDevice' ELSE 0 END, ',',"
			+ " CASE WHEN (a.sourceOfCambodiaMarketPrice <> b.sourceOfCambodiaMarketPrice) THEN "
			+ "'sourceOfCambodiaMarketPrice' ELSE 0 END, ',',"
			+ " CASE WHEN (a.reportedGlobalIssue <> b.reportedGlobalIssue) THEN 'reportedGlobalIssue' ELSE 0 END, ',',"
			+ " CASE WHEN (a.reportedLocalIssue <> b.reportedLocalIssue) THEN 'reportedLocalIssue' ELSE 0 END, ',',"
			+ " CASE WHEN (a.deviceState <> b.deviceState) THEN 'deviceState,stateInterp' ELSE 0 END, ',',"
			+ " CASE WHEN (a.userId <> b.userId) THEN 'userId,userDisplayName' ELSE 0 END, ',',"
			+ " CASE WHEN (a.remark <> b.remark) THEN 'remark' ELSE 0 END, ',',"
			+ " CASE WHEN (a.networkSpecificIdentifier <> b.networkSpecificIdentifier) THEN 'networkSpecificIdentifier' ELSE 0 END) as comparision "
			+ "from MobileDeviceRepositoryHistory as a, MobileDeviceRepositoryHistory as b where a.id =:firstId and b.id =:secondId") 
	public String getUpdatedColumns(@Param("firstId") Integer firstId, @Param("secondId") Integer secondId);
	@Query(value="select id from MobileDeviceRepositoryHistory as a where a.deviceId =:deviceId and a.id <:rowId "
			+ "order by a.id desc")
	public List<Integer> getPreHistoryId(@Param("rowId") Integer rowId, @Param("deviceId") String deviceId, Pageable pageable);
}
