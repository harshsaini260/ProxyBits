import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class junit {
    private Deck deck;
    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        deck = new Deck();
        player1 = new Player();
        player2 = new Player();
    }

    @Test
    public void testDeckDraw() {
        Card card = deck.draw();
        assertNotNull(card);
    }

    @Test
    public void testPlayerReceiveAndPlay() {
        Card card = deck.draw();
        player1.receive(card);
        Card playedCard = player1.play();
        assertEquals(card, playedCard);
    }

    @Test
    public void testPlayerHasCards() {
        Card card = deck.draw();
        player1.receive(card);
        assertTrue(player1.hasCards());
    }

    @Test
    public void testPlayerHasNoCards() {
        assertFalse(player1.hasCards());
    }

    @Test
    public void testCardCompare() {
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        Card card2 = new Card(Card.Suit.DIAMONDS, Card.Rank.THREE);
        assertTrue(card1.compare(card2) < 0);
    }

    @Test
    public void testDeckIsEmptyAfterDrawingAllCards() {
        for (int i = 0; i < 52; i++) {
            assertNotNull(deck.draw());
        }
        try {
            deck.draw();
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    @Test
    public void testGamePlay() {
        for (int i = 0; i < 26; i++) {
            player1.receive(deck.draw());
            player2.receive(deck.draw());
        }

        int rounds = 0;
        while (player1.hasCards() && player2.hasCards() && rounds < 1000) {
            rounds++;
            Card card1 = player1.play();
            Card card2 = player2.play();

            int result = card1.compare(card2);
            if (result > 0) {
                player1.receive(card1);
                player1.receive(card2);
            } else if (result < 0) {
                player2.receive(card1);
                player2.receive(card2);
            } else {
                List<Card> pile = new ArrayList<>();
                pile.add(card1);
                pile.add(card2);

                boolean warResolved = false;
                while (player1.hasCards() && player2.hasCards() && !warResolved) {
                    for (int i = 0; i < 3 && player1.hasCards() && player2.hasCards(); i++) {
                        pile.add(player1.play());
                        pile.add(player2.play());
                    }

                    card1 = player1.play();
                    card2 = player2.play();

                    result = card1.compare(card2);

                    pile.add(card1);
                    pile.add(card2);

                    if (result > 0) {
                        player1.receive(pile);
                        warResolved = true;
                    } else if (result < 0) {
                        player2.receive(pile);
                        warResolved = true;
                    }
                }
            }
        }

        assertTrue(rounds < 1000);
    }
}

