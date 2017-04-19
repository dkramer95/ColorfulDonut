package edu.colorfuldonut;

import android.view.MotionEvent;

/**
 * Created by DavidKramer on 4/17/17.
 */

public abstract class Tool {
    protected String m_name;

    public Tool() {

    }

    public abstract boolean handleInput(MotionEvent e, CanvasView canvasView);

    public String getName() {
        return m_name;
    }
}
