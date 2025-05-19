package com.gl.mdr.model.filter;

public class MobileDeviceRepositoryFilterRequest {
	
	public String startDate;
	public String endDate;
	public String modifiedOn;
	public String deviceId;
	private String deviceType;
	private String deviceStatus;
	private Integer mdrStatus;
	private String marketingName;
	private String modelName;
	private String brandName;
	private String os;
	private String organizationId;
	private Integer networkTechnologyGSM;
	private Integer networkTechnologyCDMA;
	private Integer networkTechnologyLTE;
	private Integer networkTechnology5G;
	
	public Integer userId;
	public String userType;
	private String orderColumnName;
	private String order;
	private String publicIp;
	private String browser;
	private String searchString;
	private Integer featureId;
	private Integer userTypeId;
	private String userDisplayName;
	private String manufacturer;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public Integer getMdrStatus() {
		return mdrStatus;
	}
	public void setMdrStatus(Integer mdrStatus) {
		this.mdrStatus = mdrStatus;
	}
	public String getMarketingName() {
		return marketingName;
	}
	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
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
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getNetworkTechnologyGSM() {
		return networkTechnologyGSM;
	}
	public void setNetworkTechnologyGSM(Integer networkTechnologyGSM) {
		this.networkTechnologyGSM = networkTechnologyGSM;
	}
	public Integer getNetworkTechnologyCDMA() {
		return networkTechnologyCDMA;
	}
	public void setNetworkTechnologyCDMA(Integer networkTechnologyCDMA) {
		this.networkTechnologyCDMA = networkTechnologyCDMA;
	}
	public Integer getNetworkTechnologyLTE() {
		return networkTechnologyLTE;
	}
	public void setNetworkTechnologyLTE(Integer networkTechnologyLTE) {
		this.networkTechnologyLTE = networkTechnologyLTE;
	}
	public Integer getNetworkTechnology5G() {
		return networkTechnology5G;
	}
	public void setNetworkTechnology5G(Integer networkTechnology5G) {
		this.networkTechnology5G = networkTechnology5G;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getOrderColumnName() {
		return orderColumnName;
	}
	public void setOrderColumnName(String orderColumnName) {
		this.orderColumnName = orderColumnName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public Integer getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	
	public String getUserDisplayName() {
		return userDisplayName;
	}
	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	@Override
	public String toString() {
		return "MobileDeviceRepositoryFilterRequest [startDate=" + startDate + ", endDate=" + endDate + ", modifiedOn="
				+ modifiedOn + ", deviceId=" + deviceId + ", deviceType=" + deviceType + ", deviceStatus="
				+ deviceStatus + ", mdrStatus=" + mdrStatus + ", marketingName=" + marketingName + ", modelName="
				+ modelName + ", brandName=" + brandName + ", os=" + os + ", organizationId=" + organizationId
				+ ", networkTechnologyGSM=" + networkTechnologyGSM + ", networkTechnologyCDMA=" + networkTechnologyCDMA
				+ ", networkTechnologyLTE=" + networkTechnologyLTE + ", networkTechnology5G=" + networkTechnology5G
				+ ", userId=" + userId + ", userType=" + userType + ", orderColumnName=" + orderColumnName + ", order="
				+ order + ", publicIp=" + publicIp + ", browser=" + browser + ", searchString=" + searchString
				+ ", featureId=" + featureId + ", userTypeId=" + userTypeId + "]";
	}
	
}
