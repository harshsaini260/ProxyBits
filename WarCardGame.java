import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Represents a playing card with a suit and rank.
 */
class Card {
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int compare(Card other) {
        return this.rank.compareTo(other.rank);
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

/**
 * Represents a deck of playing cards.
 */
class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }
}

/**
 * Represents a player in a card game.
 */
class Player {
    private final Queue<Card> hand = new LinkedList<>();

    // Add a single card to the player's hand
    public void receive(Card card) {
        hand.offer(card);
    }

    // Add a pile of cards to the player's hand
    public void receive(List<Card> cards) {
        for (Card card : cards) {
            hand.offer(card);
        }
    }

    // Play the top card from the player's hand
    public Card play() {
        return hand.poll();
    }

    // Check if the player has cards in their hand
    public boolean hasCards() {
        return !hand.isEmpty();
    }
}


