package com.example.cincuentazo.model;

import javafx.scene.image.Image;

public class Card {

    private static final String PATH = "/com/example/images/cards/";
    private String suit;
    private int value;
    private int id;
    private Image cardImage;

    public Card(String suit, int value, int id) {
        this.suit = suit;
        this.value = value;
        this.id = id;
        this.cardImage = new Image(
                getClass().getResourceAsStream(PATH + this.id + "-" + this.suit + ".png")
        );
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Image getCardImage() {
        return cardImage;
    }
}