package com.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final ArrayList<Card> mainDeck;
    private ArrayList<Card> tableDeck;
    private ArrayList<Card> playerDeck;
    private ArrayList<ArrayList<Card>> machinesDeck;

    public Game(int machinesNumber) {
        mainDeck = (ArrayList<Card>) CardFactory.createDeck();
        playerDeck = new ArrayList<>();
        tableDeck = new ArrayList<>();
        machinesDeck = new ArrayList<>();
        shuffleDeck();

        for(int i = 0; i < 4; i++) {
            Card playerCard = drawCard();
            playerDeck.add(playerCard);
        }

        for(int i = 0; i < machinesNumber; i++) {

            ArrayList<Card> machineDeck = new ArrayList<>();

            for(int j = 0; j < 4; j++) {
                Card machineCard = drawCard();
                machineDeck.add(machineCard);
            }

            machinesDeck.add(machineDeck);
        }

        Card gameCard = drawCard();
        tableDeck.add(gameCard);
    }

    public void shuffleDeck() {
        Collections.shuffle(mainDeck);
    }

    public Card drawCard() {
        return mainDeck.isEmpty() ? null : mainDeck.removeFirst();
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getTableDeck() {
        return tableDeck;
    }

    public ArrayList<Card> getPlayerDeck() {
        return playerDeck;
    }

    public ArrayList<ArrayList<Card>> machinesDeck() {
        return machinesDeck;
    }

}
