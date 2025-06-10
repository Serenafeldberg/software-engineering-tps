package com.example.unoback.service;

import com.example.unoback.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Dealer {

    public List<Card> fullDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        deck.addAll(cardsOn("Red"));
        deck.addAll(cardsOn("Blue"));
        deck.addAll(cardsOn("Green"));
        deck.addAll(cardsOn("Yellow"));
        Collections.shuffle(deck);
        return deck;
    }

    private List<Card> cardsOn(String colour) {
        return List.of(new WildCard(),
                new SkipCard(colour),
                new Draw2Card(colour),
                new ReverseCard(colour),
                new NumberCard(colour, 1),
                new NumberCard(colour, 2),
                new NumberCard(colour, 3),
                new NumberCard(colour, 4),
                new NumberCard(colour, 5),
                new NumberCard(colour, 6));
    }
}
