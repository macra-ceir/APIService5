package com.gl.mdr.model.generic;

public class DashboardData {
	private Integer totalDevices;
	private Integer newDevices;
	private Integer updatedDevices;
	private Integer completedDevices;
	
	public DashboardData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DashboardData(long totalDevices, long newDevices, long updatedDevices,long completedDevices) {
		this.totalDevices = (int)totalDevices;
		this.newDevices   = (int)newDevices;
		this.updatedDevices = (int)updatedDevices;
		this.completedDevices= (int)completedDevices;
	}

	public DashboardData(long totalDevices, long newDevices, long updatedDevices) {
		this.totalDevices = (int)totalDevices;
		this.newDevices   = (int)newDevices;
		this.updatedDevices = (int)updatedDevices;
	}
	
	public DashboardData(long totalDevices, long newDevices, Integer updatedDevices) {
		this.totalDevices = (int)totalDevices;
		this.newDevices   = (int)newDevices;
		this.updatedDevices = updatedDevices;
	}
	
	
	public Integer getTotalDevices() {
		return totalDevices;
	}
	public void setTotalDevices(Integer totalDevices) {
		this.totalDevices = totalDevices;
	}
	public Integer getNewDevices() {
		return newDevices;
	}
	public void setNewDevices(Integer newDevices) {
		this.newDevices = newDevices;
	}

	public Integer getUpdatedDevices() {
		return updatedDevices;
	}

	public void setUpdatedDevices(Integer updatedDevices) {
		this.updatedDevices = updatedDevices;
	}

	public Integer getCompletedDevices() {
		return completedDevices;
	}

	public void setCompletedDevices(Integer completedDevices) {
		this.completedDevices = completedDevices;
	}
	
	
}
