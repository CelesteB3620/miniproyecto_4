package com.example.cincuentazo.controllers;

import com.example.cincuentazo.model.Card;
import com.example.cincuentazo.model.Game;
import com.example.cincuentazo.view.alert.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;

public class GameController {

    @FXML Label countLabel;

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

    @FXML
    private ImageView mainDeck;

    private Game newGame;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void initializeGame(int machinesNumber) {
        hideMachinesDeck(machinesNumber);
        int numPlayers = 1 + machinesNumber;
        newGame = new Game(numPlayers);

        Card firstTableCard = newGame.getTableDeck().getFirst();
        tableDeck.setImage(firstTableCard.getCardImage());
        countLabel.setText("Cuenta en la mesa: " + newGame.getTableCount());

        mainDeck.setDisable(true);

        for (int i = 0; i < playerDeck.getChildren().size(); i++) {

            ImageView playerCard = (ImageView) playerDeck.getChildren().get(i); //Cada image view del hbox

            Card card = newGame.getPlayerDeck(0).get(i); //Objeto carta del arreglo del jugador
            playerCard.setImage(card.getCardImage()); // Establece la carta(imagen) del arreglo del jugador al image view del hbox

            playerCard.setOnMouseClicked(event -> {

                if (newGame.getCurrentPlayer() == 0) { // turno jugador

                    removeCurrentPlayerIfNoValidCards(); // validacion automatica de que hay cartas validas para jugar <50

                    System.out.println("Suma en la mesa: " + newGame.getTableCount());
                    countLabel.setText("Cuenta en la mesa: " + newGame.getTableCount());
                    int cardIndex = playerDeck.getChildren().indexOf(playerCard); // identifica el index del image view del hbox donde hace el clic
                    Card playedCard = newGame.getPlayerDeck(0).get(cardIndex); // Trae la carta del arreglo del jugador del mismo indice
                    optionCardAs(playedCard);

                    if (newGame.getTableCount() + playedCard.getValue() <= 50) { //validacion de suma con el valor de la carta
                        newGame.removeCardFromDeck(0, cardIndex); //remueve la carta del arreglo y la pone en la mesa
                        newGame.addToTableSum(playedCard.getValue());
                        System.out.println("Suma en la mesa: " + newGame.getTableCount());
                        countLabel.setText("Cuenta en la mesa: " + newGame.getTableCount());
                        playerCard.setImage(null); //Quita la imagen de la carta jugada en la interfaz
                        tableDeck.setImage(playedCard.getCardImage()); // la pone en la mesa

                        playerDeck.getChildren().forEach(node -> node.setDisable(true)); // deshabilita el clic para cada image view del hbox
                        mainDeck.setDisable(false); // habilita el clic del mazo para que tome una carta

                        mainDeck.setOnMouseClicked(event1 -> {
                            if (newGame.getCurrentPlayer() == 0) {
                                Card newCard = newGame.drawCardForPlayer(0, cardIndex); //trae la nueva carta del mazo al arreglo del jugador
                                playerCard.setImage(newCard.getCardImage()); //coloca la imagen de esa carta
                                mainDeck.setDisable(true); //Desshablita el mazo

                                newGame.moveToNextPlayer(); //siguiente jugador
                                try {
                                    playMachineTurn(); //intenta que sea el turno de la maquina de lo contrario saca una excepcion
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });
                    }
                }
            });
        }
    }

    public void optionCardAs (Card playedCard){

        String[] options = {"1", "10"};
        if (playedCard.getId() == 1){
            String selectedValue = new AlertBox().showOptions("OPCIÓN CARTA As","Elige qué valor deseas tomar para la carta As", List.of(options));

            if (selectedValue == "10") {
                playedCard.setValue(10);
            }
    }}

    public void hideMachinesDeck(int machinesNumber) {

        switch (machinesNumber) {
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

    public void playMachineTurn() throws InterruptedException {
        executor.execute(() -> {
            removeCurrentPlayerIfNoValidCards();

            int currentPlayer = newGame.getCurrentPlayer(); //trae la maquina a jugar

            if (currentPlayer != 0) {
                try {
                    // Simular tiempo entre 2 y 4 segundos para jugar una carta

                    Thread.sleep(2000 + new Random().nextInt(2000));

                    ArrayList<Card> machineDeck = newGame.getPlayerDeck(currentPlayer); //trae el arreglo de la maquina en juego

                    for (int i = 0; i < machineDeck.size(); i++) { // solo es para imprimir lo de la maquina y sus cartas se puede quitar luego
                        System.out.println("Machine " + currentPlayer + "-" + machineDeck.get(i).getId() + "-" + machineDeck.get(i).getSuit() + "-" + machineDeck.get(i).getValue());
                    }

                    for (Card card : machineDeck) {
                        if (newGame.getTableCount() + card.getValue() <= 50) { //validaciones iguales que las del jugador
                            int cardIndex = machineDeck.indexOf(card);
                            newGame.removeCardFromDeck(currentPlayer, cardIndex);
                            tableDeck.setImage(card.getCardImage());
                            newGame.addToTableSum(card.getValue());
                            System.out.println("Suma despues de maquina: " + newGame.getTableCount());
                            Platform.runLater(() -> countLabel.setText("Cuenta en la mesa: " + newGame.getTableCount()));

                            // Simular tiempo entre 2 y 4 segundos para tomar una nueva carta
                            Thread.sleep(2000 + new Random().nextInt(2000));
                            newGame.drawCardForPlayer(currentPlayer, cardIndex); // la maquina toma una carta del mazo

                            newGame.moveToNextPlayer(); //mueve al siguiente jugador en caso que sea otra maquina o ya el humano

                            if (newGame.getCurrentPlayer() == 0) {
                                playerDeck.getChildren().forEach(node -> node.setDisable(false)); //si los turno maquina ya acabaron habilito de nuevo las cartas del jugador humano
                            } else {
                                playMachineTurn(); // siguiente maquina en caso de no terminar aqui
                            }

                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void removeCurrentPlayerIfNoValidCards() { //funcion automatica que valida si las cartas cumplen las reglas  para jugar, sino deberia perder o ganar el jugador en turno
        int playerIndex = newGame.getCurrentPlayer();
        ArrayList<Card> deck = newGame.getPlayerDeck(playerIndex);
        boolean hasValidCard = deck.stream().anyMatch(card -> newGame.getTableCount() + card.getValue() <= 50); //valida si la carta cumple con la suma

        if (!hasValidCard) {
            newGame.removePlayer(playerIndex);
            System.out.println("Jugador eliminado con indice " + playerIndex);
            newGame.moveToNextPlayer();

            if (newGame.isGameOver()) {
                int winnerIndex = newGame.getActivePlayers().getFirst();
                System.out.println("Juego terminado. Ganador: " + (winnerIndex == 0 ? "Jugador" : "Máquina " + winnerIndex) + "!");
                playerDeck.getChildren().forEach(node -> node.setDisable(true));
                mainDeck.setDisable(true);
            }
        }

    }


}

