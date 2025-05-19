package com.gl.mdr.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.mdr.model.app.User;
import com.gl.mdr.model.app.UserTypeUrlMapping;
import com.gl.mdr.repo.app.UserRepository;
import com.gl.mdr.repo.app.UserTypeUrlMappingRepository;

@Service
public class UserTypeUrlMappingServiceImpl {
	private static final Logger logger = LogManager.getLogger(UserTypeUrlMappingServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserTypeUrlMappingRepository userTypeUrlMappingRepository;
	
	public boolean isRequestedUrlAllowed(String userName, String url) {
		try {
			User user = userRepository.getByUsername(userName);
			logger.info("Going to look for url:["+url+"] for user:["+userName+", "+user.getUsertype().getId()+"] permission.");
			UserTypeUrlMapping urlMapping = userTypeUrlMappingRepository.findByUserTypeIdAndUrlPathLike(
					user.getUsertype().getId(), "%"+url+"%");
			logger.info(urlMapping.getUrlPath());
			if(urlMapping != null && urlMapping.getUrlPath().contains(url))
				return true;
			logger.info("User:["+userName+"] is not allowed access the url["+url+"]");
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
		return false;
	}
}
