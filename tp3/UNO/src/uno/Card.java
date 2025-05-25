package uno;

public abstract class Card {
    String colour;
    boolean cantada;

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
    public boolean isCantada(){ return cantada; }
    public Card asUno(){
        cantada = true;
        return this;
    }

    public abstract boolean playAgainst(Card other);
    public abstract void plays(Uno game, Player player);
//    public abstract void startGame(Uno game);

}

class ColouredCard extends Card {
    public ColouredCard(String colour) {
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return true;
    }

    public void plays(Uno game, Player player) {}
//    public void startGame(Uno game) {}
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

    public void plays(Uno game, Player player) {}
//    public void startGame(Uno game) {}

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

    public void plays(Uno game, Player player) {
        throw new RuntimeException("Cannot play WildCard as itself");
    }
//    public void startGame(Uno game) {}
}

class Draw2Card extends Card {

    public Draw2Card(String colour){
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return other.colour().equals(this.colour()) || other.getClass().equals(this.getClass());
    }

    public void plays(Uno game, Player player) {

//        game.takeTwo(game.getController().whoIsNext(game));
        game.takeTwo(player);
        game.setCurrentPlayer(game.getController().whoIsNext(game));

    }

//    public void startGame(Uno game) {
//        game.takeTwo(game.getCurrentPlayer());
//        game.setCurrentPlayer(game.getController().whoIsNext(game));
//    }
}

class ReverseCard extends Card {

    public ReverseCard(String colour){
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return other.colour().equals(this.colour()) || other.getClass().equals(this.getClass());
    }

    public void plays(Uno game, Player player) {
        game.setController(game.getController().changeController());
        game.setCurrentPlayer(player);
    }

//    public void startGame(Uno game) {
//        game.setController(game.getController().changeController());
//        game.setCurrentPlayer(game.getController().whoIsNext(game));
//    }
}

class SkipCard extends Card {

    public SkipCard(String colour){
        super(colour);
    }

    public boolean playAgainst(Card other) {
        return other.colour().equals(this.colour()) || other.getClass().equals(this.getClass());
    }

    public void plays(Uno game, Player player) {
        game.setCurrentPlayer(game.getController().whoIsNext(game));
    }

//    public void startGame(Uno game) {
//        game.setCurrentPlayer(game.getController().whoIsNext(game));
//    }

}
