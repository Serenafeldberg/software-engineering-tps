package com.example.unoback.controller;

import com.example.unoback.model.JsonCard;
import com.example.unoback.service.UnoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UnoController.class)
public class UnoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnoService unoService;

    @Test
    public void newMatchReturnsOkTest00() throws Exception {
        UUID mockUUID = UUID.randomUUID();
        when(unoService.newMatch(any())).thenReturn(mockUUID);

        mockMvc.perform(post("/newmatch?players=Ana&players=Luis"))
                .andExpect(status().isOk());
    }

    @Test
    public void testActiveCardReturnsOk() throws Exception {
        UUID matchId = UUID.randomUUID();

        JsonCard card = new JsonCard("red", 5, "NumberCard", false);
        when(unoService.getActiveCard(matchId)).thenReturn(card);

        mockMvc.perform(get("/activecard/" + matchId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.color").value("red"))
                .andExpect(jsonPath("$.number").value(5))
                .andExpect(jsonPath("$.type").value("NumberCard"))
                .andExpect(jsonPath("$.shout").value(false));
    }

    @Test
    public void testPlayCard_WithValidJsonCard_ReturnsOk() throws Exception {
        UUID matchId = UUID.randomUUID();

        doNothing().when(unoService).playCard(eq(matchId), eq("Ana"), any(JsonCard.class));

        mockMvc.perform(post("/play/" + matchId + "/Ana")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "color": "green",
                      "number": 5,
                      "type": "NormalCard",
                      "shout": false
                    }
                    """))
                .andExpect(status().isOk());
    }


}
