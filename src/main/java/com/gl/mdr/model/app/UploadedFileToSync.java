package com.gl.mdr.model.app;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "uploaded_file_to_sync")
public class UploadedFileToSync {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(name="created_on", columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Column(name="file_name", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String fileName = "";
	
	@Column(name="file_path", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String filePath = "";
	
	@Column(name="copy_status", length=10, columnDefinition="varchar(10) DEFAULT ''")
	private String copyStatus = "NEW";
	
	@Column(name="txn_id", length=255, columnDefinition="varchar(255) DEFAULT ''")
	private String txnId = "";
	
	@Column(name="server_id", columnDefinition="int DEFAULT '0'")
	private Integer serverId;
	
	public UploadedFileToSync() {}
	
	public UploadedFileToSync(String fileName, String filePath, int serverId, String deviceId) {
		this.fileName = fileName;
		this.filePath = filePath;
		this.serverId = serverId;
		this.txnId    = deviceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCopyStatus() {
		return copyStatus;
	}

	public void setCopyStatus(String copyStatus) {
		this.copyStatus = copyStatus;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	
}
