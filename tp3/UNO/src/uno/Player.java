package uno;

import java.util.List;
import java.util.Optional;

public class Player {
    List<Card> cards;
    Player next;
    Player prev;

    public Player(List<Card> cards) {
        this.cards = cards;
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
                .findFirst();            // buscamos la primera WildCard
        wild.ifPresent(cards::remove);                      // si existe, la removemos
        return wild.isPresent();                            // indicamos si había o no
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
        } else{
            throw new RuntimeException("Card not playable");
        }
        if (game.isFlow()){
            return game.getCurrentPlayer().getNext();
        } else {
            return game.getCurrentPlayer().getPrev();
        }
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
