package com.example.NeowProject.service;

import com.example.NeowProject.domain.Enemy;
import com.example.NeowProject.repository.EnemyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnemyService {

    private final EnemyRepository enemyRepository;

    public EnemyService(EnemyRepository enemyRepository){
        this.enemyRepository = enemyRepository;
    }

    private Long save(Enemy enemy){
        enemyRepository.save(enemy);
        return enemy.getId();
    }

    public Enemy findOneByName(String name){
        return enemyRepository.findByName(name);
    }

    public List<Enemy> findAllEnemies(){
        return enemyRepository.findAll();
    }


}
