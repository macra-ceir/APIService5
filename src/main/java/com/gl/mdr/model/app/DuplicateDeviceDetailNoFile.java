package com.gl.mdr.model.app;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "duplicate_device_detail")
public class DuplicateDeviceDetailNoFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

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

	private Integer status;
	@Transient
	private String stateInterp;
	
	private String remarks;
	private String updatedBy;
	private String transactionId;
	private String documentType1;
	private String documentType2;
	private String documentType3;
	private String documentType4;
	private String documentPath1;
	private String documentPath2;
	private String documentPath3;
	private String documentPath4;
	private int reminderStatus;
	private int successCount;
	private int failCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStateInterp() {
		return stateInterp;
	}
	public void setStateInterp(String stateInterp) {
		this.stateInterp = stateInterp;
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
	public String getDocumentPath1() {
		return documentPath1;
	}
	public void setDocumentPath1(String documentPath1) {
		this.documentPath1 = documentPath1;
	}
	public String getDocumentPath2() {
		return documentPath2;
	}
	public void setDocumentPath2(String documentPath2) {
		this.documentPath2 = documentPath2;
	}
	public String getDocumentPath3() {
		return documentPath3;
	}
	public void setDocumentPath3(String documentPath3) {
		this.documentPath3 = documentPath3;
	}
	public String getDocumentPath4() {
		return documentPath4;
	}
	public void setDocumentPath4(String documentPath4) {
		this.documentPath4 = documentPath4;
	}
	public int getReminderStatus() {
		return reminderStatus;
	}
	public void setReminderStatus(int reminderStatus) {
		this.reminderStatus = reminderStatus;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DuplicateDeviceDetailNoFile [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", imsi=");
		builder.append(imsi);
		builder.append(", msisdn=");
		builder.append(msisdn);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", edrTime=");
		builder.append(edrTime);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(", status=");
		builder.append(status);
		builder.append(", stateInterp=");
		builder.append(stateInterp);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", updatedBy=");
		builder.append(updatedBy);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", documentType1=");
		builder.append(documentType1);
		builder.append(", documentType2=");
		builder.append(documentType2);
		builder.append(", documentType3=");
		builder.append(documentType3);
		builder.append(", documentType4=");
		builder.append(documentType4);
		builder.append(", documentPath1=");
		builder.append(documentPath1);
		builder.append(", documentPath2=");
		builder.append(documentPath2);
		builder.append(", documentPath3=");
		builder.append(documentPath3);
		builder.append(", documentPath4=");
		builder.append(documentPath4);
		builder.append(", reminderStatus=");
		builder.append(reminderStatus);
		builder.append(", successCount=");
		builder.append(successCount);
		builder.append(", failCount=");
		builder.append(failCount);
		builder.append("]");
		return builder.toString();
	}
	
	
}
