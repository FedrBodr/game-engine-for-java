package com.gej.core;

/**
 * Describes the state of the game.
 * 
 * @author Sri Harsha Chilakapati
 */
public enum GameState {

    /**
     * State indicating that the game is loading resources
     */
    GAME_LOADING,

    /**
     * State indicating that the game is in intro screen
     */
    GAME_INTRO,

    /**
     * State indicating that the game is playing
     */
    GAME_PLAYING,

    /**
     * State indicating that the game is paused
     */
    GAME_PAUSED,

    /**
     * State indicating that the game is at end screen
     */
    GAME_END

}
