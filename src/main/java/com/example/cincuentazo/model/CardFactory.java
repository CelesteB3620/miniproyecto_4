package com.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

public class CardFactory {

    public static List<Card> createDeck() {
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        List<Card> deck = new ArrayList<>();

        for (String suit : suits) {
            for (int i = 1; i <= 13; i++) {

                Card newCard = new Card(suit, i, i);

                if (i > 10){
                    newCard.setValue(-10);
                }
                if (i == 9){
                    newCard.setValue(0);
                }

                deck.add(newCard);
            }
        }
        return deck;
    }
}
