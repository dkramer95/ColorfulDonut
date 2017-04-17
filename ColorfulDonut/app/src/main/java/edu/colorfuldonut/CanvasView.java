package edu.colorfuldonut;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DavidKramer on 4/17/17.
 */

public class CanvasView extends View {
    protected CanvasInputHandler m_inputHandler;

    public CanvasView(Context context) {
        super(context);
    }

    public void saveImage() {

    }

    public void clear() {

    }

    protected void onDraw(Canvas canvas) {

    }

    public boolean onTouchEvent(MotionEvent e) {
        // direct touch event to our input handler which will
        // be one of our tools -- i.e. brush, paint bucket, etc...
        throw new UnsupportedOperationException("Implement me please!");
    }

}
