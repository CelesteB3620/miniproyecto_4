package com.example.cincuentazo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class CardFactoryTest {

    private List<Card> deck;

    @BeforeEach
    public void setUp() {
        deck = CardFactory.createDeck();
    }

    @Test
    public void testDeckSize() { // Verificar que el mazo tenga 52 cartas
        assertEquals(52, deck.size(), "El mazo debería contener 52 cartas.");
    }

    @Test
    public void testCardValues() { // Para verificar que las cartas tienen el valor que se espera

        for (Card card : deck) {
            if (card.getValue() == 9) {
                assertEquals(0, card.getValue(), "La carta 9 debería tener valor 0.");
            }
            if (card.getValue() > 10) {
                assertEquals(-10, card.getValue(), "Las cartas J, Q, K deberían tener valor -10.");
            }
        }
    }

    @Test
    public void testUniqueCards() { // Comprobar que no haya cartas duplicadas en el mazo
        for (int i = 0; i < deck.size(); i++) {
            for (int j = i + 1; j < deck.size(); j++) {
                assertEquals(false, deck.get(i).getSuit().equals(deck.get(j).getSuit()) && deck.get(i).getId() == deck.get(j).getId(), "El mazo no debe contener cartas duplicadas.");
            }
        }
    }
}