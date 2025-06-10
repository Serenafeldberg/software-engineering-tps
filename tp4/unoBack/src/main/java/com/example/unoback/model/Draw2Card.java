package com.example.unoback.model;

public class Draw2Card extends ColoredCard {
    public static Card asCard( JsonCard aJson ) {
        return new Draw2Card( aJson.getColor() ).shoutAs( aJson.isShout() );
    }

    public Draw2Card( String aColor ) { super( aColor ); }

    public void affectGame( Match partida ) {
        super.affectGame( partida );
        partida.draw();
        partida.draw();
    }
}
