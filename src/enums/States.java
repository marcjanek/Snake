package enums;

/**
 * allowed game states for arena
 */
public enum States {
    /**
     * snake moves on arena
     */
    PLAYING,
    /**
     * the game is waiting for press of the ENTER button, then it will start
     */
    READY,
    /**
     * game is ended. Best scores are listed and game is waiting for start of the new game
     */
    GAME_OVER
}
