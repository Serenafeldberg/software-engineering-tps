package com.example.unoback.service;

import com.example.unoback.model.JsonCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class UnoServiceTest {
    @Autowired
    private UnoService unoService;

    @Test
    public void newMatchTest00(){
        UUID id = unoService.newMatch(List.of("Serena", "Santiago"));
        Assertions.assertNotNull(id);
    }

    @Test
    public void activeCardTest01(){
        UUID id = unoService.newMatch(List.of("Serena", "Santiago"));
        JsonCard card = unoService.getActiveCard(id);
        Assertions.assertNotNull(card);
    }

    @Test
    public void activeCardFakeIDTest02(){
        UUID fakeId = UUID.randomUUID();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {unoService.getActiveCard(fakeId);});
    }
}
