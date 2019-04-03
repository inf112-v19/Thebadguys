package Grid;
import java.util.*;

public class MyGrid<T> implements IGrid<T> {
    private List<T> tiles;
    private int height;
    private int width;

    /**
     *
     * Construct a grid with the given dimensions.
     *
     * @param width
     * @param height
     * @param initElement
     *            What the cells should initially hold (possibly null)
     */
    public MyGrid(int width, int height, T initElement) {
        if(width <= 0 || height <= 0)
            throw new IllegalArgumentException();
        this.height = height;
        this.width = width;
        tiles = new ArrayList<T>(4*height * width);
        for (int i = 0; i < 4*height * width; ++i) {

            tiles.add(initElement);
        }
    }


    @Override
    public int getHeight() {
        return height;
    }


    @Override
    public int getWidth() {
        return width;
    }


    @Override
    public void set(int x, int y, T elem) {
        x = 2*x +1;
        y = 2*y +1;
        if(x < 0 || x >= 2*width)
            throw new IndexOutOfBoundsException();
        if(y < 0 || y >= 2*height)
            throw new IndexOutOfBoundsException();
        int index = x + width*(y);
        tiles.set(index, elem);
    }

    public void set(double x, double y, T elem) {
        x = 2*x +1;
        y = 2*y +1;
        if(x < 0 || x >= 2*width)
            throw new IndexOutOfBoundsException();
        if(y < 0 || y >= 2*height)
            throw new IndexOutOfBoundsException();
        int index =  (int)(x) + (int)(width*(y));
        tiles.set(index, elem);
    }


    @Override
    public T get(int x, int y) {
        x = 2*x +1;
        y = 2*y +1;
        if(x < 0 || x >= 2*width)
            throw new IndexOutOfBoundsException();
        if(y < 0 || y >= 2*height)
            throw new IndexOutOfBoundsException();
        int index = x + width*(y);
        return tiles.get(index);
    }

    public T get(double x, double y) {
        x = 2*x +1;
        y = 2*y +1;
        if(x < 0 || x >= 2*width)
            throw new IndexOutOfBoundsException();
        if(y < 0 || y >= 2*height)
            throw new IndexOutOfBoundsException();
        int index = (int)(x) + (int)(width*(y));
        return tiles.get(index);
    }

    @Override
    public IGrid<T> copy() {
        MyGrid<T> newGrid = new MyGrid<T>(getWidth(), getHeight(), null);

        for (int x = 0; x < width; x++)
            for(int y = 0; y < height; y++)
                newGrid.set(x,  y,  get(x, y));
        return newGrid;
    }

}