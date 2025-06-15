package com.example.unoback.controller;

import com.example.unoback.model.JsonCard;
import com.example.unoback.service.UnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class UnoController {
    @Autowired
    UnoService unoService;

    @PostMapping("newmatch")
    public ResponseEntity<UUID> newMatch(@RequestBody List<String> players) {
        return ResponseEntity.ok(unoService.newMatch(players));
    }

    @PostMapping("play/{matchId}/{player}")
    public ResponseEntity<Void> play(
                                      @PathVariable UUID matchId,
                                      @PathVariable String player,
                                      @RequestBody JsonCard card
    ) {
        unoService.playCard(matchId, player, card);
        return ResponseEntity.ok().build();
    }

    @PostMapping("draw/{matchId}/{player}")
    public ResponseEntity<Void> drawCard(
            @PathVariable UUID matchId,
            @PathVariable String player
    ) {
        unoService.drawCard(matchId, player);
        return ResponseEntity.ok().build();
    }

    @GetMapping("activecard/{matchId}")
    public ResponseEntity<JsonCard> activeCard(@PathVariable UUID matchId) {
        return ResponseEntity.ok(unoService.getActiveCard(matchId));
    }

    @GetMapping("playerhand/{matchId}")
    public ResponseEntity<List<JsonCard>> playerHand(@PathVariable UUID matchId) {
        return ResponseEntity.ok(unoService.getHand(matchId));
    }

}