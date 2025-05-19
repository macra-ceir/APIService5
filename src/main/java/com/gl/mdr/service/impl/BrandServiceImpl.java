package com.gl.mdr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.mdr.model.app.DeviceBrand;
import com.gl.mdr.repo.app.DeviceBrandRepository;

@Service
public class BrandServiceImpl {
	
	private static final Logger logger = LogManager.getLogger(BrandServiceImpl.class);
	
	@Autowired
	DeviceBrandRepository brandRepository;
	
	public List<DeviceBrand> getBrandDetailsListByBrandNames(Set<String> brandNames){
		List<DeviceBrand> brandList = brandRepository.findByBrandNameIn(brandNames);
		return brandList;
	}
	
	public HashMap<String, Integer> getBrandNameIdMap(Set<String> brandNames){
		HashMap<String, Integer> brandNameIdMap = new HashMap<String, Integer>();
		List<DeviceBrand> brandList = null;
		try {
			brandList = this.getBrandDetailsListByBrandNames(brandNames);
			for(DeviceBrand brand: brandList) {
				brandNameIdMap.put(brand.getBrandName(), brand.getId());
			}
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return brandNameIdMap;
	}
	
	public void updateNewBrands(Set<String> brands) {
		List<DeviceBrand> existingBrands = null;
		List<DeviceBrand> brandList = new ArrayList<DeviceBrand>();
		try {
			existingBrands = this.getBrandDetailsListByBrandNames(brands);
			for(DeviceBrand brand: existingBrands)
				brands.remove(brand.getBrandName());
			for(String brand: brands) {
				brandList.add(new DeviceBrand(brand));
			}
			if(brandList.size() > 0)
				brandRepository.saveAll(brandList);
		}catch(Exception ex) {
			logger.error("Error during updating new brands in the brand table.");
			logger.error(ex.getMessage(), ex);
		}	
	}
	
	public DeviceBrand saveSingleBrand( String brandName ) {
		DeviceBrand brand = brandRepository.save(new DeviceBrand(brandName));
		return brand;
	}
	
	public DeviceBrand getBrandDetails( String brandName ) {
		return brandRepository.findByBrandName(brandName);
	}

}
