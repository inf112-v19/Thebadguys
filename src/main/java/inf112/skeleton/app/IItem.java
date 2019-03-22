package inf112.skeleton.app;

public interface IItem extends Comparable<IItem> {

    @Override
    default int compareTo(IItem other) {
        return Integer.compare(getSize(), other.getSize());
    }

    int getDamage();

    int getSize();

    String getName ();

    String getSymbol();

    //can either take damage or heal
    int handleDamage(int amount, IItem source, RoboRallyDemo game);
}
