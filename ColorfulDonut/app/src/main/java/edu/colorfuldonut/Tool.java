package edu.colorfuldonut;

import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by DavidKramer on 4/17/17.
 */

public abstract class Tool extends Paint {
    protected String m_name;

    public Tool(String name) {
        m_name = name;
    }

    public abstract void handleInput(MotionEvent e, CanvasView canvasView);

    public String getName() {
        return m_name;
    }
}
