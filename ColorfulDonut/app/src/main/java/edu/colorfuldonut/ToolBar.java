package edu.colorfuldonut;

import java.util.HashMap;

/**
 * Created by DavidKramer on 4/18/17.
 */

public class ToolBar {
    protected Tool m_currentTool;
    protected HashMap<String, Tool> m_tools;

    public ToolBar() {
        init();
    }

    public void init() {
        m_tools = new HashMap<>();
    }

    public void finishInit() {
        m_currentTool = m_tools.get(0);
    }

    public void addTool(String toolName, Tool tool) {
        m_tools.put(toolName, tool);
    }

    public Tool getCurrentTool() {
        return m_currentTool;
    }

    public Tool getToolByName(String name) {
        Tool tool = m_tools.get(name);
        return tool;
    }

    public void setCurrentTool(Tool tool) {
        m_currentTool = tool;
    }
}
