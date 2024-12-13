package com.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final ArrayList<Card> mainDeck;
    private ArrayList<Card> tableDeck;
    private ArrayList<ArrayList<Card>> playerDecks;
    private ArrayList<Integer> activePlayers;
    private int currentPlayerIndex;
    private int tableCount;

    public Game(int numPlayers) {
        mainDeck = (ArrayList<Card>) CardFactory.createDeck();
        tableDeck = new ArrayList<>();
        playerDecks = new ArrayList<>();
        activePlayers = new ArrayList<>();
        shuffleDeck();

        for(int i = 0; i < numPlayers; i++) {

            activePlayers.add(i);
            ArrayList<Card> playerDeck = new ArrayList<>();

            for(int j = 0; j < 4; j++) {
                Card playerCard = drawCard();
                playerDeck.add(playerCard);
            }

            playerDecks.add(playerDeck);
        }

        Card gameCard = drawCard();
        tableDeck.add(gameCard);
        tableCount = gameCard.getValue();

        currentPlayerIndex = 0; // Inicia el jugador
    }

    public void shuffleDeck() {
        Collections.shuffle(mainDeck);
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
        shuffleDeck();
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
