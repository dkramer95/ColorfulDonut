package edu.colorfuldonut;

/**
 * Created by DavidKramer on 4/18/17.
 */

public class ToolFactory {

    public Tool create(String type) {
        switch (type) {
            case "Brush":
                return new Brush();
            case "Paint Bucket":
                return new PaintBucket();
        }
        return null;
    }

    public String[] getToolNames() {
        return new String[] { "Brush", "Paint Bucket" };
    }
}
