package project;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private Character suit;
    private int value;
    private ArrayList<Character> legalFaces = new ArrayList<>(List.of('C', 'D', 'H', 'S'));
    private ArrayList<Integer> legalValues = new ArrayList<>(List.of(2, 3, 4, 5, 5, 6, 7, 8, 9, 10, 11, 12, 13));
    private boolean shown = false;
    private String imagePath;
    private boolean transposed = false;

    public Card(Character suit, int rank) {
        if (!legalFaces.contains(suit)) {
            throw new IllegalArgumentException();
        }
        if (!legalValues.contains(rank)) {
            throw new IllegalArgumentException();
        }
        this.suit = suit;
        this.value = rank;
        this.imagePath = generateImagePath(suit, rank);
    }

    public Character getSuit() {
        return suit;
    }

    public int getRank() {
        return value;
    }

    public String toString() {
        return this.suit + "" + this.value;

    }

    public void show() {
        this.shown = true;
    }

    public boolean getShown() {
        return this.shown;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void transpose(){
        this.transposed = true;
    }

    public boolean getTransposed(){
        return this.transposed;
    }

    private String generateImagePath(char suit, int rank) {
        String suitString = null;
        if (suit == 'C') {
            suitString = "clubs";
        } else if (suit == 'D') {
            suitString = "diamonds";
        } else if (suit == 'H') {
            suitString = "hearts";
        } else if (suit == 'S') {
            suitString = "spades";
        }
        return "images/cards/" + rank + "_of_" + suitString + ".png";
    }

    public static void main(String[] args) {
        Card card = new Card('C', 2);

        System.out.println(card.getImagePath());
    }

}
