package com.example.unoback.model;

public class ReverseCard extends ColoredCard {
    public static Card asCard( JsonCard aJson ) {
        return new ReverseCard( aJson.getColor() ).shoutAs( aJson.isShout() );
    }

    public ReverseCard( String aColor ) { super( aColor ); }

    public void affectGame( Match partida ) {
        partida.reverseShifturn();
        super.affectGame( partida );
    }
}
