package com.example.cincuentazo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(3);
    }

    // Pruebas para la inicialización del juego
    @Test
    public void testGameInitialization_1() {
        for (int i = 0; i < 3; i++) {
            assertEquals(4, game.getPlayerDeck(i).size(), "Cada jugador debe empezar con 4 cartas.");
        }

        assertEquals(1, game.getTableDeck().size(), "Debe haber exactamente una carta inicial en la mesa.");
        assertEquals(game.getTableDeck().get(0).getValue(), game.getTableCount(), "La suma inicial debe coincidir con el valor de la carta en la mesa.");
    }

    @Test
    public void testGameInitialization_2() {
        // Inicialización con un número diferente de jugadores
        Game gameWithTwoPlayers = new Game(2);

        assertEquals(2, gameWithTwoPlayers.getActivePlayers().size(), "Debe haber 2 jugadores activos al iniciar.");
        assertEquals(4, gameWithTwoPlayers.getPlayerDeck(0).size(), "El primer jugador debe empezar con 4 cartas.");
    }

    // Pruebas para la lógica de turnos
    @Test
    public void testTurnLogic_1() {
        int initialPlayer = game.getCurrentPlayer();

        // Avanzar al siguiente jugador
        game.moveToNextPlayer();
        int nextPlayer = game.getCurrentPlayer();
        assertNotEquals(initialPlayer, nextPlayer, "El turno debe pasar al siguiente jugador.");
    }

    @Test
    public void testTurnLogic_2() {
        int initialPlayer = game.getCurrentPlayer();

        // Eliminar un jugador y verificar que el turno cambia correctamente
        game.removePlayer(initialPlayer);
        game.moveToNextPlayer();
        assertNotEquals(initialPlayer, game.getCurrentPlayer(), "El turno debe ajustarse cuando un jugador es eliminado.");
    }

    // Pruebas para reciclaje de cartas
    @Test
    public void testDeckRecycling_1() {
        // Vaciar el mazo principal
        while (game.drawCard() != null) {}

        // Añadir más de una carta a la mesa para reciclar
        game.addToTableSum(5);
        game.removeCardFromDeck(0, 0); // Simula jugar una carta

        assertFalse(game.getTableDeck().isEmpty(), "Debe haber al menos una carta en la mesa antes del reciclaje.");
    }

    @Test
    public void testDeckRecycling_2() {
        // Vaciar el mazo principal y verificar el reciclaje
        while (game.drawCard() != null) {}
        game.drawCard(); // Forzar reciclaje

        assertFalse(game.getTableDeck().isEmpty(), "El reciclaje debería preservar la última carta de la mesa.");
    }

    @Test
    public void testDeckRecycling_3() {
        // Mazo vacío sin cartas en la mesa
        while (game.drawCard() != null) {}
        game.getTableDeck().clear(); // Forzar mesa vacía
        assertNull(game.drawCard(), "Si el mazo y la mesa están vacíos, no se deben generar cartas.");
    }

    // Pruebas para la condición de finalización del juego
    @Test
    public void testGameOverCondition_1() {
        // Eliminar jugadores hasta que quede uno
        game.removePlayer(0);
        game.removePlayer(1);

        assertTrue(game.isGameOver(), "El juego debería terminar cuando queda un solo jugador activo.");
    }

    @Test
    public void testGameOverCondition_2() {
        // El juego no debe terminar si quedan más de un jugador
        Game gameWithFivePlayers = new Game(5);
        assertFalse(gameWithFivePlayers.isGameOver(), "El juego no debería terminar si quedan más de un jugador activo.");

        gameWithFivePlayers.removePlayer(0);
        gameWithFivePlayers.removePlayer(1);
        gameWithFivePlayers.removePlayer(2);
        gameWithFivePlayers.removePlayer(3);
        assertTrue(gameWithFivePlayers.isGameOver(), "El juego debería terminar cuando queda un solo jugador.");
    }
}