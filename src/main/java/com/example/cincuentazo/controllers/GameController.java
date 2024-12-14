package com.example.cincuentazo.controllers;

import com.example.cincuentazo.model.Card;
import com.example.cincuentazo.model.Game;
import com.example.cincuentazo.view.alert.AlertBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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


/**
 * Controller for the game view in the "CINCUENTAZO" game.
 * Manages the gameplay logic, user interactions, and updates the game interface.
 * @author Laura Celeste Berrio - Leonardo Rios Saavedra
 */
public class GameController {

    /** Label displaying the current total count on the table. */
    @FXML
    private Label countLabel;

    /** Label displaying whose turn it is in the game. */
    @FXML
    private Label turnosLabel;

    /** Horizontal box containing the player's deck of cards. */
    @FXML
    private HBox playerDeck;

    /** Image view representing the first machine's deck. */
    @FXML
    private ImageView machine1;

    /** Image view representing the second machine's deck. */
    @FXML
    private ImageView machine2;

    /** Image view representing the third machine's deck. */
    @FXML
    private ImageView machine3;

    /** Image view representing the card currently on the table. */
    @FXML
    private ImageView tableDeck;

    /** Image view representing the main deck of cards. */
    @FXML
    private ImageView mainDeck;

    /** Instance of the `Game` class that manages game state and logic. */
    private Game newGame;

