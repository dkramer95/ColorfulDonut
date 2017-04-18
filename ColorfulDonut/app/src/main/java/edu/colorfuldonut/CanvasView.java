package edu.colorfuldonut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by DavidKramer on 4/17/17.
 */

public class CanvasView extends View {
    protected CanvasInputHandler m_inputHandler;
    protected Canvas m_canvas;
    protected Bitmap m_bitmap;
    protected Paint m_paint;
    protected float m_x;
    protected float m_y;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        init();
    }

    protected void init() {
        m_paint = new Paint();
        m_bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        m_canvas = new Canvas(m_bitmap);

        // SAMPLE
        m_paint.setTextSize(35);
    }

    public void saveImage() {

    }

    public void clear() {

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDemo(canvas);
    }

    protected void drawDemo(Canvas canvas) {
        canvas.drawBitmap(m_bitmap, 0, 0, m_paint);
        canvas.drawCircle(m_x, m_y, 100, m_paint);
        m_paint.setColor(getRandColorInt());
        canvas.drawText("I'm just a sample!", m_x, m_y + 150, m_paint);
    }
    public int getRandColorInt() {
        Random rng = new Random();
        int a = rng.nextInt(255);
        int r = rng.nextInt(255);
        int g = rng.nextInt(255);
        int b = rng.nextInt(255);
        int c = Color.argb(255, r, g, b);
        return c;
    }

    public boolean onTouchEvent(MotionEvent e) {
        // direct touch event to our input handler which will
        // be one of our tools -- i.e. brush, paint bucket, etc...


        // sample touch --> TODO move to input handler
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                m_x = e.getX();
                m_y = e.getY();
                invalidate();
                break;
        }
        return false;
    }

}
