package com.example.unoback.controller;

import com.example.unoback.exception.Handler;
import com.example.unoback.model.JsonCard;
import com.example.unoback.model.Player;
import com.example.unoback.service.Dealer;
import com.example.unoback.service.UnoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.unoback.model.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(UnoController.class)
@Import(Handler.class)
public class UnoControllerTest {
    @Autowired
    private MockMvc mockMvc;
//    @Autowired
//    UnoController unoController;
    @MockBean
    Dealer dealer;

    @MockBean
    UnoService unoService;

    private final ObjectMapper objectMapper = new ObjectMapper();


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
    void beforeEach() {
        when(dealer.fullDeck()).thenReturn(fullDeck());
    }

    @Test
    public void test01ValidMatch() throws Exception {
        UUID expectedUuid = UUID.randomUUID();
        List<String> players = List.of("Serena", "Santiago");

        Mockito.when(unoService.newMatch(players)).thenReturn(expectedUuid);

        String jsonRequest = objectMapper.writeValueAsString(players);

        String responseContent = mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID returnedUuid = UUID.fromString(new ObjectMapper().readValue(responseContent, String.class));
        assertEquals(expectedUuid, returnedUuid);
    }


    @Test
    public void test02InvalidMatch() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.when(unoService.newMatch(List.of())).thenThrow(new IllegalArgumentException("Player list empty"));
        mockMvc.perform(post("/newmatch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of())))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void test03PlayCardValid() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Serena";
        JsonCard cardToPlay = new JsonCard("Red", 1, "NumberCard", false);

        doNothing().when(unoService).playCard(eq(matchId), eq(player), any(JsonCard.class));

        mockMvc.perform(post("/play/{matchId}/{player}", matchId, player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardToPlay)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(unoService, times(1)).playCard(eq(matchId), eq(player), any(JsonCard.class));
    }

    @Test
    public void test04PlayCardInvalidTurn() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Santiago";
        JsonCard cardToPlay = new JsonCard("Red", 1, "NumberCard", false);

        String errorMessage = Player.NotPlayersTurn + player;

        doThrow(new IllegalArgumentException(errorMessage))
                .when(unoService).playCard(eq(matchId), eq(player), any(JsonCard.class));

        mockMvc.perform(post("/play/{matchId}/{player}", matchId, player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardToPlay)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"" + errorMessage + "\"}"));
        verify(unoService, times(1)).playCard(eq(matchId), eq(player), any(JsonCard.class));
    }

    @Test
    public void test05PlayCardInvalidCard() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Serena";
        JsonCard cardToPlay = new JsonCard("Blue", 9, "NumberCard", false);

        doThrow(new IllegalArgumentException("Invalid card played"))
                .when(unoService).playCard(eq(matchId), eq(player), any(JsonCard.class));

        mockMvc.perform(post("/play/{matchId}/{player}", matchId, player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardToPlay)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Invalid card played\"}"));
        verify(unoService, times(1)).playCard(eq(matchId), eq(player), any(JsonCard.class));
    }

    @Test
    public void test06DrawCardValid() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Serena";

        doNothing().when(unoService).drawCard(matchId, player);

        mockMvc.perform(post("/draw/{matchId}/{player}", matchId, player))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(unoService, times(1)).drawCard(matchId, player);
    }

    @Test
    public void test07DrawCardInvalidMatchId() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Serena";

        doThrow(new IllegalArgumentException("Match not found"))
                .when(unoService).drawCard(matchId, player);

        mockMvc.perform(post("/draw/{matchId}/{player}", matchId, player))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Match not found\"}"));
        verify(unoService, times(1)).drawCard(matchId, player);
    }

    @Test
    public void test08GetActiveCardValid() throws Exception {
        UUID matchId = UUID.randomUUID();
        JsonCard activeCard = new JsonCard("Green", 7, "NumberCard", false);

        when(unoService.getActiveCard(matchId)).thenReturn(activeCard);

        String responseContent = mockMvc.perform(get("/activecard/{matchId}", matchId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonCard returnedCard = objectMapper.readValue(responseContent, JsonCard.class);
        assertEquals(activeCard.getColor(), returnedCard.getColor());
        assertEquals(activeCard.getNumber(), returnedCard.getNumber());
        assertEquals(activeCard.getType(), returnedCard.getType());
        assertEquals(activeCard.isShout(), returnedCard.isShout());

        verify(unoService, times(1)).getActiveCard(matchId);
    }

    @Test
    public void test09GetActiveCardMatchNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        when(unoService.getActiveCard(matchId)).thenThrow(new IllegalArgumentException("Match not found"));

        mockMvc.perform(get("/activecard/{matchId}", matchId))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Match not found\"}"));
        verify(unoService, times(1)).getActiveCard(matchId);
    }

    private List<JsonCard> convertCardsToJsonCards(List<Card> cards) {
        return cards.stream()
                .map(Card::asJson)
                .collect(Collectors.toList());
    }

    @Test
    public void test10GetPlayerHandValidWithFullDeck() throws Exception {
        UUID matchId = UUID.randomUUID();
        List<Card> deckCards = fullDeck();
        List<JsonCard> expectedPlayerHand = convertCardsToJsonCards(deckCards);

        when(unoService.getHand(matchId)).thenReturn(expectedPlayerHand);

        String responseContent = mockMvc.perform(get("/playerhand/{matchId}", matchId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<JsonCard> returnedHand = objectMapper.readValue(responseContent, new TypeReference<List<JsonCard>>(){});
        assertEquals(expectedPlayerHand.size(), returnedHand.size());
        for (int i = 0; i < expectedPlayerHand.size(); i++) {
            assertEquals(expectedPlayerHand.get(i).getColor(), returnedHand.get(i).getColor());
            assertEquals(expectedPlayerHand.get(i).getNumber(), returnedHand.get(i).getNumber());
            assertEquals(expectedPlayerHand.get(i).getType(), returnedHand.get(i).getType());
            assertEquals(expectedPlayerHand.get(i).isShout(), returnedHand.get(i).isShout());
        }

        verify(unoService, times(1)).getHand(matchId);
    }

    @Test
    public void test11GetPlayerHandMatchNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        when(unoService.getHand(matchId)).thenThrow(new IllegalArgumentException("Match not found"));

        mockMvc.perform(get("/playerhand/{matchId}", matchId))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Match not found\"}"));
        verify(unoService, times(1)).getHand(matchId);
    }

    @Test
    public void test12GetPlayerHandPlayerNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        when(unoService.getHand(matchId)).thenThrow(new IllegalArgumentException("Player hand could not be retrieved"));

        mockMvc.perform(get("/playerhand/{matchId}", matchId))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Player hand could not be retrieved\"}"));
        verify(unoService, times(1)).getHand(matchId);
    }

    @Test
    public void test13CreateMatchFailing1Player() throws Exception {
        List<String> players = List.of("Serena");

        Mockito.when(unoService.newMatch(players))
                .thenThrow(new IllegalArgumentException("Player list cannot less than 2"));
        mockMvc.perform(post("/newmatch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(players)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Player list cannot less than 2\"}"));
    }

    @Test
    public void test14PlayNoMatchPassed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new JsonCard("Red", 1, "NumberCard", false)))
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void test15PlayNoPlayerPassed() throws Exception {
        UUID matchId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/play/" + matchId + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new JsonCard("Red", 1, "NumberCard", false)))
                )
                .andExpect(status().isInternalServerError());
    }
}
