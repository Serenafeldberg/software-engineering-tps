package com.example.unoback.model;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class JsonCard {
    String color;
    Integer number;
    String type;
    boolean shout;

    public JsonCard() {}
    public JsonCard( String color, Integer number, String type, boolean shout ) {
        this.color = color;
        this.number = number;
        this.type = type;
        this.shout = shout;
    }

    public String toString() {
        return "{ " +
               "\"color\": \"" + color + "\", " +
               "\"number\": \"" + number + "\", " +
               "\"type\": \"" + type + "\", " +
               "\"shout\": \"" + shout + "\" " +
               "}";
    }

    @SneakyThrows public Card asCard() {
        return (Card)Class.forName( "org.udesa.unoback.model." + type )
                .getMethod( "asCard", getClass() )
                .invoke( getClass(), this );
    }
}

