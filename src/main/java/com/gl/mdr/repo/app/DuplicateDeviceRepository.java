package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.DuplicateDeviceDetail;
import com.gl.mdr.model.filter.DuplicateDeviceFilterRequest;

@Repository
public interface DuplicateDeviceRepository  extends 
JpaRepository<DuplicateDeviceDetail, Long>, JpaSpecificationExecutor<DuplicateDeviceDetail>{ 
	
	//public DuplicateDeviceDetail findById(Integer id);
	
	//public DuplicateDeviceDetail save(DuplicateDeviceDetail duplicateRequest);
	
	 @Modifying
	// @Query("update DuplicateDeviceDetail m set m.trcApprovedStatus =:#{#mdr.action},m.approvedBy =:#{#mdr.approvedBy},m.trcApprovalDate=CURRENT_TIMESTAMP,  m.remark =:#{#mdr.remark} WHERE m.id =:#{#mdr.id}")
	// public int update(@Param("id") Integer id, @Param("action") String action,@Param("remark") String remark);
	
		
		  @Query("update DuplicateDeviceDetail d set d.documentType1 =:#{#duplicateRequest.documentType1},"
		  + "d.documentType2 =:#{#duplicateRequest.documentType2}," +
		  "d.documentType3=:#{#duplicateRequest.documentType3}," +
		  "d.documentType4=:#{#duplicateRequest.documentType4}," +
		  "d.documentFileName1=:#{#duplicateRequest.documentFileName1}," +
		  "d.documentFileName2=:#{#duplicateRequest.documentFileName2}," +
		  "d.documentFileName3=:#{#duplicateRequest.documentFileName3}," +
		  "d.documentFileName4=:#{#duplicateRequest.documentFileName4}," +
		  "d.status=:#{#duplicateRequest.status}," +
		  "d.approveRemark=:#{#duplicateRequest.approveRemark}," +
		  "d.approveTransactionId=:#{#duplicateRequest.approveTransactionId}," +
		  "d.modifiedOn = CURRENT_TIMESTAMP WHERE d.id =:#{#duplicateRequest.id}")
		  public int update(@Param("duplicateRequest") DuplicateDeviceFilterRequest duplicateRequest);

	public List<DuplicateDeviceDetail> findTop1ByImei(String imei);

	public List<DuplicateDeviceDetail> findByImeiAndImsi(String imei, String imsi);

	public List<DuplicateDeviceDetail> findByImei(String imei);
		                                   
	//public int approveDuplicateDeviceDetail(@Param("duplicateRequest") DuplicateDeviceFilterRequest duplicateRequest);
	 
	 //@Query(value="SELECT DISTINCT u.status FROM app.duplicate_device_detail u where u.status<>''",nativeQuery = true)
	 //public List<DuplicateDeviceDetail> findDistinctStatus();
	 
}
