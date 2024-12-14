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

    /**
     * Draws a card from the main deck. If the deck is empty and there are enough cards on the table,
     * it recycles the table cards into the main deck.
     *
     * @return The drawn card, or {@code null} if the main deck is empty after recycling.
     */
    public Card drawCard() {
        if (mainDeck.isEmpty() && tableDeck.size() > 1) {
            recycleTableCards();
        }
        return mainDeck.isEmpty() ? null : mainDeck.removeFirst();
    }

    /**
     * Recycles all table cards except the last card back into the main deck,
     * then shuffles the deck to ensure randomness.
     */
    private void recycleTableCards() {
        Card lastCard = tableDeck.removeLast();
        mainDeck.addAll(tableDeck);
        tableDeck.clear();
        tableDeck.add(lastCard);
        shuffleDeck(mainDeck);
    }

    /**
     * Retrieves the current cards on the table.
     *
     * @return A list of cards on the table.
     */
    public ArrayList<Card> getTableDeck() {
        return tableDeck;
    }

    /**
     * Retrieves the deck of cards belonging to a specific player.
     *
     * @param playerIndex The index of the player whose deck is requested.
     * @return The list of cards in the player's deck.
     */
    public ArrayList<Card> getPlayerDeck(int playerIndex) {
        return playerDecks.get(playerIndex);
    }

    /**
     * Gets the index of the current player.
     *
     * @return The index of the current player in the active players list.
     */
    public int getCurrentPlayer() {
        return currentPlayerIndex;
    }

    /**
     * Gets the current sum of card values on the table.
     *
     * @return The current sum of card values on the table.
     */
    public int getTableCount() {
        return tableCount;
    }

    /**
     * Determines if the game is over, which happens when only one active player remains.
     *
     * @return {@code true} if the game is over, otherwise {@code false}.
     */
    public boolean isGameOver() {
        return activePlayers.size() <= 1;
    }


    /**
     * Removes a specific card from a player's deck and adds it to the table.
     *
     * @param playerIndex The index of the player whose card will be removed.
     * @param cardIndex   The index of the card to remove in the player's deck.
     */
    public void removeCardFromDeck(int playerIndex, int cardIndex) {
        Card card = playerDecks.get(playerIndex).remove(cardIndex);
        tableDeck.add(card);
    }

    /**
     * Removes all cards from a player's deck and adds them to the main deck.
     * Removes the player from the game after their deck is emptied.
     *
     * @param playerIndex The index of the player whose cards will be removed.
     */
    public void removeAllCardsFromDeck(int playerIndex) {
        mainDeck.addAll(playerDecks.get(playerIndex));
        playerDecks.remove(playerIndex);
        for (int i = 0; i < mainDeck.size(); i++) { // Debugging code to display the cards in the main deck.
            System.out.println(mainDeck.get(i).getId() + "-" + mainDeck.get(i).getSuit() + "-" + mainDeck.get(i).getValue());
        }
    }

    /**
     * Draws a card for a player and places it in their deck at a specified position.
     *
     * @param playerIndex The index of the player drawing a card.
     * @param cardIndex   The index at which to insert the new card in the player's deck.
     * @return The newly drawn card, or {@code null} if no card is available to draw.
     */
    public Card drawCardForPlayer(int playerIndex, int cardIndex) {
        Card newCard = drawCard();
        if (newCard != null) {
            getPlayerDeck(playerIndex).add(cardIndex, newCard);
        }
        return newCard;
    }

    /**
     * Removes a player from the game, making them inactive.
     *
     * @param playerIndex The index of the player to be removed.
     */
    public void removePlayer(int playerIndex) {
        activePlayers.remove((Integer) playerIndex);

        if (currentPlayerIndex >= activePlayers.size()) {
            currentPlayerIndex = 0;
        }
    }

    /**
     * Adds a value to the current table sum.
     *
     * @param value The value to add to the table sum.
     */
    public void addToTableSum(int value) {
        tableCount += value;
    }

    /**
     * Advances to the next player's turn in a circular manner.
     */
    public void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();
    }

    /**
     * Retrieves the list of active players in the game.
     *
     * @return A list of indices representing active players.
     */
    public ArrayList<Integer> getActivePlayers() {
        return activePlayers;
    }
}
