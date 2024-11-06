package com.example.NeowProject.repository;

import com.example.NeowProject.domain.FinalRelic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalRelicRepository extends JpaRepository<FinalRelic, Long> {
}
