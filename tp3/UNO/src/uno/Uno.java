package uno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Uno {
    Card topCard;
    HashMap<String, Player> playerMap = new HashMap<>();
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
        createPlayers(playerNames, cardsPerPlayer, cardDeck); 
        assignPlayers(playerNames.size(), playerNames);
        currentPlayer = playerMap.get(playerNames.get(0));
        this.gameController = new RightController();
        this.topCard.startGame(this);

    }

    private void createPlayers(List<String> playerNames, Integer cardsPerPlayer, List<Card> cardDeck) {
        AtomicInteger currentIndex = new AtomicInteger(1);
        playerNames
                .forEach(name -> {
                    int start = currentIndex.get();
                    int end   = Math.min(start + cardsPerPlayer, cardDeck.size());
                    List<Card> hand = new ArrayList<>(cardDeck.subList(start, end));
                    currentIndex.set(end);

                    playerMap.put(name, new Player(hand, name));
                });

        this.remainingCards = new ArrayList<>(
                cardDeck.subList(currentIndex.get(), cardDeck.size())
        );


    }

    private void assignPlayers(int n, List<String> playerNames) {
        IntStream.range(0, n)
                .forEach(i -> {
                    String name     = playerNames.get(i);
                    String prevName = playerNames.get((i - 1 + n) % n);
                    String nextName = playerNames.get((i + 1) % n);

                    Player p    = playerMap.get(name);
                    Player prev = playerMap.get(prevName);
                    Player next = playerMap.get(nextName);

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


    public Uno takeTwo(Player player) {
        int toTake = Math.min(2, remainingCards.size());
        List<Card> cardsToTake = new ArrayList<>(remainingCards.subList(0, toTake));
        cardsToTake.forEach(player::addCard);
        remainingCards.subList(0, toTake).clear();
        return this;
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
        this.endGame = true; // esto haria un new UnoGanado()
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
