package com.gl.mdr.repo.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.audit.AuditTrail;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long> {

}
