package inf112.skeleton.app;

public interface IItem extends Comparable<IItem> {


    int getDamage();

    int getSize();

    String getName ();

    String getSymbol();

    //can either take damage or heal
    int handleDamage(int amount, IItem source, RoboRallyDemo game);
}
