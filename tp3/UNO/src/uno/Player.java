package uno;

import java.util.List;
import java.util.Optional;

public class Player {
    List<Card> cards;
    Player next;
    Player prev;
    String name;

    public Player(List<Card> cards, String name) {
        this.cards = cards;
        this.name = name;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public void setPrev(Player prev) {
        this.prev = prev;
    }

    private boolean removeOneWildCard() {
        Optional<Card> wild = cards.stream()
                .filter(c -> c instanceof WildCard)
                .findFirst();
        wild.ifPresent(cards::remove);
        return wild.isPresent();
    }


    public Player plays (Card card, Uno game){

        if (card instanceof ColouredCard){
            if (removeOneWildCard()) {
                cards.add(card);
            }

        }
        if (!cards.contains(card)){
            throw new RuntimeException("Player does not contain card");
        }
        boolean playable = card.playAgainst(game.viewCard());


        if (playable) {
            cards.remove(card);
            card.plays(game);
            game.setTopCard(card);

            if (cards.size() == 1 && !(card.isCantada())) {
                game.takeTwo(this);
            }
        } else{

            game.takeTwo(this);

        }


        if (cards.isEmpty()){
            game.setWinner(name);
        }

        return game.getController().whoIsNext(game);

    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public Player getNext() {
        return next;
    }

    public Player getPrev() {
        return prev;
    }
}

