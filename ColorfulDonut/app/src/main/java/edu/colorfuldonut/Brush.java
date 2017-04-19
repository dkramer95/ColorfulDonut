package edu.colorfuldonut;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by DavidKramer on 4/17/17.
 */

public class Brush extends Tool{

    private Paint m_paint;
    private Path m_path;
    private float mX, mY;
    private static final float TOLERANCE = 5;

    public Brush(){
        init();
    }

    public void init(){
        m_path = new Path();
        m_paint = new Paint();
        m_paint.setColor(Color.RED);
        m_paint.setStyle(Paint.Style.STROKE);
        m_paint.setStrokeJoin(Paint.Join.ROUND);
        m_paint.setStrokeWidth(4f);

    }

    public int getSize() {
        return (int) m_paint.getStrokeWidth();
    }

    /**
     * Sets the size of the brush.
     * Max size: 50.
     * Min size: 0.
     * @param size
     */
    public void setSize(int size) {
        if(size >= 0 && 50 >= size){
            m_paint.setStrokeWidth(size);
        }else{
            size = 25;
            m_paint.setStrokeWidth(size);
        }
    }

    public int getColor() {
        return m_paint.getColor();
    }

    public void setColor(int color) {
        m_paint.setColor(color);
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y, Path path) {
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

// when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y, Path path) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    // when ACTION_UP stop touch
    private void upTouch(Path path) {
        path.lineTo(mX, mY);
    }

    @Override
    public boolean handleInput(MotionEvent e, CanvasView canvasView) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y, m_path);
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y, m_path);
                break;
            case MotionEvent.ACTION_UP:
                upTouch(m_path);
                break;
            default:
                return false;
        }
        canvasView.m_paint = m_paint;
        canvasView.m_path = m_path;
        canvasView.invalidate();
        return true;
    }
}
