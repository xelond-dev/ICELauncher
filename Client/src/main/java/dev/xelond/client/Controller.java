package dev.xelond.client;

import dev.xelond.client.Config.Config;
import dev.xelond.client.Config.ConfigObject;
import dev.xelond.client.game.RunGame;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button playButton;

    @FXML
    private TextField usernameField;

    @FXML
    void initialize() {
        playButton.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_1234567890";
            boolean correct_username = true;
            for (String symbol : username.split("")) {
                if (!(symbols.contains(symbol))) {
                    correct_username = false;
                    break;
                }
            }
            if (username.length() > 3 && username.length() < 16 && correct_username) {
                System.out.println("Launching game...");
                new Thread("Game"){
                    public void run(){
                        try {
                            RunGame.launch(username);
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }.start();

                javafx.application.Platform.exit();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");

                String error_message = "Неверное имя пользователя. ";
                if (correct_username) {
                    error_message += "Имя пользователя должно быть от 4 до 15 символов!";
                }
                else {
                    error_message += "Имя пользователя должно содержать только цифры латинские буквы и знак \"_\"!";
                }

                alert.setHeaderText(error_message);
                alert.showAndWait();
            }

        });

    }

}
