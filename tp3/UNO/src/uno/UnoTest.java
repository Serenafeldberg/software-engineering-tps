package uno;

//import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnoTest {

    // Rojo
    private NumberedCard redZero;
    private NumberedCard redOne;
    private NumberedCard redTwo;
    private NumberedCard redThree;
    private NumberedCard redFour;
    private NumberedCard redFive;
    private NumberedCard redSix;
    private NumberedCard redSeven;
    private NumberedCard redEight;
    private NumberedCard redNine;

    // Verde
    private NumberedCard greenZero;
    private NumberedCard greenOne;
    private NumberedCard greenTwo;
    private NumberedCard greenThree;
    private NumberedCard greenFour;
    private NumberedCard greenFive;
    private NumberedCard greenSix;
    private NumberedCard greenSeven;
    private NumberedCard greenEight;
    private NumberedCard greenNine;

    // Azul
    private NumberedCard blueZero;
    private NumberedCard blueOne;
    private NumberedCard blueTwo;
    private NumberedCard blueThree;
    private NumberedCard blueFour;
    private NumberedCard blueFive;
    private NumberedCard blueSix;
    private NumberedCard blueSeven;
    private NumberedCard blueEight;
    private NumberedCard blueNine;

    // Amarillo
    private NumberedCard yellowZero;
    private NumberedCard yellowOne;
    private NumberedCard yellowTwo;
    private NumberedCard yellowThree;
    private NumberedCard yellowFour;
    private NumberedCard yellowFive;
    private NumberedCard yellowSix;
    private NumberedCard yellowSeven;
    private NumberedCard yellowEight;
    private NumberedCard yellowNine;

    // Different cards
    private WildCard wildCard;
    private Draw2Card draw2Red;
    private Draw2Card draw2Green;
    private Draw2Card draw2Blue;
    private Draw2Card draw2Yellow;
    private SkipCard skipRed;
    private SkipCard skipGreen;
    private SkipCard skipBlue;
    private SkipCard skipYellow;
    private ReverseCard reverseRed;
    private ReverseCard reverseGreen;
    private ReverseCard reverseBlue;
    private ReverseCard reverseYellow;

    @BeforeEach
    void setUp() {
        // Rojo
        redZero  = new NumberedCard("red", 0);
        redOne   = new NumberedCard("red", 1);
        redTwo   = new NumberedCard("red", 2);
        redThree = new NumberedCard("red", 3);
        redFour  = new NumberedCard("red", 4);
        redFive  = new NumberedCard("red", 5);
        redSix   = new NumberedCard("red", 6);
        redSeven = new NumberedCard("red", 7);
        redEight = new NumberedCard("red", 8);
        redNine  = new NumberedCard("red", 9);

// Verde
        greenZero  = new NumberedCard("green", 0);
        greenOne   = new NumberedCard("green", 1);
        greenTwo   = new NumberedCard("green", 2);
        greenThree = new NumberedCard("green", 3);
        greenFour  = new NumberedCard("green", 4);
        greenFive  = new NumberedCard("green", 5);
        greenSix   = new NumberedCard("green", 6);
        greenSeven = new NumberedCard("green", 7);
        greenEight = new NumberedCard("green", 8);
        greenNine  = new NumberedCard("green", 9);

// Azul
        blueZero  = new NumberedCard("blue", 0);
        blueOne   = new NumberedCard("blue", 1);
        blueTwo   = new NumberedCard("blue", 2);
        blueThree = new NumberedCard("blue", 3);
        blueFour  = new NumberedCard("blue", 4);
        blueFive  = new NumberedCard("blue", 5);
        blueSix   = new NumberedCard("blue", 6);
        blueSeven = new NumberedCard("blue", 7);
        blueEight = new NumberedCard("blue", 8);
        blueNine  = new NumberedCard("blue", 9);

// Amarillo
        yellowZero  = new NumberedCard("yellow", 0);
        yellowOne   = new NumberedCard("yellow", 1);
        yellowTwo   = new NumberedCard("yellow", 2);
        yellowThree = new NumberedCard("yellow", 3);
        yellowFour  = new NumberedCard("yellow", 4);
        yellowFive  = new NumberedCard("yellow", 5);
        yellowSix   = new NumberedCard("yellow", 6);
        yellowSeven = new NumberedCard("yellow", 7);
        yellowEight = new NumberedCard("yellow", 8);
        yellowNine  = new NumberedCard("yellow", 9);

// Wild Card
        wildCard      = new WildCard();

// Draw Two Cards
        draw2Red      = new Draw2Card("red");
        draw2Green    = new Draw2Card("green");
        draw2Blue     = new Draw2Card("blue");
        draw2Yellow   = new Draw2Card("yellow");

// Skip Cards
        skipRed       = new SkipCard("red");
        skipGreen     = new SkipCard("green");
        skipBlue      = new SkipCard("blue");
        skipYellow    = new SkipCard("yellow");

// Reverse Cards
        reverseRed    = new ReverseCard("red");
        reverseGreen  = new ReverseCard("green");
        reverseBlue   = new ReverseCard("blue");
        reverseYellow = new ReverseCard("yellow");

    }

    @Test
    public void test00JuegoInicial() {
        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo);
        assertEquals("red", new Uno(mazoSimple, 3, "Sere", "Santi").viewCard().colour());
        assertEquals(0, new Uno(mazoSimple, 3, "Sere", "Santi").viewCard().number());
    }

    @Test
    public void test01JugadorInicial() {
        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo);
        assertEquals("red", new Uno(mazoSimple, 3, "Sere", "Santi").plays("Sere", redOne).viewCard().colour());
        assertEquals(1, new Uno(mazoSimple, 3, "Sere", "Santi").plays("Sere", redOne).viewCard().number());
    }

    @Test
    public void test02BothPlayers() {
        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo);
        assertEquals("red", new Uno(mazoSimple, 3, "Sere", "Santi")
                .plays("Sere", redOne)
                .plays("Santi", redFive).viewCard().colour());
        assertEquals(5, new Uno(mazoSimple, 3, "Sere", "Santi")
                .plays("Sere", redOne)
                .plays("Santi", redFive).viewCard().number());
    }

    @Test
    public void test03PlayerCannotPlay() {
        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo);
        assertThrows(Exception.class, () -> new Uno(mazoSimple, 3, "Sere", "Santi")
                .plays("Sere", redFive));
        assertThrows(Exception.class, () -> new Uno(mazoSimple, 3, "Sere", "Santi")
                .plays("Sere", redZero)
                .plays("Santi", redFour)
                .plays("Sere", redZero));
    }

    @Test
    public void test04InsufficientCards() {
        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo);
        assertThrows(Exception.class, () -> new Uno(mazoSimple, 4, "Sere", "Santi"));
    }

    @Test
    public void test05WildCard() {
        List wildDeck = List.of(redZero, redOne, redTwo, wildCard, redFour, redFive, greenFive, blueTwo);
        assertEquals("green", new Uno(wildDeck, 3, "Sere", "Santi")
                .plays("Sere", wildCard.asGreen()).viewCard().colour());
    }

    @Test
    public void test06WildCardColour() {
        List wildDeck = List.of(redZero, redOne, redTwo, wildCard, redFour, redFive, greenFive, blueTwo, greenSix, greenSeven);
        assertEquals(6, new Uno(wildDeck, 3, "Sere", "Santi")
                .plays("Sere", wildCard.asGreen())
                .plays("Santi", redFour)
                .takeOne()
                .plays("Sere", greenSeven)
                .plays("Santi", greenSix)
                .viewCard().number());
        assertEquals("green", new Uno(wildDeck, 3, "Sere", "Santi")
                .plays("Sere", wildCard.asGreen())
                .plays("Santi", redFour)
                .takeOne()
                .plays("Sere", greenSeven)
                .plays("Santi", greenSix)
                .viewCard().colour());
    }

    @Test
    public void test07SkipCard() {
        List skipDeck = List.of(redZero, redOne, redTwo, skipRed, redFour, redFive, greenFive, blueTwo);
        assertEquals("red", new Uno(skipDeck, 3, "Sere", "Santi")
                .plays("Sere", skipRed).viewCard().colour());
        assertEquals("red", new Uno(skipDeck, 3, "Sere", "Santi")
                .plays("Sere", skipRed)
                .plays("Sere", redOne).viewCard().colour());
        assertEquals(1, new Uno(skipDeck, 3, "Sere", "Santi")
                .plays("Sere", skipRed)
                .plays("Sere", redOne).viewCard().number());
    }

    @Test
    public void test08SkipCardFails() {
        List skipDeck = List.of(redZero, redOne, redTwo, skipRed, redFour, redFive, greenFive, blueTwo);
        assertThrows(Exception.class, ()-> new Uno(skipDeck, 3, "Sere", "Santi")
                .plays("Sere", skipRed)
                .plays("Santi", redFive));
    }

    @Test
    public void test09SkipCardBigger() {
        List skipDeck = List.of(redZero, redOne, redTwo, skipRed,
                redFour, redFive, greenFive,
                redTwo, skipGreen, greenOne, greenTwo);
        assertEquals(2, new Uno(skipDeck, 3, "Sere", "Santi", "Juli")
                .plays("Sere", skipRed)
                .plays("Juli", skipGreen)
                .plays("Santi", greenFive)
                .plays("Juli", greenOne.asUno())
                .takeOne()
                .plays("Sere", greenTwo).viewCard().number());
    }

    @Test
    public void test10Draw2Simple(){
        List draw2Deck = List.of(redZero, redOne, redTwo, draw2Red,
                redFour, redFive, greenFive,
                redTwo, skipGreen,
                greenOne, greenTwo, redNine, redEight);
        assertEquals(2, new Uno(draw2Deck, 3, "Sere", "Santi")
                .plays("Sere", draw2Red)
                .plays("Sere", redOne)
                .plays("Santi", redTwo).viewCard().number());
    }

    @Test
    public void test11Draw2(){
        List draw2Deck = List.of(redZero, redOne, redTwo, draw2Red,
                redFour, redFive, greenFive,
                redTwo, skipGreen, greenOne,
                greenTwo, redNine,
                redEight);
        assertEquals(9, new Uno(draw2Deck, 3, "Sere", "Santi", "Juli")
                .plays("Sere", draw2Red)
                .takeOne()
                .plays("Juli", redEight)
                .plays("Sere", redOne)
                .plays("Santi", redNine).viewCard().number());
    }

    @Test
    public void test12ReverseCard () {
        List reverseDeck = List.of(redZero, redOne, redTwo, reverseRed,
                redOne, redFive, redSix,
                reverseRed, redNine, redZero);
        assertEquals(5 , new Uno(reverseDeck, 3, "Sere", "Santi", "Juli")
                .plays("Sere", reverseRed)
                .plays("Juli", reverseRed)
                .plays("Sere", redTwo.asUno())
                .plays("Santi", redFive).viewCard().number());

    }

    @Test
    public void test13ReverseCardExtended () {
        List reverseDeckExtended = List.of(redZero, redOne, reverseRed, reverseRed, redOne, redFive,
                redSix, blueOne, blueZero, redZero, greenZero,
                greenOne, reverseRed, reverseRed, blueTwo, yellowOne);
        assertEquals(1, new Uno(reverseDeckExtended, 5, "Sere", "Santi", "Juli")
                .plays("Sere", reverseRed)
                .plays("Juli", reverseRed)
                .plays("Sere", reverseRed)
                .plays("Juli", reverseRed)
                .plays("Sere", redOne).viewCard().number());
    }

    @Test
    public void test14UnoReveal(){
        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo);
        assertEquals("Sere", new Uno(mazoSimple, 3, "Sere", "Santi")
                .plays("Sere", redOne)
                .plays("Santi", redFive)
                .plays("Sere", redTwo.asUno())
                .plays("Santi", redFour.asUno())
                .plays("Sere", redThree)
                .winner());
    }

    @Test
    public void test15ForgetUnoReveal(){
        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo, redFive);
        assertEquals("Santi", new Uno(mazoSimple, 3, "Sere", "Santi")
                .plays("Sere", redOne)
                .plays("Santi", redFive)
                .plays("Sere", redTwo)
                .plays("Santi", redFour.asUno())
                .plays("Sere", redFive)
                .plays("Santi", greenFive)
                .winner());
    }

    @Test
    public void test16GameOver(){

        List mazoSimple = List.of(redZero, redOne, redTwo, redThree, redFour, redFive, greenFive, blueTwo, redFive);

        assertThrows(Exception.class, ()-> new Uno(mazoSimple, 3, "Sere", "Santi")
                .plays("Sere", redOne)
                .plays("Santi", redFive)
                .plays("Sere", redTwo.asUno())
                .plays("Santi", redFour.asUno())
                .plays("Sere", redThree)
                .plays("Santi", greenFive));
    }

    @Test
    public void test17StartWithDraw2(){
        List draw2Deck = List.of(draw2Green, redOne, redTwo, draw2Red,
                redFour, redFive, greenFive,
                redTwo, skipGreen,
                greenOne, greenTwo, redNine, redEight);
        assertEquals(1, new Uno(draw2Deck, 3, "Sere", "Santi")
                .plays("Santi", greenFive)
                .plays("Sere", skipGreen)
                .takeOne()
                .plays("Sere", greenOne).viewCard().number());
        assertEquals("green", new Uno(draw2Deck, 3, "Sere", "Santi")
                .plays("Santi", greenFive)
                .plays("Sere", skipGreen)
                .takeOne()
                .plays("Sere", greenOne).viewCard().colour());
    }

    @Test
    public void test18StartWithReverse(){
        List reverseDeck = List.of(reverseGreen, redOne, redTwo, draw2Red,
                redFour, redFive, greenFive,
                redTwo, skipGreen, greenOne,
                greenTwo, redNine, redEight);
        assertEquals(2, new Uno(reverseDeck, 3, "Sere", "Santi", "Juli")
                .plays("Juli", greenOne)
                .plays("Santi", greenFive)
                .takeOne()
                .plays("Sere", greenTwo).viewCard().number());
        assertEquals("green", new Uno(reverseDeck, 3, "Sere", "Santi", "Juli")
                .plays("Juli", greenOne)
                .plays("Santi", greenFive)
                .takeOne()
                .plays("Sere", greenTwo).viewCard().colour());
    }

    @Test
    public void test19StartWithSkip(){
        List skipDeck = List.of(skipGreen, redOne, redTwo, draw2Red,
                redFour, redFive, greenFive,
                redTwo, skipGreen, greenOne,
                greenTwo, redNine, redEight);
        assertEquals(2, new Uno(skipDeck, 3, "Sere", "Santi", "Juli")
                .plays("Santi", greenFive)
                .plays("Juli", greenOne)
                .takeOne()
                .plays("Sere", greenTwo).viewCard().number());
        assertEquals("green", new Uno(skipDeck, 3, "Sere", "Santi", "Juli")
                .plays("Santi", greenFive)
                .plays("Juli", greenOne)
                .takeOne()
                .plays("Sere", greenTwo).viewCard().colour());
    }

    @Test
    public void test20StartWithWild(){
        List wildDeck = List.of(wildCard, redOne, redTwo, draw2Red,
                redFour, redFive, greenFive,
                redTwo, skipGreen, greenOne,
                greenTwo, redNine, redEight);
        assertEquals(2, new Uno(wildDeck, 3, "Sere", "Santi", "Juli")
                .startGame("Sere", wildCard.asRed())
                .plays("Sere", redOne)
                .plays("Santi", redFour)
                .plays("Juli", redTwo).viewCard().number());
        assertEquals("red", new Uno(wildDeck, 3, "Sere", "Santi", "Juli")
                .startGame("Sere", wildCard.asRed())
                .plays("Sere", redOne)
                .plays("Santi", redFour)
                .plays("Juli", redTwo).viewCard().colour());
    }


}
