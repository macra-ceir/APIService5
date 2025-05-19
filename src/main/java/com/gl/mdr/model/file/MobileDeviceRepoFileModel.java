package com.gl.mdr.model.file;

import java.time.format.DateTimeFormatter;

import com.gl.mdr.model.app.MobileDeviceRepositoryNoFile;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class MobileDeviceRepoFileModel {
		
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;

	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;

	@CsvBindByName(column = "Device ID")
	@CsvBindByPosition(position = 2)
	private String deviceId;
	
	@CsvBindByName(column = "Marketing Name")
	@CsvBindByPosition(position = 3)
	private String marketingName;
	
	@CsvBindByName(column = "Manufacturer")
	@CsvBindByPosition(position = 4)
	private String manufacturer;
	
	@CsvBindByName(column = "Manufacturing Location")
	@CsvBindByPosition(position = 5)
	private String manufacturingLocation;
	
	@CsvBindByName(column = "Model Name")
	@CsvBindByPosition(position = 6)
	private String modelName;
	
	@CsvBindByName(column = "Brand Name")
	@CsvBindByPosition(position = 7)
	private String brandName;
	
	@CsvBindByName(column = "OEM")
	@CsvBindByPosition(position = 8)
	private String oem;
	
	@CsvBindByName(column = "Organization ID")
	@CsvBindByPosition(position = 9)
	private String organizationId;
	
	@CsvBindByName(column = "Device Type")
	@CsvBindByPosition(position = 10)
	private String deviceType;
	
	@CsvBindByName(column = "Allocation Date")
	@CsvBindByPosition(position = 11)
	private String allocationDate;
	
	@CsvBindByName(column = "IMEI Quantity")
	@CsvBindByPosition(position = 12)
	private int imeiQuantity;
	
	@CsvBindByName(column = "SIM Slot")
	@CsvBindByPosition(position = 13)
	private int simSlot;
	
	@CsvBindByName(column = "ESIM Support")
	@CsvBindByPosition(position = 14)
	private String esimSupport;
	
	@CsvBindByName(column = "Soft SIM Support")
	@CsvBindByPosition(position = 15)
	private String softsimSupport;
	
	@CsvBindByName(column = "SIM Type")
	@CsvBindByPosition(position = 16)
	private String simType;
	
	@CsvBindByName(column = "OS")
	@CsvBindByPosition(position = 17)
	private String os;
	
	@CsvBindByName(column = "OS Base Version")
	@CsvBindByPosition(position = 18)
	private String osBaseVersion;
	
	@CsvBindByName(column = "OS Current Version")
	@CsvBindByPosition(position = 19)
	private String osCurrentVersion;
	
	@CsvBindByName(column = "Announce Date")
	@CsvBindByPosition(position = 20)
	private String announceDate;
	
	@CsvBindByName(column = "Launch Date")
	@CsvBindByPosition(position = 21)
	private String launchDate;
	
	@CsvBindByName(column = "Device Status")
	@CsvBindByPosition(position = 22)
	private String deviceStatus;
	
	@CsvBindByName(column = "Discontinue Date")
	@CsvBindByPosition(position = 23)
	private String discontinueDate;
	
	@CsvBindByName(column = "Network Technology GSM")
	@CsvBindByPosition(position = 24)
	private String networkTechnologyGSM;
	
	@CsvBindByName(column = "Network Technology CDMA")
	@CsvBindByPosition(position = 25)
	private String networkTechnologyCDMA;
	
	@CsvBindByName(column = "Network Technology EVDO")
	@CsvBindByPosition(position = 26)
	private String networkTechnologyEVDO;
	
	@CsvBindByName(column = "Network Technology LTE")
	@CsvBindByPosition(position = 27)
	private String networkTechnologyLTE;
	
	@CsvBindByName(column = "Network Technology 5G")
	@CsvBindByPosition(position = 28)
	private String networkTechnology5G;
	
	@CsvBindByName(column = "Network Technology 6G")
	@CsvBindByPosition(position = 29)
	private String networkTechnology6G;
	
	@CsvBindByName(column = "Network Technology 7G")
	@CsvBindByPosition(position = 30)
	private String networkTechnology7G;
	
	@CsvBindByName(column = "Network 2G Band")
	@CsvBindByPosition(position = 31)
	private String network2GBand;
	
	@CsvBindByName(column = "Network 3G Band")
	@CsvBindByPosition(position = 32)
	private String network3GBand;
	
	@CsvBindByName(column = "Network 4G Band")
	@CsvBindByPosition(position = 33)
	private String network4GBand;
	
	@CsvBindByName(column = "Network 5G Band")
	@CsvBindByPosition(position = 34)
	private String network5GBand;
	
	@CsvBindByName(column = "Network 6G Band")
	@CsvBindByPosition(position = 35)
	private String network6GBand;
	
	@CsvBindByName(column = "Network 7G Band")
	@CsvBindByPosition(position = 36)
	private String network7GBand;
	
	@CsvBindByName(column = "Network Speed")
	@CsvBindByPosition(position = 37)
	private String networkSpeed;
	
	@CsvBindByName(column = "Body Dimensions")
	@CsvBindByPosition(position = 38)
	private String bodyDimensions;
	
	@CsvBindByName(column = "Body Weight")
	@CsvBindByPosition(position = 39)
	private String bodyWeight;
	
	@CsvBindByName(column = "Display Type")
	@CsvBindByPosition(position = 40)
	private String displayType;
	
	@CsvBindByName(column = "Display Size")
	@CsvBindByPosition(position = 41)
	private String displaySize;
	
	@CsvBindByName(column = "Display Resolution")
	@CsvBindByPosition(position = 42)
	private String displayResolution;
	
	@CsvBindByName(column = "Display Protection")
	@CsvBindByPosition(position = 43)
	private String displayProtection;
	
	@CsvBindByName(column = "Platform Chipset")
	@CsvBindByPosition(position = 44)
	private String platformChipset;
	
	@CsvBindByName(column = "Platform CPU")
	@CsvBindByPosition(position = 45)
	private String platformCPU;
	
	@CsvBindByName(column = "Platform GPU")
	@CsvBindByPosition(position = 46)
	private String platformGPU;
	
	@CsvBindByName(column = "Memory Card Slot")
	@CsvBindByPosition(position = 47)
	private String memoryCardSlot;
	
	@CsvBindByName(column = "Memory Internal")
	@CsvBindByPosition(position = 48)
	private Integer memoryInternal;
	
	@CsvBindByName(column = "RAM")
	@CsvBindByPosition(position = 49)
	private String ram;
	
	@CsvBindByName(column = "Main Camera Type")
	@CsvBindByPosition(position = 50)
	private String mainCameraType;
	
	@CsvBindByName(column = "Main Camera Spec")
	@CsvBindByPosition(position = 51)
	private String mainCameraSpec;
	
	@CsvBindByName(column = "Main Camera Feature")
	@CsvBindByPosition(position = 52)
	private String mainCameraFeature;
	
	@CsvBindByName(column = "Main Camera Video")
	@CsvBindByPosition(position = 53)
	private String mainCameraVideo;
	
	@CsvBindByName(column = "Selfie Camera Type")
	@CsvBindByPosition(position = 54)
	private String selfieCameraType;
	
	@CsvBindByName(column = "Selfie Camera Spec")
	@CsvBindByPosition(position = 55)
	private String selfieCameraSpec;
	
	@CsvBindByName(column = "Selfie Camera Feature")
	@CsvBindByPosition(position = 56)
	private String selfieCameraFeature;
	
	@CsvBindByName(column = "Selfie Camera Video")
	@CsvBindByPosition(position = 57)
	private String selfieCameraVideo;
	
	@CsvBindByName(column = "Sound Loudspeaker")
	@CsvBindByPosition(position = 58)
	private String soundLoudspeaker;
	
	@CsvBindByName(column = "Sound 3.5mm Jack")
	@CsvBindByPosition(position = 59)
	private String sound35mmJack;
	
	@CsvBindByName(column = "Comms WLAN")
	@CsvBindByPosition(position = 60)
	private String commsWLAN;
	
	@CsvBindByName(column = "Comms Bluetooth")
	@CsvBindByPosition(position = 61)
	private String commsBluetooth;
	
	@CsvBindByName(column = "Comms GPS")
	@CsvBindByPosition(position = 62)
	private String commsGPS;
	
	@CsvBindByName(column = "Comms NFC")
	@CsvBindByPosition(position = 63)
	private String commsNFC;
	
	@CsvBindByName(column = "Comms Radio")
	@CsvBindByPosition(position = 64)
	private String commsRadio;
	
	@CsvBindByName(column = "Comms USB")
	@CsvBindByPosition(position = 65)
	private String commsUSB;
	
	@CsvBindByName(column = "Sensor")
	@CsvBindByPosition(position = 66)
	private String sensor;
	
	@CsvBindByName(column = "Battery Type")
	@CsvBindByPosition(position = 67)
	private String batteryType;
	
	@CsvBindByName(column = "Battery Capacity")
	@CsvBindByPosition(position = 68)
	private Integer batteryCapacity;
	
	@CsvBindByName(column = "Battery Charging")
	@CsvBindByPosition(position = 69)
	private String batteryCharging;
	
	@CsvBindByName(column = "Color")
	@CsvBindByPosition(position = 70)
	private String colors;
	
	@CsvBindByName(column = "Removable UICC")
	@CsvBindByPosition(position = 71)
	private Integer removableUICC;
	
	@CsvBindByName(column = "Removable EUICC")
	@CsvBindByPosition(position = 72)
	private Integer removableEUICC;
	
	@CsvBindByName(column = "Non Removable UICC")
	@CsvBindByPosition(position = 73)
	private Integer nonremovableUICC;
	
	@CsvBindByName(column = "Non Removable EUICC")
	@CsvBindByPosition(position = 74)
	private Integer nonremovableEUICC;
	
	@CsvBindByName(column = "Brand Detail")
	@CsvBindByPosition(position = 75)
	private String bandDetail;
	
	@CsvBindByName(column = "Launch Price Asian Market")
	@CsvBindByPosition(position = 76)
	private Double launchPriceAsianMarket;
	
	@CsvBindByName(column = "Launch Price US Market")
	@CsvBindByPosition(position = 77)
	private Double launchPriceUSMarket;
	
	@CsvBindByName(column = "Launch Price Europe Market")
	@CsvBindByPosition(position = 78)
	private Double launchPriceEuropeMarket;
	
	@CsvBindByName(column = "Launch Price International Market")
	@CsvBindByPosition(position = 79)
	private Double launchPriceInternationalMarket;
	
	@CsvBindByName(column = "Launch Price Cambodia Market")
	@CsvBindByPosition(position = 80)
	private Double launchPriceCambodiaMarket;
	
	@CsvBindByName(column = "Custom Price")
	@CsvBindByPosition(position = 81)
	private Double customPriceOfDevice;
	
	@CsvBindByName(column = "Source Of Cambodia Market Price")
	@CsvBindByPosition(position = 82)
	private String sourceOfCambodiaMarketPrice;
	
	@CsvBindByName(column = "Reported Global Issue")
	@CsvBindByPosition(position = 83)
	private String reportedGlobalIssue;
	
	@CsvBindByName(column = "Reported Local Issue")
	@CsvBindByPosition(position = 84)
	private String reportedLocalIssue;
	
	@CsvBindByName(column = "Device State")
	@CsvBindByPosition(position = 85)
	private String deviceState;
	
	@CsvBindByName(column = "Is Test IMEI")
	@CsvBindByPosition(position = 86)
	private Integer isTestImei;
	
	@CsvBindByName(column = "Network Specific Identifier")
	@CsvBindByPosition(position = 87)
	private Integer networkSpecificIdentifier;
	
	public MobileDeviceRepoFileModel() {}
	
	public MobileDeviceRepoFileModel(MobileDeviceRepositoryNoFile mdr) {
		DateTimeFormatter dtf        = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if(mdr.getCreatedOn() != null) {
			this.createdOn           = mdr.getCreatedOn().format(dtf);
		}else{
			this.createdOn           = "";
		}
		if(mdr.getModifiedOn() != null) {
			this.modifiedOn          = mdr.getModifiedOn().format(dtf);
		}else{
			this.modifiedOn          = "";
		}
		this.deviceId                = mdr.getDeviceId();
		this.marketingName           = mdr.getMarketingName();
		this.manufacturer            = mdr.getManufacturer();
		this.manufacturingLocation   = mdr.getManufacturingLocation();
		this.modelName               = mdr.getModelName();
		this.brandName               = mdr.getBrandName();
		this.oem                     = mdr.getOem();
		this.organizationId          = mdr.getOrganizationId();
		this.deviceType              = mdr.getDeviceType();
		if(mdr.getAllocationDate() != null) {
			this.allocationDate      = mdr.getAllocationDate().format(dtf);
		}else{
			this.allocationDate      = "";
		}
		this.imeiQuantity            = mdr.getImeiQuantity();
		this.simSlot                 = mdr.getSimSlot();
		this.esimSupport             = mdr.getEsimSupportInterp();
		this.softsimSupport          = mdr.getSoftsimSupportInterp();
		this.simType                 = mdr.getSimType();
		this.os                      = mdr.getOs();
		this.osBaseVersion           = mdr.getOsBaseVersion();
		this.osCurrentVersion        = mdr.getOsCurrentVersion();
		if(mdr.getAnnounceDate() != null) {
			this.announceDate        = mdr.getAnnounceDate().format(dtf);
		}else{
			this.announceDate        = "";
		}
		if(mdr.getLaunchDate() != null) {
			this.launchDate          = mdr.getLaunchDate().format(dtf);
		}else{
			this.launchDate          = "";
		}
		this.deviceStatus            = mdr.getDeviceStatus();
		if(mdr.getDiscontinueDate() != null) {
			this.discontinueDate     = mdr.getDiscontinueDate().format(dtf);
		}else{
			this.discontinueDate     = "";
		}
		this.networkTechnologyGSM    = mdr.getNetworkTechnologyGSMInterp();
		this.networkTechnologyCDMA   = mdr.getNetworkTechnologyCDMAInterp();
		this.networkTechnologyEVDO   = mdr.getNetworkTechnologyEVDOInterp();
		this.networkTechnologyLTE    = mdr.getNetworkTechnologyLTEInterp();
		this.networkTechnology5G     = mdr.getNetworkTechnology5GInterp();
		this.networkTechnology6G     = mdr.getNetworkTechnology6GInterp();
		this.networkTechnology7G     = mdr.getNetworkTechnology7GInterp();
		this.network2GBand           = mdr.getNetwork2GBand();
		this.network3GBand           = mdr.getNetwork3GBand();
		this.network4GBand           = mdr.getNetwork4GBand();
		this.network5GBand           = mdr.getNetwork5GBand();
		this.network6GBand           = mdr.getNetwork6GBand();
		this.network7GBand           = mdr.getNetwork7GBand();
		this.networkSpeed            = mdr.getNetworkSpeed();
		this.bodyDimensions          = mdr.getBodyDimension();
		this.bodyWeight              = mdr.getBodyWeight();
		this.displayType             = mdr.getDisplayType();
		this.displaySize             = mdr.getDisplaySize();
		this.displayResolution       = mdr.getDisplayResolution();
		this.displayProtection       = mdr.getDisplayProtection();
		this.platformChipset         = mdr.getPlatformChipset();
		this.platformCPU             = mdr.getPlatformCPU();
		this.platformGPU             = mdr.getPlatformGPU();
		this.memoryCardSlot          = mdr.getMemoryCardSlotInterp();
		this.memoryInternal          = mdr.getMemoryInternal();
		this.ram                     = mdr.getRam();
		this.mainCameraType          = mdr.getMainCameraTypeInterp();
		this.mainCameraSpec          = mdr.getMainCameraSpec();
		this.mainCameraFeature       = mdr.getMainCameraFeature();
		this.mainCameraVideo         = mdr.getMainCameraVideo();
		this.selfieCameraType        = mdr.getSelfieCameraTypeInterp();
		this.selfieCameraSpec        = mdr.getSelfieCameraSpec();
		this.selfieCameraFeature     = mdr.getSelfieCameraFeature();
		this.selfieCameraVideo       = mdr.getSelfieCameraVideo();
		this.soundLoudspeaker        = mdr.getSoundLoudspeakerInterp();
		this.sound35mmJack           = mdr.getSound35mmJackInterp();
		this.commsWLAN               = mdr.getCommsWLAN();
		this.commsBluetooth          = mdr.getCommsBluetooth();
		this.commsGPS                = mdr.getCommsGPS();
		this.commsNFC                = mdr.getCommsNFCInterp();
		this.commsRadio              = mdr.getCommsRadioInterp();
		this.commsUSB                = mdr.getCommsUSB();
		this.sensor                  = mdr.getSensor();
		this.batteryType             = mdr.getBatteryType();
		this.batteryCapacity         = mdr.getBatteryCapacity();
		this.batteryCharging         = mdr.getBatteryCharging();
		this.colors                  = mdr.getColors();
		this.removableUICC           = mdr.getRemovableUICC();
		this.removableEUICC          = mdr.getRemovableEUICC();
		this.nonremovableUICC        = mdr.getNonremovableUICC();
		this.nonremovableEUICC       = mdr.getNonremovableEUICC();
		this.bandDetail              = mdr.getBandDetail();
		this.launchPriceAsianMarket  = mdr.getLaunchPriceAsianMarket();
		this.launchPriceUSMarket     = mdr.getLaunchPriceUSMarket();
		this.launchPriceEuropeMarket = mdr.getLaunchPriceEuropeMarket();
		this.launchPriceInternationalMarket = mdr.getLaunchPriceInternationalMarket();
		this.launchPriceCambodiaMarket      = mdr.getLaunchPriceCambodiaMarket();
		this.customPriceOfDevice            = mdr.getCustomPriceOfDevice();
		this.sourceOfCambodiaMarketPrice    = mdr.getSourceOfCambodiaMarketPrice();
		this.reportedGlobalIssue = mdr.getReportedGlobalIssue();
		this.reportedLocalIssue  = mdr.getReportedLocalIssue();
		this.deviceState = mdr.getStateInterp();
		this.isTestImei  = mdr.getIsTestImei();
		this.networkSpecificIdentifier=mdr.getNetworkSpecificIdentifier();
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getManufacturingLocation() {
		return manufacturingLocation;
	}

	public void setManufacturingLocation(String manufacturingLocation) {
		this.manufacturingLocation = manufacturingLocation;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getOem() {
		return oem;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}

	public int getImeiQuantity() {
		return imeiQuantity;
	}

	public void setImeiQuantity(int imeiQuantity) {
		this.imeiQuantity = imeiQuantity;
	}

	public int getSimSlot() {
		return simSlot;
	}

	public void setSimSlot(int simSlot) {
		this.simSlot = simSlot;
	}

	public String getEsimSupport() {
		return esimSupport;
	}

	public void setEsimSupport(String esimSupport) {
		this.esimSupport = esimSupport;
	}

	public String getSoftsimSupport() {
		return softsimSupport;
	}

	public void setSoftsimSupport(String softsimSupport) {
		this.softsimSupport = softsimSupport;
	}

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsCurrentVersion() {
		return osCurrentVersion;
	}

	public void setOsCurrentVersion(String osCurrentVersion) {
		this.osCurrentVersion = osCurrentVersion;
	}

	public String getOsBaseVersion() {
		return osBaseVersion;
	}

	public void setOsBaseVersion(String osBaseVersion) {
		this.osBaseVersion = osBaseVersion;
	}

	public String getAnnounceDate() {
		return announceDate;
	}

	public void setAnnounceDate(String announceDate) {
		this.announceDate = announceDate;
	}

	public String getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDiscontinueDate() {
		return discontinueDate;
	}

	public void setDiscontinueDate(String discontinueDate) {
		this.discontinueDate = discontinueDate;
	}

	public String getNetworkTechnologyGSM() {
		return networkTechnologyGSM;
	}

	public void setNetworkTechnologyGSM(String networkTechnologyGSM) {
		this.networkTechnologyGSM = networkTechnologyGSM;
	}

	public String getNetworkTechnologyCDMA() {
		return networkTechnologyCDMA;
	}

	public void setNetworkTechnologyCDMA(String networkTechnologyCDMA) {
		this.networkTechnologyCDMA = networkTechnologyCDMA;
	}

	public String getNetworkTechnologyEVDO() {
		return networkTechnologyEVDO;
	}

	public void setNetworkTechnologyEVDO(String networkTechnologyEVDO) {
		this.networkTechnologyEVDO = networkTechnologyEVDO;
	}

	public String getNetworkTechnologyLTE() {
		return networkTechnologyLTE;
	}

	public void setNetworkTechnologyLTE(String networkTechnologyLTE) {
		this.networkTechnologyLTE = networkTechnologyLTE;
	}

	public String getNetworkTechnology5G() {
		return networkTechnology5G;
	}

	public void setNetworkTechnology5G(String networkTechnology5G) {
		this.networkTechnology5G = networkTechnology5G;
	}

	public String getNetworkTechnology6G() {
		return networkTechnology6G;
	}

	public void setNetworkTechnology6G(String networkTechnology6G) {
		this.networkTechnology6G = networkTechnology6G;
	}

	public String getNetworkTechnology7G() {
		return networkTechnology7G;
	}

	public void setNetworkTechnology7G(String networkTechnology7G) {
		this.networkTechnology7G = networkTechnology7G;
	}

	public String getNetwork2GBand() {
		return network2GBand;
	}

	public void setNetwork2GBand(String network2gBand) {
		network2GBand = network2gBand;
	}

	public String getNetwork3GBand() {
		return network3GBand;
	}

	public void setNetwork3GBand(String network3gBand) {
		network3GBand = network3gBand;
	}

	public String getNetwork4GBand() {
		return network4GBand;
	}

	public void setNetwork4GBand(String network4gBand) {
		network4GBand = network4gBand;
	}

	public String getNetwork5GBand() {
		return network5GBand;
	}

	public void setNetwork5GBand(String network5gBand) {
		network5GBand = network5gBand;
	}

	public String getNetwork6GBand() {
		return network6GBand;
	}

	public void setNetwork6GBand(String network6gBand) {
		network6GBand = network6gBand;
	}

	public String getNetwork7GBand() {
		return network7GBand;
	}

	public void setNetwork7GBand(String network7gBand) {
		network7GBand = network7gBand;
	}

	public String getNetworkSpeed() {
		return networkSpeed;
	}

	public void setNetworkSpeed(String networkSpeed) {
		this.networkSpeed = networkSpeed;
	}

	public String getBodyDimensions() {
		return bodyDimensions;
	}

	public void setBodyDimensions(String bodyDimensions) {
		this.bodyDimensions = bodyDimensions;
	}

	public String getBodyWeight() {
		return bodyWeight;
	}

	public void setBodyWeight(String bodyWeight) {
		this.bodyWeight = bodyWeight;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(String displaySize) {
		this.displaySize = displaySize;
	}

	public String getDisplayResolution() {
		return displayResolution;
	}

	public void setDisplayResolution(String displayResolution) {
		this.displayResolution = displayResolution;
	}

	public String getDisplayProtection() {
		return displayProtection;
	}

	public void setDisplayProtection(String displayProtection) {
		this.displayProtection = displayProtection;
	}

	public String getPlatformChipset() {
		return platformChipset;
	}

	public void setPlatformChipset(String platformChipset) {
		this.platformChipset = platformChipset;
	}

	public String getPlatformCPU() {
		return platformCPU;
	}

	public void setPlatformCPU(String platformCPU) {
		this.platformCPU = platformCPU;
	}

	public String getPlatformGPU() {
		return platformGPU;
	}

	public void setPlatformGPU(String platformGPU) {
		this.platformGPU = platformGPU;
	}

	public String getMemoryCardSlot() {
		return memoryCardSlot;
	}

	public void setMemoryCardSlot(String memoryCardSlot) {
		this.memoryCardSlot = memoryCardSlot;
	}

	public Integer getMemoryInternal() {
		return memoryInternal;
	}

	public void setMemoryInternal(Integer memoryInternal) {
		this.memoryInternal = memoryInternal;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getMainCameraType() {
		return mainCameraType;
	}

	public void setMainCameraType(String mainCameraType) {
		this.mainCameraType = mainCameraType;
	}

	public String getMainCameraSpec() {
		return mainCameraSpec;
	}

	public void setMainCameraSpec(String mainCameraSpec) {
		this.mainCameraSpec = mainCameraSpec;
	}

	public String getMainCameraFeature() {
		return mainCameraFeature;
	}

	public void setMainCameraFeature(String mainCameraFeature) {
		this.mainCameraFeature = mainCameraFeature;
	}

	public String getMainCameraVideo() {
		return mainCameraVideo;
	}

	public void setMainCameraVideo(String mainCameraVideo) {
		this.mainCameraVideo = mainCameraVideo;
	}

	public String getSelfieCameraType() {
		return selfieCameraType;
	}

	public void setSelfieCameraType(String selfieCameraType) {
		this.selfieCameraType = selfieCameraType;
	}

	public String getSelfieCameraSpec() {
		return selfieCameraSpec;
	}

	public void setSelfieCameraSpec(String selfieCameraSpec) {
		this.selfieCameraSpec = selfieCameraSpec;
	}

	public String getSelfieCameraFeature() {
		return selfieCameraFeature;
	}

	public void setSelfieCameraFeature(String selfieCameraFeature) {
		this.selfieCameraFeature = selfieCameraFeature;
	}

	public String getSelfieCameraVideo() {
		return selfieCameraVideo;
	}

	public void setSelfieCameraVideo(String selfieCameraVideo) {
		this.selfieCameraVideo = selfieCameraVideo;
	}

	public String getSoundLoudspeaker() {
		return soundLoudspeaker;
	}

	public void setSoundLoudspeaker(String soundLoudspeaker) {
		this.soundLoudspeaker = soundLoudspeaker;
	}

	public String getSound35mmJack() {
		return sound35mmJack;
	}

	public void setSound35mmJack(String sound35mmJack) {
		this.sound35mmJack = sound35mmJack;
	}

	public String getCommsWLAN() {
		return commsWLAN;
	}

	public void setCommsWLAN(String commsWLAN) {
		this.commsWLAN = commsWLAN;
	}

	public String getCommsBluetooth() {
		return commsBluetooth;
	}

	public void setCommsBluetooth(String commsBluetooth) {
		this.commsBluetooth = commsBluetooth;
	}

	public String getCommsGPS() {
		return commsGPS;
	}

	public void setCommsGPS(String commsGPS) {
		this.commsGPS = commsGPS;
	}

	public String getCommsNFC() {
		return commsNFC;
	}

	public void setCommsNFC(String commsNFC) {
		this.commsNFC = commsNFC;
	}

	public String getCommsRadio() {
		return commsRadio;
	}

	public void setCommsRadio(String commsRadio) {
		this.commsRadio = commsRadio;
	}

	public String getCommsUSB() {
		return commsUSB;
	}

	public void setCommsUSB(String commsUSB) {
		this.commsUSB = commsUSB;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getBatteryType() {
		return batteryType;
	}

	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}

	public Integer getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(Integer batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public String getBatteryCharging() {
		return batteryCharging;
	}

	public void setBatteryCharging(String batteryCharging) {
		this.batteryCharging = batteryCharging;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public Integer getRemovableUICC() {
		return removableUICC;
	}

	public void setRemovableUICC(Integer removableUICC) {
		this.removableUICC = removableUICC;
	}

	public Integer getRemovableEUICC() {
		return removableEUICC;
	}

	public void setRemovableEUICC(Integer removableEUICC) {
		this.removableEUICC = removableEUICC;
	}

	public Integer getNonremovableUICC() {
		return nonremovableUICC;
	}

	public void setNonremovableUICC(Integer nonremovableUICC) {
		this.nonremovableUICC = nonremovableUICC;
	}

	public Integer getNonremovableEUICC() {
		return nonremovableEUICC;
	}

	public void setNonremovableEUICC(Integer nonremovableEUICC) {
		this.nonremovableEUICC = nonremovableEUICC;
	}

	public String getBandDetail() {
		return bandDetail;
	}

	public void setBandDetail(String bandDetail) {
		this.bandDetail = bandDetail;
	}

	public Double getLaunchPriceAsianMarket() {
		return launchPriceAsianMarket;
	}

	public void setLaunchPriceAsianMarket(Double launchPriceAsianMarket) {
		this.launchPriceAsianMarket = launchPriceAsianMarket;
	}

	public Double getLaunchPriceUSMarket() {
		return launchPriceUSMarket;
	}

	public void setLaunchPriceUSMarket(Double launchPriceUSMarket) {
		this.launchPriceUSMarket = launchPriceUSMarket;
	}

	public Double getLaunchPriceEuropeMarket() {
		return launchPriceEuropeMarket;
	}

	public void setLaunchPriceEuropeMarket(Double launchPriceEuropeMarket) {
		this.launchPriceEuropeMarket = launchPriceEuropeMarket;
	}

	public Double getLaunchPriceInternationalMarket() {
		return launchPriceInternationalMarket;
	}

	public void setLaunchPriceInternationalMarket(Double launchPriceInternationalMarket) {
		this.launchPriceInternationalMarket = launchPriceInternationalMarket;
	}

	public Double getLaunchPriceCambodiaMarket() {
		return launchPriceCambodiaMarket;
	}

	public void setLaunchPriceCambodiaMarket(Double launchPriceCambodiaMarket) {
		this.launchPriceCambodiaMarket = launchPriceCambodiaMarket;
	}

	public Double getCustomPriceOfDevice() {
		return customPriceOfDevice;
	}

	public void setCustomPriceOfDevice(Double customPriceOfDevice) {
		this.customPriceOfDevice = customPriceOfDevice;
	}

	public String getSourceOfCambodiaMarketPrice() {
		return sourceOfCambodiaMarketPrice;
	}

	public void setSourceOfCambodiaMarketPrice(String sourceOfCambodiaMarketPrice) {
		this.sourceOfCambodiaMarketPrice = sourceOfCambodiaMarketPrice;
	}

	public String getReportedGlobalIssue() {
		return reportedGlobalIssue;
	}

	public void setReportedGlobalIssue(String reportedGlobalIssue) {
		this.reportedGlobalIssue = reportedGlobalIssue;
	}

	public String getReportedLocalIssue() {
		return reportedLocalIssue;
	}

	public void setReportedLocalIssue(String reportedLocalIssue) {
		this.reportedLocalIssue = reportedLocalIssue;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}

	public Integer getIsTestImei() {
		return isTestImei;
	}

	public void setIsTestImei(Integer isTestImei) {
		this.isTestImei = isTestImei;
	}

	public Integer getNetworkSpecificIdentifier() {
		return networkSpecificIdentifier;
	}

	public void setNetworkSpecificIdentifier(Integer networkSpecificIdentifier) {
		this.networkSpecificIdentifier = networkSpecificIdentifier;
	}
	
	
}
