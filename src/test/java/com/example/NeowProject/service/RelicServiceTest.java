package com.example.NeowProject.service;

import com.example.NeowProject.domain.Relic;
import com.example.NeowProject.domain.RelicType;
import com.example.NeowProject.repository.RelicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RelicServiceTest {

    @Autowired
    private RelicService relicService;
    @Autowired
    private RelicRepository relicRepository;

    @Test
    void findByName() {
        Relic bossRelic = createRelic(RelicType.BOSS, "boss relic1");

        Long savedId = relicService.save(bossRelic);

        assertEquals(bossRelic, relicService.findByName("boss relic1"));
        assertEquals(savedId, relicRepository.findByName("boss relic1").get().getId());
    }

    @Test
    void findAll() {
        Relic bossRelic1 = createRelic(RelicType.BOSS, "boss relic1");
        Relic bossRelic2 = createRelic(RelicType.BOSS, "boss relic2");
        Relic bossRelic3 = createRelic(RelicType.BOSS, "boss relic3");

        relicService.save(bossRelic1);
        relicService.save(bossRelic2);
        relicService.save(bossRelic3);
        List<Relic> relics = relicService.findAll();

        assertEquals(3,relics.size());

    }

    @Test
    void findRelicsByRelicType() {
        Relic bossRelic1 = createRelic(RelicType.BOSS, "boss relic1");
        Relic bossRelic2 = createRelic(RelicType.RARE, "boss relic2");
        Relic bossRelic3 = createRelic(RelicType.BOSS, "boss relic3");

        relicService.save(bossRelic1);
        relicService.save(bossRelic2);
        relicService.save(bossRelic3);
        List<Relic> relics = relicService.findRelicsByType(RelicType.BOSS);

        assertEquals(2,relics.size());

    }



    static Relic createRelic(RelicType type, String name) {
        Relic relic = new Relic();
        relic.setRelicType(type);
        relic.setName(name);
        return relic;
    }
}