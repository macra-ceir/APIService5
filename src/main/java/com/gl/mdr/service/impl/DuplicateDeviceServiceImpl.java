package com.gl.mdr.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gl.mdr.configuration.PropertiesReader;
import com.gl.mdr.configuration.SortDirection;
import com.gl.mdr.exceptions.ResourceServicesException;
import com.gl.mdr.model.app.DuplicateDeviceDetail;
import com.gl.mdr.model.app.StatesInterpretationDb;
import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.app.User;
import com.gl.mdr.model.audit.AuditTrail;
import com.gl.mdr.model.constants.Datatype;
import com.gl.mdr.model.constants.SearchOperation;
import com.gl.mdr.model.file.DuplicateDeviceFileModel;
import com.gl.mdr.model.file.FileDetails;
import com.gl.mdr.model.filter.DuplicateDeviceFilterRequest;
import com.gl.mdr.model.generic.GenricResponse;
import com.gl.mdr.model.generic.SearchCriteria;
import com.gl.mdr.repo.app.AttachedFileInfoRepository;
import com.gl.mdr.repo.app.DuplicateDeviceRepository;
import com.gl.mdr.repo.app.StatesInterpretaionRepository;
import com.gl.mdr.repo.app.SystemConfigListRepository;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.gl.mdr.repo.app.UserProfileRepository;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.audit.AuditTrailRepository;
import com.gl.mdr.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.mdr.util.CustomMappingStrategy;
import com.gl.mdr.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


@Service
public class DuplicateDeviceServiceImpl {
    private static final Logger logger = LogManager.getLogger(DuplicateDeviceServiceImpl.class);

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    SystemConfigurationDbRepository systemConfigurationDbRepository;

    @Autowired
    AttachedFileInfoRepository attachedFileInfoRepository;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatesInterpretaionRepository statesInterpretaionRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    SystemConfigListRepository systemConfigListRepository;

    @Autowired
    Utility utility;

    @Autowired
    DuplicateDeviceRepository duplicateDeviceRepo;


