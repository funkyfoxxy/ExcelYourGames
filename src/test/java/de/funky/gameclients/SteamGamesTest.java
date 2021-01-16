package de.funky.gameclients;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteamGamesTest {

    @org.junit.jupiter.api.Test
    void getDivisor() {
        SteamGames games = new SteamGames();
        assertEquals(60, games.getDivisor());
    }

    @Test
    void requestSteamGames() {
        System.out.println("This is just for testing and will be written as a good test in some time");
    }
}