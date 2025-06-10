package com.example.unoback.model;

public class SkipCard extends ColoredCard {
    public static Card asCard( JsonCard aJson ) {
        return new SkipCard( aJson.getColor() ).shoutAs( aJson.isShout() );
    }

    public SkipCard( String aColor ) { super( aColor );}

    public void affectGame( Match partida ) {
        super.affectGame( partida );
        partida.shiftTurn();
    }
}
