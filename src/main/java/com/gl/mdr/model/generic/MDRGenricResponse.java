package com.gl.mdr.model.generic;

public class MDRGenricResponse {
	private int errorCode;
	private String message;
	private Long deviceId;


	public MDRGenricResponse(int errorCode, String message, Long deviceId) {
		this.errorCode = errorCode;
		this.message = message;
		this.deviceId = deviceId;
	}


	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "MDRGenricResponse [errorCode=" + errorCode + ", message=" + message + ", deviceId=" + deviceId + "]";
	}
	
}
