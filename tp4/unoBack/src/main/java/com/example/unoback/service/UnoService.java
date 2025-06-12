package com.example.unoback.service;

import com.example.unoback.model.Card;
import com.example.unoback.model.JsonCard;
import com.example.unoback.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UnoService {
    @Autowired
    private Dealer dealer;
    private Map< UUID, Match> sessions = new HashMap< UUID, Match>();

    public UUID newMatch(List<String> players) {
        UUID newKey = UUID.randomUUID();
        sessions.put(newKey, Match.fullMatch(dealer.fullDeck(), players));
        return newKey;
    }

    public void playCard(UUID matchId, String playerName, JsonCard jsonCard) {
        Match currentMatch = sessions.get(matchId);
        if (currentMatch == null) {
            throw new IllegalArgumentException("Match with ID " + matchId + " not found.");
        }
        Card cardToPlay = jsonCard.asCard();
        currentMatch.play(playerName, cardToPlay);
    }

    public void drawCard(UUID matchId, String playerName) {
        Match currentMatch = sessions.get(matchId);
        if (currentMatch == null) {
            throw new IllegalArgumentException("Match with ID " + matchId + " not found.");
        }
        currentMatch.drawCard(playerName);
    }

    public JsonCard getActiveCard(UUID matchId) {
        Match currentMatch = sessions.get(matchId);
        if (currentMatch == null) {
            throw new IllegalArgumentException("Match with ID " + matchId + " not found.");
        }
        return currentMatch.activeCard().asJson();
    }

    public List<JsonCard> getHand(UUID matchId) {
        Match currentMatch = sessions.get(matchId);
        if (currentMatch == null) {
            throw new IllegalArgumentException("Match with ID " + matchId + " not found.");
        }
        return currentMatch.playerHand().stream()
                .map(Card::asJson)
                .collect(java.util.stream.Collectors.toList());
    }
//
//    public List<JsonCard> getPlayerHand(UUID matchId, String playerName) {
//        Match currentMatch = sessions.get(matchId);
//        if (currentMatch == null) {
//            throw new IllegalArgumentException("Match with ID " + matchId + " not found.");
//        }
//        // Obtiene la mano del jugador y la convierte a una lista de JsonCard
//        return currentMatch.playerHand().stream()
//                .map(Card::asJson)
//                .collect(java.util.stream.Collectors.toList());
//    }
//
//    public String getCurrentPlayerName(UUID matchId) {
//        Match currentMatch = sessions.get(matchId);
//        if (currentMatch == null) {
//            throw new IllegalArgumentException("Match with ID " + matchId + " not found.");
//        }
//        // Obtiene el jugador actual de GameStatus
//        return currentMatch.currentPlayerName(); // Necesitarás un método como este en Match.java
//    }
//
//    public boolean isMatchOver(UUID matchId) {
//        Match currentMatch = sessions.get(matchId);
//        if (currentMatch == null) {
//            throw new IllegalArgumentException("Match with ID " + matchId + " not found.");
//        }
//        return currentMatch.isOver(); // Necesitarás un método isOver() en Match.java
//    }


}
