import java.util.ArrayList;
import java.util.List;

public class War {
    public static void main(String[] args) {
        // Initialize the deck and players
        Deck deck = new Deck();
        Player player1 = new Player();
        Player player2 = new Player();

        // Deal the cards

        for (int i = 0; i < 26; i++) {
            player1.receive(deck.draw());
            player2.receive(deck.draw());
        }
        // Play the game

        int rounds = 0;
        while (player1.hasCards() && player2.hasCards()) {
            rounds++;
            Card card1 = player1.play();
            Card card2 = player2.play();

            System.out.println("Round " + rounds + ": " + card1 + " vs " + card2);

            int result = card1.compare(card2);
            if (result > 0) {
                player1.receive(card1);
                player1.receive(card2);
            } else if (result < 0) {
                player2.receive(card1);
                player2.receive(card2);
            } else {
                System.out.println("War!");
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

                    System.out.println("War round: " + card1 + " vs " + card2);
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
        // Determine the winner

        if (player1.hasCards()) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("Player 2 wins!");
        }
    }
}


