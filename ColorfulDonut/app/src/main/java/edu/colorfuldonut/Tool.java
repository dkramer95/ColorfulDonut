package edu.colorfuldonut;

/**
 * Created by DavidKramer on 4/17/17.
 */

public abstract class Tool {
    protected String m_name;

    public Tool(String name) {
        m_name = name;
    }

    public abstract void handleInput();

    public String getName() {
        return m_name;
    }
}
