package com.example.unoback.controller;

import com.example.unoback.service.UnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UnoController {
    @Autowired
    UnoService unoService;

    @GetMapping("/hola")
    public ResponseEntity<String> holaMundo() {
        return new ResponseEntity<>("respuesta a Hola Mundo", HttpStatus.OK);
    }


    @PostMapping("newmatch")
    public ResponseEntity newMatch(@RequestParam List<String> players) {
        return ResponseEntity.ok(unoService.newMatch(players));
    }

}
