package com.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the core game logic for a card-based game.
 *
 * The game involves managing a main deck, table deck, player decks, active players,
 * and the current table count. It handles the initialization of the game, card distribution,
 * and deck shuffling.
 */
public class Game {

    private final ArrayList<Card> mainDeck; // Main deck of cards
    private ArrayList<Card> tableDeck; // Cards currently on the table
    private ArrayList<ArrayList<Card>> playerDecks; // Decks for each player
    private ArrayList<Integer> activePlayers; // List of active players (by index)
    private int currentPlayerIndex; // Index of the current player
    private int tableCount; // Current sum of the table cards

    /**
     * Initializes a new game with the specified number of players.
     *
     * Each player is dealt an initial hand of four cards. The main deck is shuffled,
     * and the first card from the deck is placed on the table to initialize the game.
     *
     * @param numPlayers The number of players in the game.
     */
    public Game(int numPlayers) {
        mainDeck = (ArrayList<Card>) CardFactory.createDeck(); // Create a new deck of cards
        tableDeck = new ArrayList<>();
        playerDecks = new ArrayList<>();
        activePlayers = new ArrayList<>();
        shuffleDeck(mainDeck); // Shuffle the main deck


        // Initialize each player with 4 cards
        for (int i = 0; i < numPlayers; i++) {
            activePlayers.add(i);
            ArrayList<Card> playerDeck = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                Card playerCard = drawCard();
                playerDeck.add(playerCard);
            }

            playerDecks.add(playerDeck);
        }

        // Place the first card from the deck on the table
        Card gameCard = drawCard();
        tableDeck.add(gameCard);
        tableCount = gameCard.getValue();

        currentPlayerIndex = 0; // Start with the first player
    }

    /**
     * Shuffles the specified deck of cards.
     *
     * @param deck The deck of cards to shuffle.
     */
    public void shuffleDeck(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }

    public Card drawCard() {
        if (mainDeck.isEmpty() && tableDeck.size() > 1) {
            recycleTableCards();
        }
        return mainDeck.isEmpty() ? null : mainDeck.removeFirst();
    }

    private void recycleTableCards() {
        Card lastCard = tableDeck.removeLast();
        mainDeck.addAll(tableDeck);
        tableDeck.clear();
        tableDeck.add(lastCard);
        shuffleDeck(mainDeck);
    }


    public ArrayList<Card> getTableDeck() {
        return tableDeck;
    }


    public ArrayList<Card> getPlayerDeck(int playerIndex) {
        return playerDecks.get(playerIndex);
    }

    public int getCurrentPlayer() {
        return activePlayers.get(currentPlayerIndex);
    }


    public int getTableCount() {
        return tableCount;
    }

    public boolean isGameOver() {
        return activePlayers.size() <= 1;
    }

    public void removeCardFromDeck(int playerIndex, int cardIndex) {
        Card card = playerDecks.get(playerIndex).remove(cardIndex);
        tableDeck.add(card);
    }

    public void removeAllCardsFromDeck(int playerIndex) {
        mainDeck.addAll(playerDecks.get(playerIndex));
        playerDecks.remove(playerIndex);
        for (int i = 0; i < mainDeck.size(); i++) { // solo es para imprimir lo de la maquina y sus cartas se puede quitar luego
            System.out.println(mainDeck.get(i).getId() + "-" + mainDeck.get(i).getSuit() + "-" + mainDeck.get(i).getValue());
        }
    }

    public Card drawCardForPlayer(int playerIndex, int cardIndex) {
        Card newCard = drawCard();
        if (newCard != null) {
            getPlayerDeck(playerIndex).add(cardIndex, newCard);
        }

        return newCard;
    }

    public void removePlayer(int playerIndex) {
        activePlayers.remove((Integer) playerIndex);
    }

    public void addToTableSum(int value) {
        tableCount += value;
    }

    public void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();
    }

    public ArrayList<Integer> getActivePlayers() {
        return activePlayers;
    }

}
