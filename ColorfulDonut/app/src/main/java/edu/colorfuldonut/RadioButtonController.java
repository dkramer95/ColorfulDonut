package edu.colorfuldonut;

import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by DavidKramer on 4/21/17.
 */

public class RadioButtonController {
    private RadioButton m_activeButton;
    private ArrayList<RadioButton> m_buttons;

    public RadioButtonController() {
        m_buttons = new ArrayList<>();
    }

    public void setActiveButton(RadioButton button) {
        for(int j = 0; j < m_buttons.size(); ++j) {
            RadioButton b = m_buttons.get(j);
            b.setChecked(false);
        }
        m_activeButton = button;
        m_activeButton.setChecked(true);
    }

    public void addButton(RadioButton button) {
        m_buttons.add(button);
    }
}
