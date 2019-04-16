package Grid;

public interface IGrid<T>{

    /**
     * @return The height of the grid.
     */
    int getHeight();

    /**
     * @return The width of the grid.
     */
    int getWidth();

    void set(int x, int y, T element);

    void set(double x, double y, T element);

    T get(int x, int y);
    T get(double x , double y);

    /**
     * Make a copy
     *
     * @return A fresh copy of the grid, with the same elements
     */
    IGrid<T> copy();

}