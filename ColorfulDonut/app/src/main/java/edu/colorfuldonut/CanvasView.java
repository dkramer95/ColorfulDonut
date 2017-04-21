package edu.colorfuldonut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DavidKramer on 4/17/17.
 */

public class CanvasView extends View {
    protected ToolBar m_toolbar;
    protected Canvas m_canvas;
    protected Bitmap m_bitmap;
    protected Paint m_paint;
    protected Path m_path;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        init();
    }

    protected void init() {
        m_paint = new Paint();
        m_path = new Path();

        // default starting color
        GlobalColor.set(Color.BLACK);
        m_paint.setColor(GlobalColor.get());
        // some reason getWidth() has been returning zero, which will crash app
        // if we create a 0 sized bitmap
        int width =  getWidth() == 0 ? 1200 : getWidth();
        int height = getHeight() == 0 ? 1200 : getHeight();

        m_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        m_canvas = new Canvas(m_bitmap);
        m_canvas.drawColor(Color.WHITE);
    }

    public void clear() {
        m_bitmap.recycle();
        init();
        invalidate();
    }

    public Bitmap getBitmap() {
        return m_bitmap;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        m_paint.setColor(GlobalColor.get());
        canvas.drawBitmap(m_bitmap, 0, 0, m_paint);
        m_canvas.drawPath(m_path, m_paint);
    }

    public void setBitmap(Bitmap bitmap) {
        m_bitmap = bitmap;
    }

    public boolean onTouchEvent(MotionEvent e) {
        boolean b = false;
        Tool tool = m_toolbar.getCurrentTool();
        if (tool != null) {
            b = tool.handleInput(e, this);
        }
        // TODO what boolean should we really be returning??
        return b;
    }

    public void setToolbar(ToolBar toolbar) {
        m_toolbar = toolbar;
    }
}
