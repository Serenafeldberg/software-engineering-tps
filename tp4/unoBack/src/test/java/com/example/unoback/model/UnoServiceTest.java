package com.example.unoback.model;

import com.example.unoback.service.UnoService;
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
    public void newMatchTest(){
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        Assertions.assertNotNull(id);
    }
}
