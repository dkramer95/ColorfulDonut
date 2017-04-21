package edu.colorfuldonut;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

/**
 * Created by DavidKramer on 4/18/17.
 */
public class PaintBucket extends Tool {

    private int mX = 0;
    private int mY = 0;
    private Path m_path;
    private Paint m_paint;
    private static final float TOLERANCE = 5;

    public PaintBucket(){
        init();
    }

    public void init(){
        m_name = "Paint Bucket";
        m_path = new Path();
        m_paint = new Paint();
        m_paint.setColor(Color.BLUE);
        m_paint.setStyle(Paint.Style.FILL);
        m_paint.setStrokeJoin(Paint.Join.ROUND);
        m_paint.setStrokeWidth(4f);
    }

    private void startTouch(int x, int y, Path path) {
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    @Override
    public boolean handleInput(MotionEvent e, CanvasView canvasView) {
        int x = (int) e.getX();
        int y = (int) e.getY();;
        Bitmap bitmap = canvasView.getBitmap();
        int targetColor = bitmap.getPixel(mX,mY);
        //TODO: Uncomment comments below to give Bucket Button functionality.
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y, m_path);
                break;
            case MotionEvent.ACTION_UP:
                QueueLinearFloodFiller filler = new QueueLinearFloodFiller(bitmap, targetColor, GlobalColor.get());
                filler.prepare();
                filler.floodFill(x, y);
                break;
            default:
                return false;
        }
//        canvasView.m_paint = m_paint;
        canvasView.invalidate();
        return true;
    }
}