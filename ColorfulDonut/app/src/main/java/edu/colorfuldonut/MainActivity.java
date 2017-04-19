package edu.colorfuldonut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static ToolFactory toolFactory;

    protected CanvasView m_canvasView;
    protected ToolBar m_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolFactory = new ToolFactory();
        m_canvasView = (CanvasView)findViewById(R.id.canvas);
        m_toolbar = (ToolBar)findViewById(R.id.toolbar);
        m_toolbar.init();
        m_canvasView.setToolbar(m_toolbar);
    }

    public void clearButtonClicked(View view) {
        m_canvasView.clear();
        Toast.makeText(this, "Cleared!", Toast.LENGTH_SHORT).show();
    }

    public void toolButtonClicked(View view) {
        Toast.makeText(this, "Brush!", Toast.LENGTH_SHORT).show();
    }
}
