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
    boolean flow = false;


    public Uno(List<Card> cardDeck, Integer cardsPerPlayer, String... players){
        List<String> playerNames = Arrays.asList(players);

        if (!(cardDeck.size() > cardsPerPlayer * playerNames.size() + 1)){
            throw new RuntimeException("Card Deck not sufficient to play");
        }

        this.topCard = cardDeck.getFirst();// hay que llamar a topCard.startGame() --> condiciones
        createPlayers(playerNames, cardsPerPlayer, cardDeck);  // Ver de cambiar el nombre porque la funcion tamb reparte
        assignPlayers(playerNames.size(), playerNames);
        currentPlayer = playerMap.get(playerNames.get(0));

    }

    private void createPlayers(List<String> playerNames, Integer cardsPerPlayer, List<Card> cardDeck) {
        AtomicInteger currentIndex = new AtomicInteger(1);
        playerNames
                .forEach(name -> {
                    int start = currentIndex.get();
                    int end   = Math.min(start + cardsPerPlayer, cardDeck.size());
                    List<Card> hand = new ArrayList<>(cardDeck.subList(start, end));
                    currentIndex.set(end);

                    playerMap.put(name, new Player(hand));
                });

        this.remainingCards = cardDeck.subList(currentIndex.get(), cardDeck.size());
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

    public void setFlow(boolean flow){
        this.flow = flow;
    }

    public boolean isFlow(){
        return this.flow;
    }

    public Uno plays(String player1, Card card){
        currentPlayer = currentPlayer.plays(card, this);
        this.topCard = card;
        return this;
    }

    public Uno takeOne(){
        int toTake = Math.min(1, remainingCards.size());
        List<Card> takenCards = new ArrayList<>(remainingCards.subList(0, toTake)); // deberia ser una carta sola en vez de una lista
        remainingCards.subList(0, toTake).clear();
        currentPlayer.addCards(takenCards);
        return this;
    }

    public Uno takeTwo(Player player){
        int toTake = Math.min(2, remainingCards.size());
        List<Card> takenCards = new ArrayList<>(remainingCards.subList(0, toTake));
        remainingCards.subList(0, toTake).clear();
        player.addCards(takenCards);
        return this;
    }


    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }
}
