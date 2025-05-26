package uno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Uno {
    Card topCard;
    List<Card> remainingCards = new ArrayList<>();
    Player currentPlayer;
    String winner;
    boolean endGame = false;

    Controller gameController;


    public Uno(List<Card> cardDeck, Integer cardsPerPlayer, String... players){
        List<String> playerNames = Arrays.asList(players);

        if (!(cardDeck.size() >= cardsPerPlayer * playerNames.size() + 1)){
            throw new RuntimeException("Card Deck not sufficient to play");
        }

        this.topCard = cardDeck.getFirst();
        List<Player> playerList = createPlayers(playerNames, cardsPerPlayer, cardDeck);
        assignPlayers(playerList);
        this.currentPlayer  = playerList.get(0);
        int dealtCount      = cardsPerPlayer * playerNames.size();
        this.remainingCards = new ArrayList<>(
                cardDeck.subList(1 + dealtCount, cardDeck.size())
        );

        this.gameController = new RightController();
        this.topCard.startGame(this);

    }

    private List<Player> createPlayers(List<String> playerNames, Integer cardsPerPlayer, List<Card> cardDeck) {
        AtomicInteger currentIndex = new AtomicInteger(1);
        return playerNames.stream()
                .map(name -> {
                    int start = currentIndex.get();
                    int end   = Math.min(start + cardsPerPlayer, cardDeck.size());
                    List<Card> hand = new ArrayList<>(cardDeck.subList(start, end));
                    currentIndex.set(end);
                    return new Player(hand, name);
                })
                .collect(Collectors.toList());
    }

    private void assignPlayers(List<Player> playerList) {
        int n = playerList.size();
        IntStream.range(0, n)
                .forEach(i -> {
                    Player p    = playerList.get(i);
                    Player prev = playerList.get((i - 1 + n) % n);
                    Player next = playerList.get((i + 1) % n);
                    p.setPrev(prev);
                    p.setNext(next);
                });
    }

    public Card viewCard(){
        return this.topCard;
    }

    public Uno plays(String player1, Card card){

        if (!endGame){
            currentPlayer = currentPlayer.plays(card, this);
            return this;
        }

        throw new RuntimeException("The game is over.");

    }

    public Uno takeOne() {

        if (remainingCards.isEmpty()) {
            throw new IllegalStateException("Insufficient cards to play");
        }
        Card drawn = remainingCards.remove(0);
        currentPlayer.addCard(drawn);
        return this;
    }


    public void takeTwo(Player player) {
        int toTake = Math.min(2, remainingCards.size());
        List<Card> cardsToTake = new ArrayList<>(remainingCards.subList(0, toTake));
        cardsToTake.forEach(player::addCard);
        remainingCards.subList(0, toTake).clear();
    }


    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public void setTopCard(Card topCard){
        this.topCard = topCard;
    }

    public void setWinner(String winner){
        this.winner = winner;
        this.endGame = true;
    }

    public String winner(){
        return winner;
    }

    public void setController(Controller newController){
        this.gameController = newController;
    }

    public Controller getController(){
        return this.gameController;
    }

    public Uno startGame(String name, Card card){
        this.topCard = card;
        return this;
    }
}
