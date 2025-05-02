package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game {
    private Deck deck = new Deck();

    private Stack<Card> deckStack = new Stack<>();

    private Stack<Card> deckStackEmpty = new Stack<>();

    private ArrayList<Card> flipCards = new ArrayList<>();

    private ArrayList<ArrayList<Boolean>> board = new ArrayList<>();

    private ArrayList<GameObserver> observers = new ArrayList<>();

    private ArrayList<Character> legalFaces = new ArrayList<>(List.of('C', 'D', 'H', 'S'));

    private boolean isGameOver;

    private Character winner;

    public Game() {
        deck.shuffleDeck();
        deckStack.addAll(deck.getDeck());
        for (Character c : legalFaces) {
            observers.add(new GameObserver(c));
        }

        for (int col = 0; col <= 3; col++) {
            ArrayList<Boolean> r = new ArrayList<>();
            for (int row = 0; row <= 8; row++) {
                if (row == 0) {
                    r.add(true);
                } else {
                    r.add(false);
                }
            }
            board.add(r);
        }
        for (int i = 0; i <= 6; i++) {
            flipCards.add(deckStack.pop());
        }

        flipCards.get(2).transpose();
        flipCards.get(5).transpose();
    }

    public ArrayList<Card> getFlipCards() {
        return flipCards;
    }

    public Card flipNextCard(int col) {
        Card card = null;
        if (!flipCards.get(col).getShown()) {
            card = flipCards.get(col);
            flipCards.get(col).show();
        }
        return card;
    }

    public ArrayList<ArrayList<Boolean>> getBoard() {
        return board;
    }

    public ArrayList<Card> getDeck() {
        return new ArrayList<>(this.deckStack);
    }

    public int getAcePosition(Character face) {
        int col = getCol(face);
        return board.get(col).indexOf(true);
    }

    public int getCol(Character face) {
        face = Character.toUpperCase(face);
        switch (face) {
            case 'D':
                return 1;
            case 'H':
                return 2;
            case 'S':
                return 3;
            default:
                return 0;
        }
    }

    public Card drawCard() {
        if (deckStack.empty()) {
            if (deckStackEmpty.isEmpty()) {
                throw new IllegalStateException("Both deckStack and deckStackEmpty are empty!");
            }
            deckStack.addAll(deckStackEmpty);
            deckStackEmpty.clear();
        }
        Card drawCard = deckStack.pop();
        deckStackEmpty.add(drawCard);
        int row = getCol(drawCard.getSuit());
        int col = getAcePosition(drawCard.getSuit());
        swapPositions(col, col + 1, row);
        for (GameObserver g : this.observers) {
            g.update(drawCard.getSuit(), col + 1);
        }
       
        checkGameOver();
        return drawCard;
    }

    public void swapPositions(int indexTrue, int indexFalse, int row) {
        board.get(row).set(indexTrue, false);
        board.get(row).set(indexFalse, true);
    }

    public Card drawFlipCard() {
       
        Card flipCard = flipCards.stream()
                .filter(c -> !c.getShown())
                .findFirst()
                .orElse(null);
    
        if (flipCard != null) {
            int index = getFlipCards().indexOf(flipCard);
    
        
            boolean allPositionsMatch = 
                    getAcePosition('C') >= index + 1 &&
                    getAcePosition('D') >= index + 1 &&
                    getAcePosition('H') >= index + 1 &&
                    getAcePosition('S') >= index + 1;
    
            if (allPositionsMatch) {
                flipCard.show();
          
                int col = getCol(flipCard.getSuit());
                int indexAce = getAcePosition(flipCard.getSuit());
                if (flipCard.getTransposed()){
                    swapPositions(indexAce, 0, col);
                }else{
                swapPositions(indexAce, indexAce - 1, col);
                }
                for (GameObserver g : this.observers) {
                    g.update(flipCard.getSuit(), indexAce - 1);
                }
                return flipCard;
            }
        }
        return null;
        
    }
    

    public ArrayList<GameObserver> getObservers() {
        return this.observers;
    }

    public void checkGameOver() {
        for (GameObserver o : this.observers) {
            if (o.getIsGameOver()) {
                this.isGameOver = true;
                this.winner = o.getFaceObserv();
            }
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Character getWinner() {
        return winner;
    }



    public static void main(String[] args) {
        Game game = new Game();
        ArrayList<ArrayList<Boolean>> board = game.getBoard();
        game.drawCard();
        game.drawCard();
        game.drawCard();
        game.drawCard();
        game.drawCard();
        game.drawCard();
        game.drawCard();
        game.drawCard();
        game.drawCard();
        for (ArrayList<Boolean> b : board) {
            System.out.println(b);
        }
        System.out.println("_____________");
        System.out.println(game.drawFlipCard());
        for (ArrayList<Boolean> b : board) {
            System.out.println(b);
        }

    }

}
