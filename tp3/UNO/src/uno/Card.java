package uno;

public abstract class Card {
    String colour;

    public Card(String colour) {
        this.colour = colour;
    }

    public Card(){}

    public String colour(){
        return colour;
    }
    public Integer number(){
        return -1;
    }

    public abstract boolean playAgainst(Card other);
    public abstract void plays(Uno game);


}

class ColouredCard extends Card {
    public ColouredCard(String colour) {
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return true;
    }

    public void plays(Uno game) {

    }
}

class NumberedCard extends Card {
    Integer number;
    public NumberedCard(String colour, int number){
        super(colour);
        this.number = number;
    }

    @Override
    public Integer number() {
        return number;
    }

    public boolean playAgainst(Card other) {
        return other.number().equals(this.number()) || other.colour().equals(this.colour());
    }

    public void plays(Uno game) {

    }

}

class WildCard extends Card {

    public ColouredCard asGreen(){
        return new ColouredCard("green");
    }

    public ColouredCard asRed(){
        return new ColouredCard("red");
    }

    public ColouredCard asBlue(){
        return new ColouredCard("blue");
    }

    public ColouredCard asYellow(){
        return new ColouredCard("yellow");
    }

    public boolean playAgainst(Card other) {
        return true;
    }

    public void plays(Uno game) {
        throw new RuntimeException("Cannot play WildCard as itself");
    }
}

class Draw2Card extends Card {

    public Draw2Card(String colour){
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return other.colour().equals(this.colour());
    }

    public void plays(Uno game) {
       if (game.isFlow()){
           game.takeTwo(game.getCurrentPlayer().getNext());
       } else{
           game.takeTwo(game.getCurrentPlayer().getPrev());
       }
    }
}

class ReverseCard extends Card {

    public ReverseCard(String colour){
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return other.colour().equals(this.colour());
    }

    public void plays(Uno game) {
        game.setFlow(!game.isFlow());
    }
}

class SkipCard extends Card {

    public SkipCard(String colour){
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return other.colour().equals(this.colour());
    }

    public void plays(Uno game) {
        if (game.isFlow()){
            game.setCurrentPlayer(game.getCurrentPlayer().getNext());
        } else{
            game.setCurrentPlayer(game.getCurrentPlayer().getPrev());
        }
    }

}