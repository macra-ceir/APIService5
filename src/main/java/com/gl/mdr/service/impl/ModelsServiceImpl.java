package com.gl.mdr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.mdr.model.app.DeviceBrand;
import com.gl.mdr.model.app.DeviceModel;
import com.gl.mdr.repo.app.DeviceModelRepository;

@Service
public class ModelsServiceImpl {
	
	private static final Logger logger = LogManager.getLogger(ModelsServiceImpl.class);
	
	@Autowired
	BrandServiceImpl brandService;
	
	@Autowired
	DeviceModelRepository modelRepository;
	
	
	public void saveBrandModel(String brandName, String modelName) {
		DeviceBrand brand = null;
		try {
			brand = brandService.getBrandDetails(brandName);
			if( Objects.isNull(brand) ) {
				brand = brandService.saveSingleBrand(brandName);		
			}
			if(modelName != null && modelName != "" && Objects.isNull(modelRepository.findByBrandNameIdAndModelName(brand.getId(),
					modelName)))
				modelRepository.save(new DeviceModel(modelName, brand.getId(), modelName));
		}catch(Exception ex) {
			logger.error("Error during updating new models in the model table.");
			logger.error(ex.getMessage(), ex);
		}
	}
	
	public void updateNewModels(Set<String> brandNames, HashMap<String, String> modelMap) {
		HashMap<String, Integer> brandNameIdMap = null;
		List<DeviceModel> modelList = new ArrayList<DeviceModel>();
		try {
			brandNameIdMap = brandService.getBrandNameIdMap(brandNames);
			for(String modelName: modelMap.keySet()) {
				if(Objects.isNull(modelRepository.findByBrandNameIdAndModelName(brandNameIdMap.get(modelMap.get(modelName)),
						modelName)))
					modelList.add(new DeviceModel(modelMap.get(modelName),
							brandNameIdMap.get(modelMap.get(modelName)), modelName));
			}
			if(modelList.size() > 0)
				modelRepository.saveAll(modelList);
		}catch(Exception ex) {
			logger.error("Error during updating new models in the model table.");
			logger.error(ex.getMessage(), ex);
		}
	}

}
