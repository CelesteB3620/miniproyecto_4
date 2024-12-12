package com.example.cincuentazo.controllers;

import com.example.cincuentazo.model.Game;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameController {

    @FXML
    private HBox playerDeck;

    @FXML
    private ImageView machine1;

    @FXML
    private ImageView machine2;

    @FXML
    private ImageView machine3;

    @FXML
    private ImageView tableDeck;

    private Game newGame;

    public void initializeGame(int machinesNumber) {
        hideMachinesDeck(machinesNumber);
        newGame = new Game(machinesNumber);

        for(int i = 0; i < playerDeck.getChildren().size(); i++) {

            ImageView playerCard = (ImageView) playerDeck.getChildren().get(i);

            playerCard.setImage(newGame.getPlayerDeck().get(i).getCardImage());
        }

        tableDeck.setImage(newGame.getTableDeck().getFirst().getCardImage());

    }

    public void hideMachinesDeck(int machinesNumber) {

        switch(machinesNumber) {
            case 1:
                machine2.setVisible(false);
                machine3.setVisible(false);
                break;
            case 2:
                machine3.setVisible(false);
                break;
            default:
                break;
        }
    }

}