    /** Executor service for managing background tasks related to machine turns. */
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Initializes a new game with the specified number of machine players.
     * Updates the game interface and sets up event handlers for user interactions.
     *
     * @param machinesNumber The number of machine players (1-3).
     */
    public void initializeGame(int machinesNumber) {
        hideMachinesDeck(machinesNumber); // Hide unused machine decks based on the number of machines
        int numPlayers = 1 + machinesNumber; // Total players = user + machines
        newGame = new Game(numPlayers);

        // Initialize the table with the first card
        Card firstTableCard = newGame.getTableDeck().getFirst();
        tableDeck.setImage(firstTableCard.getCardImage());
        countLabel.setText("TOTAL MESA: " + newGame.getTableCount());
        turnosLabel.setText("TURNO JUGADOR");
        mainDeck.setDisable(true); // Disable the main deck initially

        // Setup player's cards in the interface
        for (int i = 0; i < playerDeck.getChildren().size(); i++) {
            ImageView playerCard = (ImageView) playerDeck.getChildren().get(i);
            Card card = newGame.getPlayerDeck(0).get(i);
            playerCard.setImage(card.getCardImage());

            // Set up click handler for each player's card
            playerCard.setOnMouseClicked(event -> {
                if (newGame.getCurrentPlayer() == 0) { // Check if it's the player's turn
                    removeCurrentPlayerIfNoValidCards(); // Validate cards < 50

                    countLabel.setText("TOTAL MESA: " + newGame.getTableCount());
                    int cardIndex = playerDeck.getChildren().indexOf(playerCard);
                    Card playedCard = newGame.getPlayerDeck(0).get(cardIndex);
                    optionCardAs(playedCard);

                    // Validate if the card can be played (table sum <= 50)
                    if (newGame.getTableCount() + playedCard.getValue() <= 50) {
                        newGame.removeCardFromDeck(0, cardIndex);
                        newGame.addToTableSum(playedCard.getValue());
                        countLabel.setText("TOTAL MESA: " + newGame.getTableCount());
                        playerCard.setImage(null);
                        tableDeck.setImage(playedCard.getCardImage());

                        // Disable player's deck and enable main deck
                        playerDeck.getChildren().forEach(node -> node.setDisable(true));
                        mainDeck.setDisable(false);

                        // Handle drawing a card from the main deck
                        mainDeck.setOnMouseClicked(event1 -> {
                            if (newGame.getCurrentPlayer() == 0) {
                                Card newCard = newGame.drawCardForPlayer(0, cardIndex);
                                playerCard.setImage(newCard.getCardImage());
                                mainDeck.setDisable(true);

                                // Move to the next player and handle machine turns
                                newGame.moveToNextPlayer();
                                try {
                                    playMachineTurn();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    } else {
                        // Game over condition if sum exceeds 50
                        new AlertBox().showAlert("INFORMATION", "¡El juego ha terminado!", "PERDISTE :(", Alert.AlertType.INFORMATION);
                        playerDeck.getChildren().forEach(node -> node.setDisable(true));
                        mainDeck.setDisable(true);
                    }
                }
            });
        }
    }

    /**
     * Handles the behavior when the "As" card is played.
     *
     * @param playedCard the card being played
     */
    public void optionCardAs(Card playedCard) {
        String[] options = {"1", "10"};
        if (playedCard.getId() == 1) {
            String selectedValue = new AlertBox().showOptions(
                    "OPCIÓN CARTA As",
                    "Elige qué valor deseas tomar para la carta As",
                    List.of(options)
            );

            if (selectedValue == "10") {
                playedCard.setValue(10);
            }
        }
    }

    /**
     * Hides the decks of machines based on the number of machines in the game.
     *
     * @param machinesNumber the number of machines currently in the game
     */
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

    /**
     * Handles the logic for a machine's turn during the game.
     * This includes card selection, validation, and updating the game state.
     *
     * @throws InterruptedException if the thread is interrupted while simulating delays
     */
    public void playMachineTurn() throws InterruptedException {
        executor.execute(() -> {
            removeCurrentPlayerIfNoValidCards();

            int currentPlayer = newGame.getCurrentPlayer(); // Get the current machine player

            if (currentPlayer != 0) {
                try {
                    // Simulate a delay between 2 and 4 seconds before playing a card
                    Thread.sleep(2000 + new Random().nextInt(2000));

                    ArrayList<Card> machineDeck = newGame.getPlayerDeck(currentPlayer); // Get the machine's deck
                    Platform.runLater(() -> turnosLabel.setText("TURNO MÁQUINA " + currentPlayer));
                    for (int i = 0; i < machineDeck.size(); i++) { // Log machine's cards (optional for debugging)
                        System.out.println("Machine " + currentPlayer + "-" + machineDeck.get(i).getId() + "-" + machineDeck.get(i).getSuit() + "-" + machineDeck.get(i).getValue());
                    }

                    newGame.shuffleDeck(machineDeck);

                    for (Card card : machineDeck) {
                        if (card.getId() == 1) {
                            Random random = new Random();
                            int randomNum = random.nextInt(2); // Generate 0 or 1
                            int result = (randomNum == 0) ? 1 : 10; // Assign 1 or 10 based on the random result
                            card.setValue(result);
                            newGame.getPlayerDeck(currentPlayer).get(machineDeck.indexOf(card)).setValue(result);
                        }

                        removeCurrentPlayerIfNoValidCards();

                        if (newGame.getTableCount() + card.getValue() <= 50) { // Validate card play
                            int cardIndex = machineDeck.indexOf(card);
                            newGame.removeCardFromDeck(currentPlayer, cardIndex);
                            tableDeck.setImage(card.getCardImage());
                            newGame.addToTableSum(card.getValue());
                            System.out.println("Suma despues de maquina: " + newGame.getTableCount());
                            Platform.runLater(() -> countLabel.setText("TOTAL MESA: " + newGame.getTableCount()));

                            // Simulate a delay between 2 and 4 seconds before drawing a new card
                            Thread.sleep(2000 + new Random().nextInt(2000));
                            newGame.drawCardForPlayer(currentPlayer, cardIndex); // Machine draws a new card

                            newGame.moveToNextPlayer(); // Move to the next player

                            if (newGame.getCurrentPlayer() == 0) {
                                Platform.runLater(() -> turnosLabel.setText("TURNO JUGADOR"));
                                playerDeck.getChildren().forEach(node -> node.setDisable(false)); // Enable human player's cards
                            } else {
                                playMachineTurn(); // Call for the next machine
                            }

                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                playerDeck.getChildren().forEach(node -> node.setDisable(false));
            }
        });
    }

    /**
     * Automatically validates whether the current player has valid cards to play.
     * If no valid cards are available, the player either loses or wins based on the game state.
     */
    public void removeCurrentPlayerIfNoValidCards() {
        int playerIndex = newGame.getCurrentPlayer();
        ArrayList<Card> deck = newGame.getPlayerDeck(playerIndex);
        boolean hasValidCard = deck.stream().anyMatch(card -> newGame.getTableCount() + card.getValue() <= 50); // Validate if the card meets the sum condition

        if (!hasValidCard) {
            if (playerIndex != 0) {
                newGame.moveToNextPlayer();
                newGame.removePlayer(playerIndex);
                newGame.removeAllCardsFromDeck(playerIndex);

                Platform.runLater(() -> {
                    new AlertBox().showAlert("INFORMATION", "¡Jugador eliminado!", " Máquina " + playerIndex, Alert.AlertType.INFORMATION);
                });

            } else {
                Platform.runLater(() -> {
                    new AlertBox().showAlert("INFORMATION", "¡El juego ha terminado!", "PERDISTE :(", Alert.AlertType.INFORMATION);
                });
                playerDeck.getChildren().forEach(node -> node.setDisable(true));
                mainDeck.setDisable(true);
            }
            if (newGame.isGameOver()) {
                int winnerIndex = newGame.getActivePlayers().getFirst();
                Platform.runLater(() -> {
                    new AlertBox().showAlert("INFORMATION", "¡El juego ha terminado!", "Ganador:" + (winnerIndex == 0 ? " Jugador" : " Máquina " + winnerIndex) + "!", Alert.AlertType.INFORMATION);
                });
                playerDeck.getChildren().forEach(node -> node.setDisable(true));
                mainDeck.setDisable(true);
            }

        }
    }

    public void onHandleInstructionsButton(ActionEvent actionEvent) {
        new AlertBox().showAlert("INFORMATION", "INSTRUCCIONES DEL JUEGO", "El juego consiste en que cada jugador recibe 4 cartas del mazo principal y posteriormente se saca de ese mismo mazo 1 carta que se pone en la mesa. Cada jugador en su turno debe escoger una carta, ponerla en la mesa y sacar una carta del mazo que reemplace la que acaba de poner ya que siempre debe tener 4 cartas, las cartas en la mesa se irán sumando y el jugador que en su turno ponga una carta que al sumarse con las cartas de la mesa sobrepase la cuenta de 50 perderá y saldrá del juego. El juego continúa hasta que solo quede un jugador. Las cartas tienen los siguientes valores:  las cartas del 2 al 8 y el 10 suman su número, las cartas con el número 9 ni suman ni restan o sea valen 0, las cartas con las letras J, Q y K restan 10, las cartas con la letra A suman 1 o 10 según lo que escoja el jugador. Usted puede jugar hasta contra 3 jugadores.", Alert.AlertType.INFORMATION);
    }


}

