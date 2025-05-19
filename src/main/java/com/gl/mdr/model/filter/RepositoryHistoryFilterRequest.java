package com.gl.mdr.model.filter;

public class RepositoryHistoryFilterRequest {
	
	public String startDate;
	public String endDate;
	public String deviceId;
	private String modelName;
	private String brandName;
	private String os;
	private Integer id;
	
	public Integer userId;
	public String userType;
	private String orderColumnName;
	private String order;
	private String publicIp;
	private String browser;
	private String searchString;
	private Integer featureId;
	private Integer userTypeId;
	private String manufacturer;
	private String marketingName;

	private Integer mdrStatus;
	
	private String userDisplayName;
	
	private String deviceType;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	
	public String getMarketingName() {
		return marketingName;
	}
	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}
	public Integer getMdrStatus() {
		return mdrStatus;
	}
	public void setMdrStatus(Integer mdrStatus) {
		this.mdrStatus = mdrStatus;
	}
	public String getUserDisplayName() {
		return userDisplayName;
	}
	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	@Override
	public String toString() {
		return "RepositoryHistoryFilterRequest [startDate=" + startDate + ", endDate=" + endDate + ", deviceId="
				+ deviceId + ", modelName=" + modelName + ", brandName=" + brandName + ", os=" + os + ", id=" + id
				+ ", userId=" + userId + ", userType=" + userType + ", orderColumnName=" + orderColumnName + ", order="
				+ order + ", publicIp=" + publicIp + ", browser=" + browser + ", searchString=" + searchString
				+ ", featureId=" + featureId + ", userTypeId=" + userTypeId + "]";
	}
	
}
