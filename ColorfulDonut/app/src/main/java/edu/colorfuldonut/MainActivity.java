package edu.colorfuldonut;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    protected static final int OPEN_IMAGE = 0;

    protected CanvasView m_canvasView;
    protected ToolBar m_toolbar;
    protected DeviceShake m_shakeToClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        createShakeListener();
        animateIn();
    }

    protected void onResume() {
        super.onResume();
        m_shakeToClear.register();
    }

    protected void onPause() {
        super.onPause();
        m_shakeToClear.unregister();
    }

    protected void initComponents() {
        m_toolbar = new ToolBar();
        m_canvasView = (CanvasView)findViewById(R.id.canvas);
        m_canvasView.setToolbar(m_toolbar);

        createTools();
        createSlider();
        createColors();
    }

    protected void animateIn() {
        m_canvasView.setAlpha(0f);

        LinearLayout toolbar = (LinearLayout)findViewById(R.id.toolbar);
        toolbar.animate().translationYBy(-1000f).alpha(0f).setDuration(0).start();
        toolbar.animate().translationYBy(1000f).alpha(1f).setDuration(1250).withEndAction(new Runnable() {
            @Override
            public void run() {
                m_canvasView.clear();
                m_canvasView.animate().alpha(1f).setDuration(750).start();
            }
        }).start();
    }

    protected void createShakeListener() {
        m_shakeToClear = new DeviceShake(this, new DeviceShake.ShakeCallback() {
            @Override
            public void onShake() {
                animatedCanvasClear();
            }
        });
    }

    /**
     * Creates all the color swatches.
     */
    protected void createColors() {
        GridLayout colorPanel = (GridLayout)findViewById(R.id.colorPanel);
        int colorCount = colorPanel.getChildCount();

        int[] colors = new int[] { Color.RED, Color.YELLOW, Color.GREEN,
                Color.BLUE, Color.BLACK, Color.WHITE };

        class RadioButtonController {
            private RadioButton m_activeButton;

            void setActiveButton(RadioButton button) {
                if (m_activeButton != null) {
                    m_activeButton.setChecked(false);
                }
                m_activeButton = button;
                m_activeButton.setChecked(true);
            }
        }

        final RadioButtonController group = new RadioButtonController();

        for (int j = 0; j < colorCount; ++j) {
            final int color = colors[j];

            final RadioButton colorButton = (RadioButton) colorPanel.getChildAt(j);
            Drawable drawable = getResources().getDrawable(R.drawable.circle);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

            colorButton.setBackground(drawable);

            colorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // singleton color so that other tools can use
                    group.setActiveButton(colorButton);
                    GlobalColor.set(color);
                }
            });
        }
        group.setActiveButton((RadioButton)colorPanel.getChildAt(0));
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
        GridLayout toolPanel = (GridLayout) findViewById(R.id.toolPanel);
        int toolCount = toolPanel.getChildCount();

        class ButtonController {
            private AppCompatButton m_activeButton;

            void setActiveButton(AppCompatButton button) {
                if (m_activeButton != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        m_activeButton.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
                    }
                }
                m_activeButton = button;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button.setBackgroundTintMode(PorterDuff.Mode.ADD);
                }
            }
        }

        ButtonController controller = new ButtonController();

        for (int j = 0; j < toolCount; ++j) {
            final Button toolButton = (Button)toolPanel.getChildAt(j);
            final String toolName = toolButton.getTag().toString();
            final Tool tool = ToolFactory.create(toolName);
            m_toolbar.addTool(toolName, tool);
            toolButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tool.m_name = toolName;
                    m_toolbar.setCurrentTool(tool);
                    toolButtonClicked(toolButton);
                }
            });
        }
        controller.setActiveButton((AppCompatButton)toolPanel.getChildAt(0));
        m_toolbar.finishInit();
    }

    public void saveButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String filename = SimpleDateFormat.getDateInstance().toString();
        Bitmap bitmap = m_canvasView.getBitmap();
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                filename, "Colored Donut Drawing");
        Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
    }

    public void clearButtonClicked(View view) {
        animatedCanvasClear();
    }

    public void animatedCanvasClear() {
        m_canvasView.animate().alpha(0f).setDuration(750).withEndAction(new Runnable() {
            @Override
            public void run() {
                m_canvasView.clear();
                m_canvasView.animate().alpha(1f).setDuration(300).start();
            }
        }).start();
    }

    public void toolButtonClicked(View view) {
        Button toolButton = (Button)view;
        Drawable drawable = toolButton.getBackground();

        String toolName = toolButton.getTag().toString();
        Toast.makeText(this, toolName, Toast.LENGTH_SHORT).show();
    }

    public void openButtonClicked(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Open Image"), OPEN_IMAGE);
    }

    public void openImage(int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    Toast.makeText(this, "Image loaded!", Toast.LENGTH_LONG).show();
                    m_canvasView.addImageBitmap(bmp);
                } catch (IOException e) {
                    Toast.makeText(this, "Failed to open image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case OPEN_IMAGE:
                openImage(resultCode, data);
                break;
        }
    }
}
