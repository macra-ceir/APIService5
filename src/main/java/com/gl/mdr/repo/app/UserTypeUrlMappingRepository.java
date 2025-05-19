package com.gl.mdr.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.UserTypeUrlMapping;

@Repository
public interface UserTypeUrlMappingRepository extends JpaRepository<UserTypeUrlMapping, Long>{
	public UserTypeUrlMapping findByUserTypeIdAndUrlPathLike(Integer userTypeId, String urlPath);
}
