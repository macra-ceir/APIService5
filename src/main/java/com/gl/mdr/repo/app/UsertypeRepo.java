package com.gl.mdr.repo.app;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.Usertype;

@Repository
public interface UsertypeRepo extends JpaRepository<Usertype, Long>{
	public List<Usertype> findAll();
	public Usertype findById(long id); 
	public Usertype findByUsertypeName(String usertype);
}
