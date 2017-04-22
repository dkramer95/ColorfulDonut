package edu.colorfuldonut;

/**
 * Created by DavidKramer on 4/18/17.
 */

public class ToolFactory {

    public static Tool create(String type) {
        switch (type) {
            case "Brush":
                return new Brush();
            case "Paint Bucket":
                return new PaintBucket();
            case "Pixel Grid":
                return new PixelGridView();
            case "Pencil":
                return new Pencil();
       }
        return null;
    }

    public static String[] getToolNames() {
        return new String[] { "Brush", "Paint Bucket", "Pixel Grid" };
    }
}
