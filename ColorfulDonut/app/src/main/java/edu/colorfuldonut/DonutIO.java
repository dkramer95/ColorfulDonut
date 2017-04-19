package edu.colorfuldonut;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DavidKramer on 4/18/17.
 */

public class DonutIO {

    public static boolean saveImage(String filepath, Bitmap image) {
        boolean success = false;
        try {
            FileOutputStream out = new FileOutputStream(new File(filepath));
            image.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static Bitmap loadImage(String filepath) {
        Bitmap image = BitmapFactory.decodeFile(filepath);
        return image;
    }
}
