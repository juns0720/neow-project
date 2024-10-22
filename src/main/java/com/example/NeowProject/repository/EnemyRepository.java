package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyRepository extends JpaRepository<Enemy, Long> {

    public Enemy findByName(String name);
}
