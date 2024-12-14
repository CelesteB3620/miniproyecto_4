package com.example.cincuentazo.model;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    @Test
    public void testCardConstructor_1() { //Para comprobar que la carta es del palo Diamonds, con valor 5 e id 5
        Card card = new Card("Diamonds", 5, 5);

        assertEquals(5, card.getValue(), "El valor de la carta debería ser 5.");

        assertEquals("Diamonds", card.getSuit(), "El palo de la carta debería ser Diamonds.");

        assertEquals(5, card.getId(), "El id de la carta debería ser 5.");

        assertEquals(true, card.getCardImage() != null, "La imagen de la carta no debería ser nula.");
    }

    @Test
    public void testCardConstructor_2() { //Para comprobar que la carta es del palo Hearts, con valor -10 e id 11
        Card card = new Card("Hearts", -10, 11);

        assertEquals(-10, card.getValue(), "El valor de la carta debería ser 5.");

        assertEquals("Hearts", card.getSuit(), "El palo de la carta debería ser Hearts.");

        assertEquals(11, card.getId(), "El id de la carta debería ser 11.");

        assertEquals(true, card.getCardImage() != null, "La imagen de la carta no debería ser nula.");
    }
}