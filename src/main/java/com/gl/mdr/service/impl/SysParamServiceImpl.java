package com.gl.mdr.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.mdr.model.app.SystemConfigurationDb;
import com.gl.mdr.model.generic.GenericErrorResponse;
import com.gl.mdr.repo.app.SystemConfigurationDbRepository;
import com.google.gson.Gson;

@Service
public class SysParamServiceImpl {
	private static final Logger logger = LogManager.getLogger(SysParamServiceImpl.class);
	
	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;
	
	public SystemConfigurationDb getConfigurationByTag(String tag) {
		return systemConfigurationDbRepository.getByTag(tag);
	}
	
	public String[] getWhiteAPIList() {
		String[] whiteAPIList = null;
		List<SystemConfigurationDb> configurations = systemConfigurationDbRepository.findByActiveAndTag(1, "WHITELIST_API");
		whiteAPIList = new String[configurations.size()];
		for(int i=0; i<configurations.size(); i++) {
			whiteAPIList[i] = configurations.get(i).getValue();
		}
		return whiteAPIList;
	}
	  
	public void getErrorDetails(String tag, HttpServletResponse response) {
//		  Object[] errorDetails = null;
		SystemConfigurationDb configuration = systemConfigurationDbRepository.getByTag(tag);
//		  errorDetails = new Object[] {configuration.getType(), configuration.getValue()};
//		  return errorDetails;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(configuration.getType());
			response.getWriter().write(new Gson().toJson(
  				new GenericErrorResponse(configuration.getType(), configuration.getValue()))
  				);
		}catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}