    public Page<DuplicateDeviceDetail> getDuplicateDevicesDetails(
            DuplicateDeviceFilterRequest duplicateRequest, Integer pageNo, Integer pageSize, String operation) {
        List<StatesInterpretationDb> states = null;
        String stateInterp = null;
        try {
            logger.info("Duplicate Request" + duplicateRequest);
            String orderColumn;
            if (operation.equals("Export")) {
                orderColumn = "modifiedOn";
            } else {
                logger.info("column Name :: " + duplicateRequest.getOrderColumnName());
                orderColumn = "Detection Date".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "edrTime" :
                        "IMEI".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "imei" :
                                "Phone Number".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "msisdn" :
                                        "Updated By".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "updatedBy" :
                                                "Status".equalsIgnoreCase(duplicateRequest.getOrderColumnName()) ? "status"
                                                        : "modifiedOn";
            }

            logger.info("orderColumn data:  " + orderColumn);
            
            //List<DuplicateDeviceDetail> DuplicateDeviceDetail=duplicateDeviceRepo.findDistinctStatus();
            //logger.info("status List for Duplicate Device"+DuplicateDeviceDetail);

            //states = statesInterpretaionRepository.findByFeatureId(duplicateRequest.getFeatureId());
            //logger.info("state Interp for feature:  "+states);

            Sort.Direction direction;
            if ("modifiedOn".equalsIgnoreCase(orderColumn)) {
                direction = Sort.Direction.DESC;
            } else {
                direction = SortDirection.getSortDirection(duplicateRequest.getSort());
            }


            if ("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(duplicateRequest.getSort()).equals(Sort.Direction.ASC)) {
                direction = Sort.Direction.ASC;
            }
            Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(direction, orderColumn));
            
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setFeatureName("Duplicate Device");
            auditTrail.setSubFeature("View All");
            auditTrail.setPublicIp(duplicateRequest.getPublicIp());
            auditTrail.setBrowser(duplicateRequest.getBrowser());
            if (Objects.nonNull(duplicateRequest.getUserId())) {
                User user = userRepository.getByid(duplicateRequest.getUserId());
                auditTrail.setUserId(duplicateRequest.getUserId());
                auditTrail.setUserName(user.getUsername());
            } else {
                auditTrail.setUserName("NA");
            }
            if (Objects.nonNull(duplicateRequest.getUserType())) {
                auditTrail.setUserType(duplicateRequest.getUserType());
                auditTrail.setRoleType(duplicateRequest.getUserType());
            } else {
                auditTrail.setUserType("NA");
                auditTrail.setRoleType("NA");
            }
            auditTrail.setTxnId("NA");
            auditTrailRepository.save(auditTrail);
            

            GenericSpecificationBuilder<DuplicateDeviceDetail> uPSB = new GenericSpecificationBuilder<DuplicateDeviceDetail>(propertiesReader.dialect);
            if (Objects.nonNull(duplicateRequest.getStartDate()) && !duplicateRequest.getStartDate().isEmpty())
                uPSB.with(new SearchCriteria("edrTime", duplicateRequest.getStartDate(), SearchOperation.GREATER_THAN,
                        Datatype.DATE));

            if (Objects.nonNull(duplicateRequest.getEndDate()) && !duplicateRequest.getEndDate().isEmpty())
                uPSB.with(new SearchCriteria("edrTime", duplicateRequest.getEndDate(), SearchOperation.LESS_THAN,
                        Datatype.DATE));

            if (Objects.nonNull(duplicateRequest.getImei()) && !duplicateRequest.getImei().equals(""))
                uPSB.with(new SearchCriteria("imei", duplicateRequest.getImei(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(duplicateRequest.getMsisdn()) && !duplicateRequest.getMsisdn().equals(""))
                uPSB.with(new SearchCriteria("msisdn", duplicateRequest.getMsisdn(), SearchOperation.LIKE, Datatype.STRING));

            if (Objects.nonNull(duplicateRequest.getUpdatedBy()) && !duplicateRequest.getUpdatedBy().equals(""))
                uPSB.with(new SearchCriteria("updatedBy", duplicateRequest.getUpdatedBy(), SearchOperation.LIKE, Datatype.STRING));


           // if (Objects.nonNull(duplicateRequest.getStatus()) && !duplicateRequest.getStatus().equals(""))
                uPSB.with(new SearchCriteria("status", "DUPLICATE", SearchOperation.LIKE, Datatype.STRING));
            //  uPSB.with(new SearchCriteria("status", duplicateRequest.getStatus(), SearchOperation.LIKE, Datatype.STRING));

            
            Page<DuplicateDeviceDetail> pageResult = duplicateDeviceRepo.findAll(uPSB.build(), pageable);
			/*
			 * for (DuplicateDeviceDetail detail : pageResult.getContent()) { String
			 * interpretation = getInterpretationForStatus(detail.getStatus(),
			 * Long.valueOf(duplicateRequest.getFeatureId()));
			 * detail.setInterpretation(interpretation); }
			 */

            return pageResult;


        } catch (Exception e) {
            logger.info("Exception found =" + e.getMessage());
            logger.info(e.getClass().getMethods().toString());
            logger.info(e.toString());
            return null;
        }
    }

    public String getInterpretationForStatus(int status, Long featureId) {
        StatesInterpretationDb interpretation = statesInterpretaionRepository.findByStateAndFeatureId(status, featureId);
        return interpretation != null ? interpretation.getInterpretation() : null;
    }


    public FileDetails exportData(DuplicateDeviceFilterRequest duplicateRequest) {
        logger.info("inside export Duplicate Device Feature service");
        logger.info("Export Request:  " + duplicateRequest);
        String fileName = null;
        Writer writer = null;
        DuplicateDeviceFileModel uPFm = null;
        SystemConfigurationDb dowlonadDir = systemConfigurationDbRepository.getByTag("file.download-dir");
        SystemConfigurationDb dowlonadLink = systemConfigurationDbRepository.getByTag("file.download-link");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
        String filePath = dowlonadDir.getValue();
        StatefulBeanToCsvBuilder<DuplicateDeviceFileModel> builder = null;
        StatefulBeanToCsv<DuplicateDeviceFileModel> csvWriter = null;
        List<DuplicateDeviceFileModel> fileRecords = null;
        MappingStrategy<DuplicateDeviceFileModel> mapStrategy = new CustomMappingStrategy<>();

        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setFeatureName("Duplicate Device");
        auditTrail.setSubFeature("Export");
        auditTrail.setPublicIp(duplicateRequest.getPublicIp());
        auditTrail.setBrowser(duplicateRequest.getBrowser());
        if (Objects.nonNull(duplicateRequest.getUserId())) {
            User user = userRepository.getByid(duplicateRequest.getUserId());
            auditTrail.setUserId(duplicateRequest.getUserId());
            auditTrail.setUserName(user.getUsername());
        } else {
            auditTrail.setUserName("NA");
        }
        if (Objects.nonNull(duplicateRequest.getUserType())) {
            auditTrail.setUserType(duplicateRequest.getUserType());
            auditTrail.setRoleType(duplicateRequest.getUserType());
        } else {
            auditTrail.setUserType("NA");
            auditTrail.setRoleType("NA");
        }
        auditTrail.setTxnId("NA");
        auditTrailRepository.save(auditTrail);

        try {
            mapStrategy.setType(DuplicateDeviceFileModel.class);
            duplicateRequest.setSort("");
            List<DuplicateDeviceDetail> list = getDuplicateDevicesDetails(duplicateRequest, pageNo, pageSize, "Export").getContent();
            if (list.size() > 0) {
                fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_duplicate_device.csv";
            } else {
                fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_duplicate_device.csv";
            }
            logger.info(" file path plus file name: " + Paths.get(filePath + fileName));
            writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
            builder = new StatefulBeanToCsvBuilder<>(writer);

            csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            if (list.size() > 0) {
                fileRecords = new ArrayList<DuplicateDeviceFileModel>();
                for (DuplicateDeviceDetail duplicateDeviceDetail : list) {
                	uPFm = new DuplicateDeviceFileModel();
                    uPFm.setEdrTime(duplicateDeviceDetail.getEdrTime());
                    uPFm.setImei(duplicateDeviceDetail.getImei());
                    uPFm.setMsisdn(duplicateDeviceDetail.getMsisdn());
                 //   uPFm.setUpdatedBy(duplicateDeviceDetail.getUpdatedBy());
                    uPFm.setStatus(duplicateDeviceDetail.getStatus());
                    fileRecords.add(uPFm);
                }
                csvWriter.write(fileRecords);
            }
            logger.info("fileName::" + fileName);
            logger.info("filePath::::" + filePath);
            logger.info("link:::" + dowlonadLink.getValue());
            return new FileDetails(fileName, filePath, dowlonadLink.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {

            }
        }

    }

    public ResponseEntity<?> viewApprovedDevice_backup(DuplicateDeviceFilterRequest duplicateRequest) {
        logger.info("View Approved Devices by id : " + duplicateRequest.getId());
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setFeatureName("Duplicate Device");
        auditTrail.setSubFeature("View");
        auditTrail.setPublicIp(duplicateRequest.getPublicIp());
        auditTrail.setBrowser(duplicateRequest.getBrowser());
        if (Objects.nonNull(duplicateRequest.getUserId())) {
            User user = userRepository.getByid(duplicateRequest.getUserId());
            auditTrail.setUserId(duplicateRequest.getUserId());
            auditTrail.setUserName(user.getUsername());
        } else {
            auditTrail.setUserName("NA");
        }
        if (Objects.nonNull(duplicateRequest.getUserType())) {
            auditTrail.setUserType(duplicateRequest.getUserType());
            auditTrail.setRoleType(duplicateRequest.getUserType());
        } else {
            auditTrail.setUserType("NA");
            auditTrail.setRoleType("NA");
        }
        auditTrail.setTxnId("NA");
        auditTrailRepository.save(auditTrail);
        Optional<DuplicateDeviceDetail> result = duplicateDeviceRepo.findById(duplicateRequest.getId());
        logger.info("View result : " + result);
        if (result.isPresent()) {
            GenricResponse response = new GenricResponse(200, "", "", result.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            GenricResponse response = new GenricResponse(500, "Something wrong happend", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> viewApprovedDevice(DuplicateDeviceFilterRequest duplicateRequest) {
        logger.info("View Approved Devices by id : " + duplicateRequest.getId());
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setFeatureName("Duplicate Device");
        auditTrail.setSubFeature("View");
        auditTrail.setPublicIp(duplicateRequest.getPublicIp());
        auditTrail.setBrowser(duplicateRequest.getBrowser());
        if (Objects.nonNull(duplicateRequest.getUserId())) {
            User user = userRepository.getByid(duplicateRequest.getUserId());
            auditTrail.setUserId(duplicateRequest.getUserId());
            auditTrail.setUserName(user.getUsername());
        } else {
            auditTrail.setUserName("NA");
        }
        if (Objects.nonNull(duplicateRequest.getUserType())) {
            auditTrail.setUserType(duplicateRequest.getUserType());
            auditTrail.setRoleType(duplicateRequest.getUserType());
        } else {
            auditTrail.setUserType("NA");
            auditTrail.setRoleType("NA");
        }
        auditTrail.setTxnId("NA");
        auditTrailRepository.save(auditTrail);
        Optional<DuplicateDeviceDetail> result = duplicateDeviceRepo.findById(duplicateRequest.getId());
        
        
        
        logger.info("View result : " + result);
        if (result.isPresent()) {
        	DuplicateDeviceDetail detail = result.get();
        	
        	//for status interpretation
        	//String interpretation = getInterpretationForStatus(detail.getStatus(), Long.valueOf(duplicateRequest.getFeatureId()));
        	//logger.info("interpretation in view" +interpretation);
            
        	List<String> documentTypes = Arrays.asList(detail.getDocumentType1(), detail.getDocumentType2(),
                    detail.getDocumentType3(), detail.getDocumentType4());
            List<String> documentPaths = Arrays.asList(detail.getDocumentFileName1(), detail.getDocumentFileName2(),
                    detail.getDocumentFileName3(), detail.getDocumentFileName4());

            // Filter out null values from document types and document paths
            documentTypes = documentTypes.stream().filter(Objects::nonNull).collect(Collectors.toList());
            documentPaths = documentPaths.stream().filter(Objects::nonNull).collect(Collectors.toList());


            SystemConfigurationDb uploadedFilePath = systemConfigurationDbRepository.getByTag("upload_file_link");

            logger.info("uploadedFilePath :::" + uploadedFilePath.getValue());
            
            

            // Create a map to hold the response data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", detail.getId());
            responseData.put("createdOn", detail.getCreatedOn());
            responseData.put("modifiedOn", detail.getModifiedOn());
            responseData.put("imei", detail.getImei());
            responseData.put("edrTime", detail.getEdrTime());
            responseData.put("approveTransactionId", detail.getApproveTransactionId());
            responseData.put("approveRemark", detail.getApproveRemark());
            responseData.put("msisdn", detail.getMsisdn());
            responseData.put("updatedBy", detail.getUpdatedBy());
            responseData.put("documentTypes", documentTypes);
            responseData.put("documentPaths", documentPaths);
            //responseData.put("interpretation", interpretation);
            responseData.put("status", detail.getStatus());
            responseData.put("uploadedFilePath", uploadedFilePath.getValue().replace("$LOCAL_IP", propertiesReader.localIp));

            GenricResponse response = new GenricResponse(200, "Success", "", responseData);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            GenricResponse response = new GenricResponse(500, "Something wrong happened", "", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    @Transactional(rollbackOn = {SQLException.class})
    public ResponseEntity<?> update(DuplicateDeviceFilterRequest duplicateRequest) {

        if (Objects.nonNull(duplicateRequest.getId())) {
            logger.info("approveDuplicateDevice Request : " + duplicateRequest);
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setFeatureName("Duplicate Device");
            auditTrail.setSubFeature("Approve");
            auditTrail.setPublicIp(duplicateRequest.getPublicIp());
            auditTrail.setBrowser(duplicateRequest.getBrowser());
            if (Objects.nonNull(duplicateRequest.getUserId())) {
                User user = userRepository.getByid(duplicateRequest.getUserId());
                auditTrail.setUserId(duplicateRequest.getUserId());
                auditTrail.setUserName(user.getUsername());
            } else {
                auditTrail.setUserName("NA");
            }
            if (Objects.nonNull(duplicateRequest.getUserType())) {
                auditTrail.setUserType(duplicateRequest.getUserType());
                auditTrail.setRoleType(duplicateRequest.getUserType());
            } else {
                auditTrail.setUserType("NA");
                auditTrail.setRoleType("NA");
            }
            auditTrail.setTxnId("NA");
            auditTrailRepository.save(auditTrail);
            //Changed status from duplicate to Original
            logger.info("After set status to Original[3] Status is  : " + duplicateRequest.getStatus());
            logger.info("Approve Tranaction ID  : " + duplicateRequest.getApproveTransactionId());
            logger.info("ApproveRemark  : " + duplicateRequest.getApproveRemark());
            int result = duplicateDeviceRepo.update(duplicateRequest);
            if (result == 1) {
                GenricResponse response = new GenricResponse(HttpStatus.OK.value(), "Deivce Approved Successfully", "", result);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        }
        GenricResponse response = new GenricResponse(HttpStatus.EXPECTATION_FAILED.value(), "Something wrong happend", "", "");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
