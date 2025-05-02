package project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class SceneController {

    @FXML
    HBox flipCards;

    @FXML
    Button starButton;

    @FXML
    GridPane board;

    @FXML
    ImageView deck;

    @FXML
    VBox cardDrawImage;

    @FXML
    ImageView logo;

    private Image cAce = new Image("images/cards/ace_of_clubs.png");

    private Image dAce = new Image("images/cards/ace_of_diamonds.png");

    private Image hAce = new Image("images/cards/ace_of_hearts.png");

    private Image sAce = new Image("images/cards/ace_of_spades.png");

    private final Rectangle cAceRec = new Rectangle(80, 40, new ImagePattern(cAce));

    private final Rectangle dAceRec = new Rectangle(80, 40, new ImagePattern(dAce));

    private final Rectangle hAceRec = new Rectangle(80, 40, new ImagePattern(hAce));

    private final Rectangle sAceRec = new Rectangle(80, 40, new ImagePattern(sAce));

    Game game = new Game();

    public void StartButtonEvent(ActionEvent event) {
        initializeBoard();
        starButton.setText("Trekk");
        starButton.setOnAction(handleButtonClick -> {
            Card card = game.drawCard();
            try {
                updateHorses(card.getSuit());
            } catch (IOException e) {

                e.printStackTrace();
            }
            updateDrawCard(card);
            try {
                updateFlipCard();
                updateHorses(card.getSuit());
            } catch (IOException e) {

                e.printStackTrace();
            }

        });
    }

    public void initializeBoard() {
        board.setHgap(15);
        board.setVgap(10);

        for (int col = 0; col <= 8; col++) {
            for (int row = 0; row <= 3; row++) {
                Image cardImage = new Image("images/cards/back_card.png");
                Rectangle rec = new Rectangle(80, 40);
                rec.setFill(new ImagePattern(cardImage));
                board.add(rec, col, row);
            }
        }

        board.add(cAceRec, 0, 0);
        board.add(dAceRec, 0, 1);
        board.add(hAceRec, 0, 2);
        board.add(sAceRec, 0, 3);

        for (int i = 0; i <= 6; i++) {
            Image cardImage = new Image("images/cards/back_card.png");
            Rectangle rec = new Rectangle(40, 80);
            Rectangle rec2 = new Rectangle(60, 80);
            rec.setFill(new ImagePattern(cardImage));
            rec2.setFill(Color.TRANSPARENT);
            flipCards.getChildren().add(rec);
            flipCards.getChildren().add(rec2);
        }
        Rotate rotate = new Rotate(90);
        rotate.setPivotX(25);
        rotate.setPivotY(30);
        flipCards.getChildren().get(4).getTransforms().add(rotate);
        flipCards.getChildren().get(10).getTransforms().add(rotate);

    }

    private Rectangle getRectangleBySuit(Character suit) {
        switch (Character.toUpperCase(suit)) {
            case 'C':
                return cAceRec;
            case 'D':
                return dAceRec;
            case 'H':
                return hAceRec;
            case 'S':
                return sAceRec;
            default:
                return null;
        }
    }

    public void updateHorses(Character c) throws IOException {
        int col = game.getCol(c);
        int indexAce = game.getAcePosition(c);

        Rectangle rectToMove = getRectangleBySuit(c);
        if (rectToMove == null) {
            System.err.println("Invalid suit: " + c);
            return;
        }

        board.getChildren().remove(rectToMove);
        board.add(rectToMove, indexAce, col);
        if (game.isGameOver()) {
            fireGameOver();
        }
    }

    public void updateDrawCard(Card card) {
        ImageView cardDraw = new ImageView(card.getImagePath());
        cardDraw.setFitWidth(120);
        cardDraw.setFitHeight(200);
        cardDrawImage.getChildren().setAll(cardDraw);
    }

    public void updateFlipCard() throws IOException {
        Card flipCard = game.drawFlipCard();
        if (flipCard != null) {
            updateHorses(flipCard.getSuit());
            Image cardImage = new Image(flipCard.getImagePath());
            Rectangle rec = new Rectangle(40, 80);
            rec.setFill(new ImagePattern(cardImage));
            int indexFlipCard = game.getFlipCards().indexOf(flipCard) * 2;
            flipCards.getChildren().remove(indexFlipCard);
            flipCards.getChildren().add(indexFlipCard, rec);
            if(flipCard.getTransposed()){
                Rotate rotate = new Rotate(90);
                rotate.setPivotX(25);
                rotate.setPivotY(30);
                flipCards.getChildren().get(indexFlipCard).getTransforms().add(rotate);
            }
        }

    }

    public void fireGameOver() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameOverScene.fxml"));
        Parent root = loader.load();

        gameOverSceneController gameOverSceneController = loader.getController();

        String winner;
        switch (Character.toUpperCase(game.getWinner())) {
            case 'C':
                winner = "Club";
                break;
            case 'D':
                winner = "Diamond";
                break;
            case 'H':
                winner = "Heart";
                break;
            case 'S':
                winner = "Spades";
                break;
            default:
                winner = "No Winner";
        }

        gameOverSceneController.updateWinnerText(winner);
        Stage stage = (Stage) starButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
