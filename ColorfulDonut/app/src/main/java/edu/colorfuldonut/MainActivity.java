package edu.colorfuldonut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected CanvasView m_canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_canvasView = (CanvasView)findViewById(R.id.canvas);
    }

    public void clearButtonClicked(View view) {
        m_canvasView.clear();
        Toast.makeText(this, "Cleared!", Toast.LENGTH_SHORT).show();
    }

    public void toolButtonClicked(View view) {
        if(view.getId() == R.id.button3){
            Toast.makeText(this, "Brush!", Toast.LENGTH_SHORT).show();
            m_canvasView.m_currentTool = new Brush();
        }
    }
}
