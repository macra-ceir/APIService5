package com.gl.mdr.model.filter;

public class DuplicateDeviceFilterRequest {
	private Long id;
	public String createdOn;
	public String modifiedOn;
	
	private String imei;
	private String msisdn;
	private String edrTime;
	private String updatedBy;
	private String status;

	
	public Integer userId;
	public String userType;
	private String publicIp;
	private String browser;
	private String searchString;
	private Integer featureId;
	private Integer userTypeId;
	
	private String orderColumnName;
	private String order;
	private String sort;
	
	//Parameter when approving device
	private String transactionId;
	private String documentType1;
	private String documentType2;
	private String documentType3;
	private String documentType4;
	private String documentFileName1;
	private String documentFileName2;
	private String documentFileName3;
	private String documentFileName4;
	private String approveTransactionId;
	private String approveRemark;
	
	private String stateInterp;
	private String startDate;
	private String endDate;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getEdrTime() {
		return edrTime;
	}

	public void setEdrTime(String edrTime) {
		this.edrTime = edrTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDocumentType1() {
		return documentType1;
	}

	public void setDocumentType1(String documentType1) {
		this.documentType1 = documentType1;
	}

	public String getDocumentType2() {
		return documentType2;
	}

	public void setDocumentType2(String documentType2) {
		this.documentType2 = documentType2;
	}

	public String getDocumentType3() {
		return documentType3;
	}

	public void setDocumentType3(String documentType3) {
		this.documentType3 = documentType3;
	}

	public String getDocumentType4() {
		return documentType4;
	}

	public void setDocumentType4(String documentType4) {
		this.documentType4 = documentType4;
	}

	public String getDocumentFileName1() {
		return documentFileName1;
	}

	public void setDocumentFileName1(String documentFileName1) {
		this.documentFileName1 = documentFileName1;
	}

	public String getDocumentFileName2() {
		return documentFileName2;
	}

	public void setDocumentFileName2(String documentFileName2) {
		this.documentFileName2 = documentFileName2;
	}

	public String getDocumentFileName3() {
		return documentFileName3;
	}

	public void setDocumentFileName3(String documentFileName3) {
		this.documentFileName3 = documentFileName3;
	}

	public String getDocumentFileName4() {
		return documentFileName4;
	}

	public void setDocumentFileName4(String documentFileName4) {
		this.documentFileName4 = documentFileName4;
	}

	public String getApproveTransactionId() {
		return approveTransactionId;
	}

	public void setApproveTransactionId(String approveTransactionId) {
		this.approveTransactionId = approveTransactionId;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getStateInterp() {
		return stateInterp;
	}

	public void setStateInterp(String stateInterp) {
		this.stateInterp = stateInterp;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DuplicateDeviceFilterRequest{");
		sb.append("id=").append(id);
		sb.append(", createdOn='").append(createdOn).append('\'');
		sb.append(", modifiedOn='").append(modifiedOn).append('\'');
		sb.append(", imei='").append(imei).append('\'');
		sb.append(", msisdn='").append(msisdn).append('\'');
		sb.append(", edrTime='").append(edrTime).append('\'');
		sb.append(", updatedBy='").append(updatedBy).append('\'');
		sb.append(", status='").append(status).append('\'');
		sb.append(", userId=").append(userId);
		sb.append(", userType='").append(userType).append('\'');
		sb.append(", publicIp='").append(publicIp).append('\'');
		sb.append(", browser='").append(browser).append('\'');
		sb.append(", searchString='").append(searchString).append('\'');
		sb.append(", featureId=").append(featureId);
		sb.append(", userTypeId=").append(userTypeId);
		sb.append(", orderColumnName='").append(orderColumnName).append('\'');
		sb.append(", order='").append(order).append('\'');
		sb.append(", sort='").append(sort).append('\'');
		sb.append(", transactionId='").append(transactionId).append('\'');
		sb.append(", documentType1='").append(documentType1).append('\'');
		sb.append(", documentType2='").append(documentType2).append('\'');
		sb.append(", documentType3='").append(documentType3).append('\'');
		sb.append(", documentType4='").append(documentType4).append('\'');
		sb.append(", documentFileName1='").append(documentFileName1).append('\'');
		sb.append(", documentFileName2='").append(documentFileName2).append('\'');
		sb.append(", documentFileName3='").append(documentFileName3).append('\'');
		sb.append(", documentFileName4='").append(documentFileName4).append('\'');
		sb.append(", approveTransactionId='").append(approveTransactionId).append('\'');
		sb.append(", approveRemark='").append(approveRemark).append('\'');
		sb.append(", stateInterp='").append(stateInterp).append('\'');
		sb.append(", startDate='").append(startDate).append('\'');
		sb.append(", endDate='").append(endDate).append('\'');
		sb.append('}');
		return sb.toString();
	}


}
