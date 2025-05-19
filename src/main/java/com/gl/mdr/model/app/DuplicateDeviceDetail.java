package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.mdr.model.constants.Tags;


@Entity
@Table(name = "duplicate_device_detail")
public class DuplicateDeviceDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_on", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn;

	@Column(name="modified_on", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedOn;

	private String imei;
	private String imsi;
	private String msisdn;
	private String fileName;

	@Column(name="edr_time", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime edrTime;

	private String operator;

	@Column(name="expiryDate", columnDefinition="timestamp DEFAULT NULL")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime expiryDate;

	@Transient
	private String uploadedFilePath;

	private String remarks;
	private String updatedBy;
	private String transactionId;
	private String documentType1;
	private String documentType2;
	private String documentType3;
	private String documentType4;

	@Column(name="document_file_name_1", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName1;

	@Column(name="document_file_name_2", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName2;

	@Column(name="document_file_name_3", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName3;

	@Column(name="document_file_name_4", length=150, columnDefinition="varchar(150) DEFAULT NULL")
	private String documentFileName4;
	private String approveTransactionId;
	private String approveRemark;
	private Integer reminderStatus;
	private Integer successCount;
	private Integer failCount;

	private String status;
	@Transient
	private String interpretation;
	
	private Integer userStatus;
	
	@Transient
    private String tableName=Tags.duplicate_device_detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDateTime getEdrTime() {
		return edrTime;
	}

	public void setEdrTime(LocalDateTime edrTime) {
		this.edrTime = edrTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getUploadedFilePath() {
		return uploadedFilePath;
	}

	public void setUploadedFilePath(String uploadedFilePath) {
		this.uploadedFilePath = uploadedFilePath;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public Integer getReminderStatus() {
		return reminderStatus;
	}

	public void setReminderStatus(Integer reminderStatus) {
		this.reminderStatus = reminderStatus;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DuplicateDeviceDetail{");
		sb.append("id=").append(id);
		sb.append(", createdOn=").append(createdOn);
		sb.append(", modifiedOn=").append(modifiedOn);
		sb.append(", imei='").append(imei).append('\'');
		sb.append(", imsi='").append(imsi).append('\'');
		sb.append(", msisdn='").append(msisdn).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", edrTime=").append(edrTime);
		sb.append(", operator='").append(operator).append('\'');
		sb.append(", expiryDate=").append(expiryDate);
		sb.append(", uploadedFilePath='").append(uploadedFilePath).append('\'');
		sb.append(", remarks='").append(remarks).append('\'');
		sb.append(", updatedBy='").append(updatedBy).append('\'');
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
		sb.append(", reminderStatus=").append(reminderStatus);
		sb.append(", successCount=").append(successCount);
		sb.append(", failCount=").append(failCount);
		sb.append(", status='").append(status).append('\'');
		sb.append(", interpretation='").append(interpretation).append('\'');
		sb.append(", userStatus=").append(userStatus);
		sb.append(", tableName='").append(tableName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
