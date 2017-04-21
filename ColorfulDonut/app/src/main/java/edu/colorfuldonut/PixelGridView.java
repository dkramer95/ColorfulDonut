package edu.colorfuldonut;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Donut on 4/21/2017.
 */

public class PixelGridView extends Tool {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private int viewWidth, viewHeight;
    private Paint blackPaint = new Paint();
    private boolean[][] cellChecked;

    public PixelGridView(){
        m_name = "Pixel Grid";
    }

    public PixelGridView(int viewWidth, int viewHeight, int c, int r, CanvasView cv) {
        m_name = "Pixel Grid";
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        setNumColumns(c, cv);
        setNumRows(r, cv);
    }

    public void setNumColumns(int numColumns, CanvasView cv) {
        this.numColumns = numColumns;
        calculateDimensions(cv);
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows, CanvasView cv) {
        this.numRows = numRows;
        calculateDimensions(cv);
    }

    public int getNumRows() {
        return numRows;
    }

//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        calculateDimensions();
//    }

    private void calculateDimensions(CanvasView cv) {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = viewWidth / numColumns;
        cellHeight = viewHeight / numRows;

        cellChecked = new boolean[numColumns][numRows];

        cv.invalidate();
    }

    protected void onDraw(Canvas canvas) {

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = this.viewWidth;
        int height = this.viewHeight;

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (cellChecked[i][j]) {

                    canvas.drawRect(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight,
                            blackPaint);
                }
            }
        }

        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, blackPaint);
        }

        for (int i = 1; i < numRows; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);
        }
    }

    @Override
    public boolean handleInput(MotionEvent e, CanvasView canvasView) {

        canvasView.m_toolbar.setCurrentTool(canvasView.m_pixelGrid);
        calculateDimensions(canvasView);
        onDraw(canvasView.m_canvas);
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(e.getX() / cellWidth);
            int row = (int)(e.getY() / cellHeight);

            //cellChecked[column][row] = !cellChecked[column][row];
            canvasView.invalidate();
        }

        return true;
    }

}