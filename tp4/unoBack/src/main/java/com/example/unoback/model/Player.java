package com.example.unoback.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public static String NotPlayersTurn = "It is not turn of player ";
    private List<Card> hand;
    private String name;

    public Player( String aName, List<Card> aHand ) {
        name = aName;
        hand = aHand;
    }

    public void assertTurnOf( String playerName ) {
        if ( !name.equals( playerName ) ) {
            throw new RuntimeException( NotPlayersTurn + playerName );
        }
    }

    public boolean noCardLeft() {
        return hand.isEmpty();
    }

    public boolean hasCard( Card aCard ) {
        return hand.contains( aCard );
    }

    public boolean oneCardLeft() {
        return hand.size() == 1;
    }

    public void removeCard( Card aCard ) {
        hand.remove( aCard );
    }

    public void drawCard( Card aCard ) {
        hand.add( aCard );
    }

    public List hand() {
        return new ArrayList( hand );
    }
}
