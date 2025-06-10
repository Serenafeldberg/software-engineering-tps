package com.example.unoback.model;

public class WildCard extends Card {
    private String assignedColor = "";

    public static Card asCard( JsonCard aJson ) {
        return new WildCard().asColor( aJson.getColor() ).shoutAs( aJson.isShout() );
    }

    public Card asColor( String aColor ) {
        assignedColor = aColor;
        return this;
    }

    public Card asYellow() {    return asColor( "Yellow" ); }
    public Card asBlue() {      return asColor( "Blue" );   }
    public Card asGreen() {     return asColor( "Green" );  }
    public Card asRed() {       return asColor( "Red" );    }

    public boolean acceptsOnTop( Card aCard ) { return  aCard.yourColorIs( assignedColor );   }
    public boolean yourColorIs( String color ) { return true;  }

    public JsonCard asJson() { return new JsonCard( assignedColor, null, getClass().getSimpleName(), unoShouted() ); }

}
