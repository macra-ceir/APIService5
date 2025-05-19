package com.gl.mdr.model.file;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class DuplicateDeviceFileModel {
	
	@CsvBindByName(column = "Detection Date")
	@CsvBindByPosition(position = 0)
	private LocalDateTime edrTime;
	
	@CsvBindByName(column = "IMEI")
	@CsvBindByPosition(position = 1)
	private String imei;
	
	@CsvBindByName(column = "Phone Number")
	@CsvBindByPosition(position = 2)
	private String msisdn;
	
/*	@CsvBindByName(column = "Updated By")
	@CsvBindByPosition(position = 3)
	private String updatedBy;*/
	
	
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 3)
	private String status;


	public LocalDateTime getEdrTime() {
		return edrTime;
	}


	public void setEdrTime(LocalDateTime edrTime) {
		this.edrTime = edrTime;
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


/*
	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

*/

	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DuplicateDeviceFileModel [edrTime=");
		builder.append(edrTime);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", msisdn=");
		builder.append(msisdn);
		builder.append(", updatedBy=");
	//	builder.append(updatedBy);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}


	
}
