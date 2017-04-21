package edu.colorfuldonut;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    public static ToolFactory toolFactory;
    protected CanvasView m_canvasView;
    protected ToolBar m_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_toolbar = new ToolBar();
        m_canvasView = (CanvasView)findViewById(R.id.canvas);
        m_canvasView.setToolbar(m_toolbar);

        initComponents();
    }

    protected void initComponents() {
        createTools();
        createSlider();
        createColors();
    }

    /**
     * Creates all the color swatches.
     */
    protected void createColors() {
        GridLayout colorPanel = (GridLayout)findViewById(R.id.colorPanel);
        int colorCount = colorPanel.getChildCount();

        int[] colors = new int[] { Color.RED, Color.YELLOW, Color.GREEN,
                Color.BLUE, Color.BLACK, Color.WHITE };

        for (int j = 0; j < colorCount; ++j) {
            final int color = colors[j];

            Button colorButton = (Button)colorPanel.getChildAt(j);
            Drawable drawable = getResources().getDrawable(R.drawable.circle);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            colorButton.setBackground(drawable);

            colorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Brush brushTool = (Brush)m_toolbar.getToolByName("Brush");
                    brushTool.setColor(color);
                }
            });
        }
    }


    /**
     * Creates the brush size slider adjustment
     */
    protected void createSlider() {
        final SeekBar brushSizeSlider = (SeekBar)findViewById(R.id.brushSizeSlider);
        brushSizeSlider.setMax(50);
        brushSizeSlider.setProgress(10);
        brushSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int size = i;
                if (i < 1) {
                    brushSizeSlider.setProgress(1);
                    size = 1;
                }
                Brush brushTool = (Brush)m_toolbar.getToolByName("Brush");
                brushTool.setSize(size);

                // update text indicator
                TextView sizeLabel = (TextView)findViewById(R.id.brushSizeTextView);
                sizeLabel.setText("" + size);
            }
            // unused
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    protected void createTools() {
        LinearLayout toolPanel = (LinearLayout)findViewById(R.id.toolPanel);
        int toolCount = toolPanel.getChildCount();

        for (int j = 0; j < toolCount; ++j) {
            final Button toolButton = (Button)toolPanel.getChildAt(j);
            String toolName = toolButton.getTag().toString();
            final Tool tool = ToolFactory.create(toolName);
            m_toolbar.addTool(toolName, tool);
            toolButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    m_toolbar.setCurrentTool(tool);
                    toolButtonClicked(toolButton);
                }
            });
        }
        m_toolbar.finishInit();
    }

    public void saveButtonClicked(View view) {
        Toast.makeText(this, "STILL NEED TO IMPLEMENT SAVING", Toast.LENGTH_SHORT).show();
    }

    public void clearButtonClicked(View view) {
        m_canvasView.clear();
        Toast.makeText(this, "Cleared!", Toast.LENGTH_SHORT).show();
    }

    public void toolButtonClicked(View view) {
        Button toolButton = (Button)view;
        String toolName = toolButton.getTag().toString();
        Toast.makeText(this, toolName, Toast.LENGTH_SHORT).show();
    }
}
