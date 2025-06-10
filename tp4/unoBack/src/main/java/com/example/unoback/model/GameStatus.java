package com.example.unoback.model;

import java.util.List;

public abstract class GameStatus {
    protected Player player;

    public GameStatus( Player aPlayer ) {
        player = aPlayer;
    }

    public Player player() { return player; };

    public abstract GameStatus right();
    public abstract GameStatus left();
    public abstract void assertTurnOf( String player );
    public abstract boolean isOver() ;
}
class GameOver extends GameStatus {
    public static String GameOver = "GameOver";

    public GameOver( Player aPlayer ) {
        super( aPlayer );
    }

    public GameStatus right() { return null;    }
    public GameStatus left() {  return null;    }

    public void assertTurnOf( String player ) {
       throw new RuntimeException( GameOver );
    }

    public boolean isOver() {
        return true;
    }
}
class Playing extends GameStatus {
    private Playing left;
    private Playing right;

    public Playing( String aName, List<Card> aHand ) {
        super( new Player( aName, aHand ) );
    }

    public Playing( Playing aPlayer, String aName, List<Card> aHand ) {
        this( aName, aHand );
        linkTo( aPlayer );
    }
    public void linkTo( Playing aPlayer ) {
        left = aPlayer;
        aPlayer.right = this;
    }

    public GameStatus right() {     return right; }
    public GameStatus left() {      return left;  }

    public void assertTurnOf( String playerName ) {
        player.assertTurnOf( playerName );
    }

    public boolean isOver() {
        return false;
    }
}