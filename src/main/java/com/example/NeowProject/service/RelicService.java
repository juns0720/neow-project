package com.example.NeowProject.service;


import com.example.NeowProject.domain.Relic;
import com.example.NeowProject.domain.RelicType;
import com.example.NeowProject.repository.RelicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelicService {
    private RelicRepository relicRepository;

    public RelicService(RelicRepository relicRepository) {
        this.relicRepository = relicRepository;
    }

    public Long save(Relic relic) {
        relicRepository.save(relic);
        return relic.getId();
    }

    public Relic findByName(String name){
        return relicRepository.findByName(name).orElseThrow(() -> new RuntimeException("유물을 찾을 수 없습니다."));
    }

    public List<Relic> findAll(){
        return relicRepository.findAll();
    }

    public List<Relic> findRelicsByType(RelicType type){
        return relicRepository.findRelicsByRelicType(type);
    }
}
