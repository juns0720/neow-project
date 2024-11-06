package com.example.NeowProject.repository;

import com.example.NeowProject.domain.SelectBossRelic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectBossRelicRepository extends JpaRepository<SelectBossRelic, Long> {
}
