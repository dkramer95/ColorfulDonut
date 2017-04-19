package edu.colorfuldonut;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by DavidKramer on 4/18/17.
 */

public class ToolBar extends LinearLayout {
    protected Tool m_currentTool;
    protected ArrayList<Button> m_toolButtons;

    public ToolBar(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public void init() {
        setLayoutMode(HORIZONTAL);
        createButtons();
    }

    protected void createButtons() {
        m_toolButtons = new ArrayList<>();
        String[] toolNames = MainActivity.toolFactory.getToolNames();

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.setMargins(5, 5, 5, 5);
        setPadding(10, 10, 10, 10);

        // Go through list of tool names from the factory and create buttons for them
        for(String s : toolNames) {
            Button toolButton = createToolButton(s);
            toolButton.setLayoutParams(params);
            m_toolButtons.add(toolButton);
            addView(toolButton);
        }

        // fake out first time, so we have an active tool
        m_toolButtons.get(0).callOnClick();
    }

    protected Button createToolButton(String text) {
        final Tool tool = MainActivity.toolFactory.create(text);
        final Button toolButton = new Button(getContext());
        toolButton.setText(text);
        toolButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.normalripplebutton));
        toolButton.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        toolButton.setPadding(25, 25, 25, 25);

        toolButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentTool(tool, toolButton);

                // debugging stuff TODO remove later
                Toast.makeText(getContext(), toolButton.getText()
                        + " was clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return toolButton;
    }

    public Tool getCurrentTool() {
        return m_currentTool;
    }

    public void setCurrentTool(Tool tool, Button button) {
        // set all buttons to their normal state
        for(Button b : m_toolButtons) {
            b.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.normalripplebutton));
        }

        // set style for the active tool button to make it clear what tool is active
        button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.selectedripplebutton));
        m_currentTool = tool;
    }
}
