package edu.colorfuldonut;

/**
 * Created by DavidKramer on 4/21/17.
 */

public class GlobalColor {
    private int m_colorValue;

    private static GlobalColor instance;

    private GlobalColor() {}

    private static GlobalColor getInstance() {
        if (instance == null) {
            instance = new GlobalColor();
        }
        return instance;
    }

    public static int get() {
        return getInstance().m_colorValue;
    }

    public static void set(int color) {
        getInstance().m_colorValue = color;
    }
}
