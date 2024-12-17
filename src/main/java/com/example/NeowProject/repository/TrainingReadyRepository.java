package com.example.NeowProject.repository;

import com.example.NeowProject.domain.TrainingReady;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingReadyRepository extends JpaRepository<TrainingReady, Long> {

}
