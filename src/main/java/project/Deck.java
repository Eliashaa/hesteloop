package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private final ArrayList<Card> deck = new ArrayList<>();
    private final List<Character> legalFaces = List.of('C', 'D', 'H', 'S');

    public Deck() {
        for (Character face : legalFaces) {
            for (int i = 2; i <= 13; i++) {
                Card card = new Card(face, i);

                deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        ArrayList<Card> cards = new ArrayList<>(deck);
        ArrayList<Card> shuffledDeck = new ArrayList<>();
        Random random = new Random();

        while (!cards.isEmpty()) {
            int randomIndex = random.nextInt(cards.size());
            Card card = cards.remove(randomIndex);
            shuffledDeck.add(card);
        }

        deck.clear();
        deck.addAll(shuffledDeck);
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println("Deck size before shuffle: " + deck.getDeck().size());
        deck.shuffleDeck();

        for (Card card : deck.getDeck()) {
            System.out.println(card + " - " + card.getImagePath());
        }
    }
}
