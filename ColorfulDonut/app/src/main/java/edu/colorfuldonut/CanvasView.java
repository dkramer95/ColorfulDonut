package edu.colorfuldonut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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
    protected PixelGridView m_pixelGrid;
    protected Paint m_paint;
    protected Path m_path;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        init();
    }

    protected void init() {
        m_paint = new Paint();
        m_path = new Path();

        GlobalColor.set(Color.BLACK);
        m_paint.setColor(GlobalColor.get());

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
        m_pixelGrid = new PixelGridView(m_bitmap.getWidth(), m_bitmap.getHeight(), m_bitmap.getWidth()/30, m_bitmap.getHeight()/30, this);
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

    public void addImageBitmap(final Bitmap bmp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // create bitmap so that it fits within our canvas and isn't stretched
                Matrix m = new Matrix();
                m.setRectToRect(new RectF(0, 0, bmp.getWidth(), bmp.getHeight()),
                        new RectF(0, 0, getWidth(), getHeight()), Matrix.ScaleToFit.CENTER);
                Bitmap scaledBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
                m_canvas.drawBitmap(scaledBitmap, 0, 0, m_paint);
            }
        }).start();
    }
}
