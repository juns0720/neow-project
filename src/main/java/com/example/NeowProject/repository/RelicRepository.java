package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Relic;
import com.example.NeowProject.domain.RelicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelicRepository extends JpaRepository<Relic, Long> {

    public Optional<Relic> findByName(String name);

    public List<Relic> findRelicsByRelicType(RelicType type);

    public List<Relic> findRelicsByRelicTypeNot(RelicType type);
}
