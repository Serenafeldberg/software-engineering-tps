package com.example.unoback.model;

public abstract class Card {
    private boolean uno = false;

    public Card() {}
    public boolean equals( Object o ) {
        return  (this == o) ||
                (o != null && getClass() == o.getClass());
    }
    public Card uno() { return shoutAs( true );     }
    public String color() {    return null;    }
    public boolean unoShouted() { return uno;     }
    public boolean acceptsOnTop( Card aCard ) { return false;   }
    public boolean yourNumberIs( int number ) { return false;   }
    public boolean yourColorIs( String color ) { return false;  }
    public void affectGame( Match partida ) { partida.shiftTurn();  }

    public Card shoutAs( boolean shouted ) { uno = shouted; return this; }
    public abstract JsonCard asJson();
}

