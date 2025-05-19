package com.gl.mdr.repo.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.mdr.model.app.StatesInterpretationDb;

@Repository
public interface StatesInterpretaionRepository extends JpaRepository<StatesInterpretationDb, Long>, JpaSpecificationExecutor<StatesInterpretationDb> {

	public List<StatesInterpretationDb> findByFeatureId(long featureId);

	public StatesInterpretationDb findByStateAndFeatureId(int status, Long featureId);

}
