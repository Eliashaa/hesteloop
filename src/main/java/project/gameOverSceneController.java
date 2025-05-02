package project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class gameOverSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Text gameOverText;

    @FXML
    Text winner;

    @FXML
    Button nyttSpillButton;

    public void updateWinnerText(String winner) {
        this.winner.setText(winner);
    }

    public void startNewGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
