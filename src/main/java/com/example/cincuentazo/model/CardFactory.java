package com.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

public class CardFactory {

    public static List<Card> createDeck() {
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
        List<Card> deck = new ArrayList<>();

        for (String suit : suits) {
            for (int i = 1; i <= 13; i++) {
                if ((i >= 2 && i <= 8) || i == 10){
                    Card newCard = new Card(suit, i, i); //Cartas del 2 al 8 valen su número
                    deck.add(newCard);
                }
                if (i >= 11 && i <= 13){
                    Card newCard = new Card(suit, -10, i); //Cartas J, Q y K valen -10
                    deck.add(newCard);
                }
                if (i == 9){
                    Card newCard = new Card(suit, 0, i); //Carta con el número 9 vale 0
                    deck.add(newCard);
                }
                if (i == 1){
                    Card newCard = new Card(suit, 1, i); //Carta A vale 1 (provisionalmente)
                    deck.add(newCard); //Luego el jugador podrá escoger si la carta A cambia su valor a 10 o lo deja en 1
                }
            }
        }
        return deck;
    }
}
