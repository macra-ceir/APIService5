package com.gl.mdr.repo.app;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User getByUsername(String userName);
	public User getByid(Integer id);
    public List<User> getByUsertype_Id(long id);
	Optional<User> findByUsername(String userName);
	
}
