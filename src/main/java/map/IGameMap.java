package map;

public interface IGameMap {

    /**
     * Get labyrinth cell contents at x,y
     *
     * @param x
     * @param y
     * @return The tile at x,y
     */
    /**
     * @return The number of rows.
     */
    int getHeight();

    /**
     * @return The number of columns.
     */
    int getWidth();

    /**
     * @return True if the game is active
     */
    boolean isPlaying();
}
