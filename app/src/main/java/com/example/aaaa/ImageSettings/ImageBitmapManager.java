package com.example.aaaa.ImageSettings;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public class ImageBitmapManager {
    public static  Bitmap make90degress(Bitmap bitmap)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap make90degressbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return  make90degressbitmap;
    }
    public static  Bitmap toGrayscale(Bitmap original) {
        int width, height;
        height = original.getHeight();
        width = original.getWidth();
        Bitmap grayscaleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(grayscaleBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(original, 0, 0, paint);
        return grayscaleBitmap;
    }
    public Bitmap toBloodBlack(Bitmap original) {
        Bitmap grayscaleBitmap = original;
        Bitmap bloodBlackBitmap = Bitmap.createBitmap(grayscaleBitmap.getWidth(), grayscaleBitmap.getHeight(), grayscaleBitmap.getConfig());
        Canvas canvas = new Canvas(bloodBlackBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        float contrast = 2.2f;
        float brightness = -120;
        colorMatrix.set(new float[] {
                contrast, 0, 0, 0, brightness,
                0, contrast, 0, 0, brightness,
                0, 0, contrast, 0, brightness,
                0, 0, 0, 1, 0
        });
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(grayscaleBitmap, 0, 0, paint);
        return bloodBlackBitmap;
    }
    public Bitmap zoomInBitmap(Bitmap originalBitmap) {
        return zoomBitmap(originalBitmap, ZOOM_IN_SCALE);
    }

    public Bitmap zoomOutBitmap(Bitmap originalBitmap) {
        return zoomBitmap(originalBitmap, ZOOM_OUT_SCALE);
    }
    private static final float ZOOM_IN_SCALE = 1.2f;
    private static final float ZOOM_OUT_SCALE = 1.0f;
    private Bitmap zoomBitmap(Bitmap originalBitmap, float scaleFactor) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scaleFactor, scaleFactor, width / 2, height / 2);
        Bitmap scaledBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, true);
        return scaledBitmap;
    }
    public Bitmap invertColorsAndSetBlackBackground(Bitmap bitmap) {
        // Make the bitmap mutable if it isn't already
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelColor = bitmap.getPixel(x, y);

                // Extract the alpha, red, green, and blue components
                int alpha = Color.alpha(pixelColor);
                int red = Color.red(pixelColor);
                int green = Color.green(pixelColor);
                int blue = Color.blue(pixelColor);

                // Calculate brightness of the pixel
                int brightness = (red + green + blue) / 3;

                if (brightness > 128) { // Light pixel (likely background)
                    // Set it to black
                    bitmap.setPixel(x, y, Color.BLACK);
                } else { // Dark pixel (likely text/foreground)
                    // Set it to white
                    bitmap.setPixel(x, y, Color.WHITE);
                }
            }
        }

        return bitmap;
    }


}
