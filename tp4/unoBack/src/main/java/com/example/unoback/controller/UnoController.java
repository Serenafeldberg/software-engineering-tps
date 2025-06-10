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

import com.example.unoback.model.Match;
import static com.example.unoback.model.Player.NotPlayersTurn;

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

    @PostMapping("play/{matchId}/{player}")
    public ResponseEntity play( @PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        try {
            unoService.playCard(matchId, player, card);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            if (e.getMessage() != null) {
                if (e.getMessage().contains(Match.NotACardInHand) ||
                        e.getMessage().contains(Match.CardDoNotMatch) ||
                        e.getMessage().contains(NotPlayersTurn)) { // Si intentas jugar en una partida terminada CHEQUEAR GAMEOVER
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                } else {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected game rule error occurred: " + e.getMessage(), e);
                }
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error occurred.", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage(), e);
        }
    }

//    @PostMapping("draw/{matchId}/{player}")
//    public ResponseEntity drawCard( @PathVariable UUID matchId, @RequestParam String player ) {
//
//    }

    @GetMapping("activecard/{matchId}")
    public ResponseEntity activeCard( @PathVariable UUID matchId ) {
        return ResponseEntity.ok(unoService.getActiveCard(matchId));
    }
//
//    @GetMapping("playerhand/{matchId}")
//    public ResponseEntity playerHand( @PathVariable UUID matchId ) {
//
//    }

}