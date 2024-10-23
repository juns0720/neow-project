package com.example.NeowProject.service;

import com.example.NeowProject.domain.Enemy;
import com.example.NeowProject.repository.EnemyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EnemyServiceTest {

    @Autowired
    private EnemyRepository enemyRepository;
    @Autowired
    private EnemyService enemyService;


    @Test
    public void findOneByName() {
        Enemy enemy1 = createEnemy("엄태준");

        enemyService.save(enemy1);
        assertEquals(enemy1.getId(),enemyService.findOneByName("엄태준").getId());
        assertEquals(enemy1, enemyRepository.findByName("엄태준"));
    }


    public static Enemy createEnemy(String name) {
        Enemy enemy = new Enemy();
        enemy.setName(name);

        return enemy;
    }
}