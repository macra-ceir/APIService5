package com.gl.mdr.repo.app;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

	@Query(value="select t from Token t where t.userId =:userId and (t.expired = false or t.revoked = false)")
	List<Token> findAllValidTokenByUserId(Integer userId);
	
	Optional<Token> findByToken(String token);
}
