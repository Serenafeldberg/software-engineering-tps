package com.example.unoback.service;

import com.example.unoback.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnoServiceSpringBootTest {

    @MockBean
    private Dealer dealer;

    @Autowired
    private UnoService service;

    private List<String> players;
    private List<com.example.unoback.model.Card> smallDeck;

    public static List<Card> fullDeck(){
        return List.of( new NumberCard("Red", 1),
                new NumberCard("Red", 2),
                new NumberCard("Red", 3),
                new NumberCard("Red", 4),
                new NumberCard("Red", 5),
                new WildCard(),
                new SkipCard("Red"),
                new Draw2Card("Red"),
                new NumberCard("Red", 1),
                new NumberCard("Red", 2),
                new NumberCard("Red", 3),
                new NumberCard("Red", 4),
                new WildCard(),
                new SkipCard("Red"),
                new Draw2Card("Red"),
                new NumberCard("Red", 6));

    }

    @BeforeEach
    void setUp() {
        List<Card> deck = fullDeck();
        when(dealer.fullDeck()).thenReturn(deck);
        players = List.of("A", "B");
    }

    @Test
    void newMatch_shouldInitializeGame() {
        UUID matchId = service.newMatch(players);
        assertNotNull(matchId, "newMatch debe devolver un UUID");

        // La carta activa debe ser la primera del smallDeck
        JsonCard active = service.getActiveCard(matchId);
        assertEquals("Red", active.getColor());
        assertEquals(1, active.getNumber());

        // La mano inicial de A debe tener 2 cartas (reparte round-robin)
        List<JsonCard> handA = service.getHand(matchId);
        assertEquals(7, handA.size());
    }

    @Test
    void playCard_invalidMatch_throws() {
        UUID fakeId = UUID.randomUUID();
        JsonCard card = new JsonCard("Red", 1, "NumberCard", false);
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.playCard(fakeId, "A", card)
        );
        assertTrue(ex.getMessage().contains("Match with ID"));
    }

    @Test
    void playCard_validMatch_removesCardFromHand() {
        UUID matchId = service.newMatch(players);
        List<JsonCard> before_A = service.getHand(matchId);
        JsonCard toPlayA = before_A.get(0);

        service.playCard(matchId, "A", toPlayA);

        List<JsonCard> before_B = service.getHand(matchId);
        JsonCard toPlayB = before_B.get(0);

        service.playCard(matchId, "B", toPlayB);

        List<JsonCard> after_A = service.getHand(matchId);

        assertEquals(before_A.size() - 1, after_A.size(),
                "La mano debe reducirse en 1 después de jugar");
    }

    @Test
    void drawCard_invalidMatch_throws() {
        UUID fakeId = UUID.randomUUID();
        assertThrows(
                IllegalArgumentException.class,
                () -> service.drawCard(fakeId, "A")
        );
    }

    @Test
    void drawCard_validMatch_addsCardToHand() {
        UUID matchId = service.newMatch(players);
        List<JsonCard> before = service.getHand(matchId);

        service.drawCard(matchId, "A");

        List<JsonCard> after = service.getHand(matchId);
        assertEquals(before.size() + 1, after.size(),
                "La mano debe aumentar en 1 después de robar");
    }

    @Test
    void getActiveCard_invalidMatch_throws() {
        UUID fakeId = UUID.randomUUID();
        assertThrows(
                IllegalArgumentException.class,
                () -> service.getActiveCard(fakeId)
        );
    }

    @Test
    void playCard_updatesActiveCard() {
        UUID matchId = service.newMatch(players);
        JsonCard firstHandCard = service.getHand(matchId).get(0);

        service.playCard(matchId, "A", firstHandCard);

        JsonCard newActive = service.getActiveCard(matchId);
        assertEquals(firstHandCard.getColor(), newActive.getColor());
        assertEquals(firstHandCard.getNumber(), newActive.getNumber());
    }


    @Test
    void playCard_invalidPlayer_throws() {
        UUID matchId = service.newMatch(players);
        JsonCard card = new JsonCard("Red", 1, "NumberCard", false);
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.playCard(matchId, "C", card)
        );

        assertTrue(ex.getMessage().contains("It is not turn of player C"));
    }

    @Test
    void playCard_samePlayerTwice_throws() {
        UUID matchId = service.newMatch(players);

        JsonCard firstHandCard = service.getHand(matchId).get(0);
        JsonCard secondHandCard = service.getHand(matchId).get(1);

        service.playCard(matchId, "A", firstHandCard);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.playCard(matchId, "A", secondHandCard)
        );

        assertTrue(ex.getMessage().contains("It is not turn of player A"));
    }

    @Test
    void getHand_invalidMatch_throws() {
        UUID fakeId = UUID.randomUUID();
        assertThrows(
                IllegalArgumentException.class,
                () -> service.getHand(fakeId)
        );
    }

    @Test
    void drawCard_invalidPlayer_throws() {
        UUID matchId = service.newMatch(players);
        assertThrows(
                RuntimeException.class,
                () -> service.drawCard(matchId, "C")
        );
    }

    @Test
    void multipleSessions_areIsolated() {
        UUID id1 = service.newMatch(players);
        UUID id2 = service.newMatch(players);

        JsonCard active1 = service.getActiveCard(id1);
        JsonCard active2 = service.getActiveCard(id2);
        assertEquals(active1.getColor(), active2.getColor());
        assertEquals(active1.getNumber(), active2.getNumber());

        // Play on session 1
        JsonCard card1 = service.getHand(id1).get(0);
        service.playCard(id1, "A", card1);

        // Ensure session 2 is unaffected
        List<JsonCard> hand2 = service.getHand(id2);
        assertEquals(7, hand2.size(), "La segunda sesión no debe verse afectada");
    }
}
