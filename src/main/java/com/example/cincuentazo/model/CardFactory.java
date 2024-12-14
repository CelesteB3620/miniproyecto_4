package com.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating a deck of playing cards.
 * This class provides a static method to generate a complete deck of cards
 * with predefined suits and values.
 */
public class CardFactory {

    /**
     * Creates and returns a standard deck of playing cards.
     *
     * Each card has a suit ("Hearts", "Diamonds", "Spades", "Clubs") and a value
     * ranging from 1 to 13. Specific cards have custom values:
     * - Cards with values greater than 10 are assigned a value of -10.
     * - Cards with a value of 9 are assigned a value of 0.
     *
     * @return A list of {@link Card} objects representing the deck.
     */
    public static List<Card> createDeck() {
        String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"}; // Card suits
        List<Card> deck = new ArrayList<>(); // List to hold the deck

        // Iterate through each suit
        for (String suit : suits) {
            // Create cards for values 1 to 13
            for (int i = 1; i <= 13; i++) {
                Card newCard = new Card(suit, i, i);

                // Assign special values for specific cards
                if (i > 10) {
                    newCard.setValue(-10); // Cards greater than 10
                }
                if (i == 9) {
                    newCard.setValue(0); // Card with value 9
                }

                // Add the card to the deck
                deck.add(newCard);
            }
        }
        return deck;
    }
}