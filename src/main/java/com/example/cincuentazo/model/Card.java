package com.example.cincuentazo.model;

import javafx.scene.image.Image;

/**
 * Represents a playing card in a card game.
 * Each card has a suit, a value, a unique identifier (id), and an associated image.
 */
public class Card {

    private static final String PATH = "/com/example/images/cards/"; // Base path for card images
    private String suit; // The suit of the card (e.g., Hearts, Diamonds, etc.)
    private int value; // The value of the card (e.g., 1 for Ace, 11 for Jack, etc.)
    private int id; // Unique identifier for the card
    private Image cardImage; // The image associated with the card

    /**
     * Creates a new card with a specified suit, value, and identifier.
     *
     * @param suit The suit of the card (e.g., "Hearts", "Spades").
     * @param value The value of the card (1-13 depending on the game).
     * @param id The unique identifier for the card.
     */
    public Card(String suit, int value, int id) {
        this.suit = suit;
        this.value = value;
        this.id = id;
        this.cardImage = new Image(
                getClass().getResourceAsStream(PATH + this.id + "-" + this.suit + ".png")
        );
    }

    /**
     * Gets the suit of the card.
     *
     * @return The suit of the card.
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Sets the value of the card.
     *
     * @param value The new value for the card.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the image associated with the card.
     *
     * @return The image of the card.
     */
    public Image getCardImage() {
        return cardImage;
    }

    /**
     * Gets the value of the card.
     *
     * @return The value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the unique identifier of the card.
     *
     * @return The ID of the card.
     */
    public int getId() {
        return id;
    }
}
