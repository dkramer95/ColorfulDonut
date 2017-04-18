package edu.colorfuldonut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DavidKramer on 4/17/17.
 */

public class CanvasView extends View {
    protected Tool m_currentTool;
    protected Canvas m_canvas;
    protected Bitmap m_bitmap;
    protected Paint m_paint;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        init();
    }

    protected void init() {
        m_paint = new Paint();
        m_bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        m_canvas = new Canvas(m_bitmap);
    }

    public void saveImage() {

    }

    public void clear() {

    }

    public Bitmap getBitmap() {
        return m_bitmap;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(m_bitmap, 0, 0, m_paint);
    }

    public boolean onTouchEvent(MotionEvent e) {
        m_currentTool.handleInput(e, this);

        // TODO what boolean should we really be returning??
        return false;
    }

}
