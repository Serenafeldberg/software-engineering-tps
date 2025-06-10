package com.example.unoback.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class UnoTest {

    static Card RedOn( int n ) { return new NumberCard( "Red", n ); }
    static Card BlueOn( int n ) { return new NumberCard( "Blue", n ); }
    static Card YellowOn( int n ) { return new NumberCard( "Yellow", n ); }
    static Card GreenOn( int n ) { return new NumberCard( "Green", n ); }
    static Card red1 = RedOn( 1 );
    static Card red2 = RedOn( 2 );
    static Card red3 = RedOn( 3 );
    static Card red4 = RedOn( 4 );
    static Card red5 = RedOn( 5 );
    static Card redDraw2 = new Draw2Card( "Red" );
    static Card redSkip = new SkipCard( "Red" );
    static Card blue1 = BlueOn( 1 );
    static Card blue2 = BlueOn( 2 );
    static Card blue3 = BlueOn( 3 );
    static Card blue4 = BlueOn( 4 );
    static Card green1 = GreenOn( 1 );
    static Card green3() { return GreenOn( 3 ); }
    static Card green5 = GreenOn( 5 );
    static Card yellow2 = YellowOn( 2 );
    static Card yellow3 = YellowOn( 3 );
    static Card yellow5 = YellowOn( 5 );
    static Card yellowReverse = new ReverseCard( "Yellow" );
    static WildCard wildCard() { return new WildCard(); }

    @Test void testNewDiscardPileHead() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        assertActiveCard( match, red1 );
    }

    @Test void testAFirtsValidDiscardOnSameNumber() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );
        match.play( "A", blue1 );

        assertActiveCard( match, blue1 );
    }

    @Test void testAFirtsValidDiscardOnSameColor() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );
        match.play( "A", blue1 );

        assertActiveCard( match, blue1 );
    }

    @Test void testBCannotPlayFirst() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        assertThrowsLike( Player.NotPlayersTurn + "B", () -> match.play( "B", blue1 ) );
        assertActiveCard( match, red1 );
    }

    @Test void testNumberOrColormMstMatch() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        assertThrowsLike( Match.CardDoNotMatch, () -> match.play( "A", blue2 ) );
        assertActiveCard( match, red1 );
    }

    @Test void testPlayerPlaysCardsInHand() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        assertThrowsLike(  Match.NotACardInHand + "A", () -> match.play( "A", BlueOn( 5 ) ) );
        assertActiveCard( match, red1 );
    }

    @Test void testPlayerCantPlayTwice() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        match.play( "A", blue1 );
        assertThrowsLike( Player.NotPlayersTurn + "A", () -> match.play( "A", blue2 ) );
        assertActiveCard( match, blue1 );
    }

    @Test void testPlayerAlternates() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        match.play( "A", blue1 );
        match.play( "B", blue1 );
        assertActiveCard( match, blue1 );
    }

    @Test void testPlayedCardIsNotInNextHand() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        match.play( "A", blue1 );
        match.play( "B", blue1 );
        assertThrowsLike( Match.NotACardInHand + "A", () -> match.play( "A", blue1 ) );
        assertActiveCard( match, blue1 );
    }

    @Test void testPlayedTakesACardaAndPlays() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        match.drawCard( "A" );
        match.play( "A", green1 );
        assertActiveCard( match, green1 );
    }

    @Test void testPlayerCanNotTakeIfNotItsTurn() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        assertThrowsLike( Player.NotPlayersTurn + "B", () -> match.drawCard( "B" ) );
        assertActiveCard( match, red1 );
    }

    @Test void testTakesConsumesCardFromPile() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        match.drawCard( "A" );
        match.play( "A", green1 );
        match.drawCard( "B" );
        match.play( "B", green5 );
        assertActiveCard( match, green5 );
    }

    @Test void testAPlayerCanWin() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        match.play( "A", blue1 );
        match.play( "B", blue1 );
        match.play( "A", blue2 );
        match.play( "B", blue2 );
        match.play( "A", blue3 );
        match.play( "B", green3() );
        match.play( "A", green3().uno() );
        assertFalse( match.isOver() );
        match.play( "B", yellow3.uno()  );
        match.play( "A", yellow3 );

        assertTrue( match.isOver() );
        assertThrowsLike( GameOver.GameOver, () -> match.drawCard( "B" ) );
        assertThrowsLike( GameOver.GameOver, () -> match.play( "B", red3 ) );
    }

    @Test void testAPlayerCantWinWithoutUno() {
        Match match = Match.newReducedMatch( deck(), "A", "B" );

        match.play( "A", blue1 );
        match.play( "B", blue1 );
        match.play( "A", blue2 );
        match.play( "B", blue2 );
        match.play( "A", blue3 );
        match.play( "B", green3() );
        match.play( "A", green3() );
        assertFalse( match.isOver() );
        match.play( "B", yellow3.uno()  );
        match.play( "A", yellow3 );

        assertFalse( match.isOver() );
        assertActiveCard( match, yellow3 );
    }

    @Test void testPlaySkipCard() {
        Match match = Match.newReducedMatch( trickyDeck(), "A", "B" );

        match.play( "A", red4 );
        match.play( "B", redSkip );
        assertActiveCard( match, redSkip );
        match.play( "B", red3 );
        assertActiveCard( match, red3 );
    }

    @Test void testPlayDraw2Card() {
        Match match = Match.newReducedMatch( trickyDeck(), "A", "B" );

        match.play( "A", redDraw2 );
        match.play( "B", red5 );
        assertActiveCard( match, red5 );
    }

    @Test void testPlayWildCard() {
        Match match = Match.newReducedMatch( trickyDeck(), "A", "B" );

        match.play( "A", wildCard().asYellow() );
        match.play( "B", yellow3 );
        assertActiveCard( match, yellow3 );
    }

    @Test void testPlayReverseCard() {
        Match match = Match.newReducedMatch( largeDeck(), "A", "B", "C" );

        match.play( "A", wildCard().asYellow() );
        match.play( "B", yellow3 );
        match.play( "C", yellow2 );
        match.play( "A", yellowReverse );
        match.play( "C", yellow5 );
        match.play( "B", wildCard().asBlue() );
        match.play( "A", blue2 );
        match.play( "C", blue4 );

        assertActiveCard( match, blue4 );
    }

    @Test void testPlaySkipCardWith3Players() {
        Match match = Match.newReducedMatch( largeDeck(), "A", "B", "C" );

        match.play( "A", red2 );
        match.play( "B", red4 );
        match.play( "C", redSkip );
        match.play( "B", redDraw2 );

        assertActiveCard( match, redDraw2 );
    }

    private static void assertActiveCard( Match match, Card card) {
        assertActiveCard( match, card, card.color() );
    }
    private static void assertActiveCard( Match match, Card card, String aColor ) {
        assertTrue( match.acceptsColor( aColor ) );
        assertEquals( card, match.activeCard() );
    }

    private void assertThrowsLike( String expectedMessage, Executable executable ) {
        assertEquals( expectedMessage,
                      assertThrows( RuntimeException.class, executable ).getMessage() );
    }

    private static List<Card> deck() {
        return List.of( red1,
                        blue1, blue2, green3(), blue3, yellow3,
                        blue1, blue2, green3(), red3, yellow3,
                        green1, green5 );
    }

    private static List<Card> trickyDeck() {
        return List.of( red1,
                        red4, redDraw2, wildCard(), blue3, yellow3,
                        blue1, redSkip, blue4, red3, yellow3,
                        green1, red5 );
    }

    private static List<Card> largeDeck() {
        return List.of( red1,
                        red2, blue2, wildCard(), yellowReverse, yellow3,
                        red4, redDraw2, wildCard(), blue3, yellow3,
                        yellow5, redSkip, blue4, red3, yellow2,
                        green1, red5  );
    }

}
