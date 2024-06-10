package com.example.aaaa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.aaaa.localdatabase.AsiaDateTimeHelper;
import com.example.aaaa.localdatabase.HomeActivity;
import com.example.aaaa.localdatabase.ProfileUpgradeManager;
import com.example.aaaa.localdatabase.RegistrationAcivity;
import com.example.aaaa.localdatabase.SharedPreferencesManager;
import com.example.aaaa.localdatabase.WorkManager;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.util.function.Consumer;


public class MainActivity2 extends AppCompatActivity {
    ImageView realimage,changeimage;
    private  int current_index = 0 ;
    private List<Bitmap> segments;

    private static final String TAG = "TextExtractorActivity";
    private ImageView imageView;
    private TextView textView,resulttext;


    private static final int INPUT_SIZE = 224; // Assuming your model expects a 224x224 input image
    private static final int OUTPUT_SIZE = 1000; // Assuming your model has 1000 output classes

    private Interpreter interpreter;
    private List<String> detectedTextList = new ArrayList<>();
    private void loadModel() throws IOException {
        MappedByteBuffer modelFile = loadModelFile();
        Interpreter.Options options = new Interpreter.Options();
        interpreter = new Interpreter(modelFile, options);
    }
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    private Bitmap preprocess(Bitmap inputBitmap) {
        // Resize the image to 28x28 pixels
        int targetWidth = 28;
        int targetHeight = 28;
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(inputBitmap, targetWidth, targetHeight, true);

        return resizedBitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        resulttext=findViewById(R.id.resulttext);
        //
        try {
            loadModel();
            Bitmap inputBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.one);
            Bitmap preprocessedBitmap = preprocess(inputBitmap);

            // Run the TensorFlow Lite model

        } catch (IOException e) {
            Log.e("AAAAAAAAAA", "Failed to load model", e);
            return;
        }
        //

        realimage=findViewById(R.id.realimage);
        changeimage=findViewById(R.id.changeimage);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.su);
        realimage.setImageBitmap(bitmap);
        Bitmap grayImage = preprocessImage(bitmap);
        Bitmap binaryImage =binarizeImage(grayImage);
        segments = segmentImage(binaryImage);

    }

    private Bitmap preprocessImage(Bitmap inputImage) {
        Bitmap grayImage = Bitmap.createBitmap(inputImage.getWidth(), inputImage.getHeight(), Bitmap.Config.ARGB_8888);
        for (int x = 0; x < inputImage.getWidth(); x++) {
            for (int y = 0; y < inputImage.getHeight(); y++) {
                int pixel = inputImage.getPixel(x, y);
                int gray = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3;
                grayImage.setPixel(x, y, Color.rgb(gray, gray, gray));
            }
        }
        return grayImage;
    }
    private Bitmap binarizeImage(Bitmap grayImage) {
        Bitmap binaryImage = Bitmap.createBitmap(grayImage.getWidth(), grayImage.getHeight(), Bitmap.Config.ARGB_8888);
        for (int x = 0; x < grayImage.getWidth(); x++) {
            for (int y = 0; y < grayImage.getHeight(); y++) {
                int pixel = grayImage.getPixel(x, y);
                int gray = Color.red(pixel);
                if (gray < 128) {
                    binaryImage.setPixel(x, y, Color.BLACK);
                } else {
                    binaryImage.setPixel(x, y, Color.WHITE);
                }
            }
        }
        return binaryImage;
    }
    private List<Bitmap> segmentImage(Bitmap binaryImage) {
        int width = binaryImage.getWidth();
        int height = binaryImage.getHeight();
        List<Integer> columnSums = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            int sum = 0;
            for (int y = 0; y < height; y++) {
                if (binaryImage.getPixel(x, y) == Color.BLACK) {
                    sum++;
                }
            }
            columnSums.add(sum);
        }

        List<Integer> boundaries = new ArrayList<>();
        boolean inSegment = false;
        for (int x = 0; x < width; x++) {
            if (columnSums.get(x) > 0 && !inSegment) {
                boundaries.add(x);
                inSegment = true;
            } else if (columnSums.get(x) == 0 && inSegment) {
                boundaries.add(x);
                inSegment = false;
            }
        }

        List<Bitmap> segments = new ArrayList<>();
        for (int i = 0; i < boundaries.size(); i += 2) {
            int startX = boundaries.get(i);
            int endX = boundaries.get(i + 1);
            Bitmap segment = Bitmap.createBitmap(binaryImage, startX, 0, endX - startX, height);
            segments.add(segment);
        }

        return segments;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changee(View view) {
       /*
        // Create an InputImage object from a Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ccf);
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        changeimage.setImageBitmap(bitmap);
        OCRManager ocrManager = new OCRManager();
        ocrManager.performOCR(image, new OCRManager.OCRCallback() {
            @Override
            public void onOCRComplete(List<String> detectedTextList) {
                // Process the detectedTextList here
                detectedTextList=filterdatalist(detectedTextList);
                detectedTextList = cleanTextList(detectedTextList);
                detectedTextList= removeWhitespaceFromList(detectedTextList);
                detectedTextList = modifyTextList(detectedTextList);
                detectedTextList = sortTextListByPrefix(detectedTextList);
                detectedTextList = processTextList(detectedTextList);
                determinePercentage(detectedTextList, resultList -> {
                    System.out.println("Resulting List:"+resultList);
                    System.out.println("Resulting List:"+resultList.size());
                });
                //now add :
                resulttext.setText("");
                resulttext.setText(""+detectedTextList);
                Log.d("KKKKKKKKKKKK", ""+detectedTextList.size());
            }
        });


        */
    }
    public static List<String> filterdatalist(List<String> textList) {
        List<String> updatedTextList = new ArrayList<>();

        for (String text : textList) {
            int length = text.length();
            if (length >=6)
            {
                updatedTextList.add(text);
            }
        }
        return updatedTextList;
    }
    public void determinePercentage(List<String> detectedTextList, Consumer<List<String>> callback) {
        List<String> newList = new ArrayList<>();
        for (String text : detectedTextList) {
            if (text.length() >= 5) {
                long digitCount = text.chars().filter(Character::isDigit).count();
                long specialCharacterCount = text.chars().filter(c -> !Character.isLetterOrDigit(c) && !Character.isWhitespace(c)).count();
                if (digitCount >= 6) {
                    int digitScore = 4 * 20;
                    int specialCharacterScore = specialCharacterCount > 1 ? (int) (specialCharacterCount * 10) : 20;
                    int total = digitScore + specialCharacterScore;
                    String digitScoreString = total + "%";
                    newList.add(digitScoreString);
                } else {
                    int digitScore = (int) (digitCount * 15);
                    int specialCharacterScore = specialCharacterCount > 1 ? (int) (specialCharacterCount * 5) : 5;
                    int total = digitScore + specialCharacterScore;
                    String digitScoreString = total + "%";
                    newList.add(digitScoreString);
                }
            }
        }
        callback.accept(newList);
    }
    public static List<String> processTextList(List<String> textList) {
        List<String> updatedTextList = new ArrayList<>();

        for (String text : textList) {
            int length = text.length();
            for (int j = 0; j < length; j += 6) {
                int endIndex = Math.min(j + 6, length);
                String substring = text.substring(j, endIndex);
                if (substring.length() >= 6) {
                    StringBuilder sb = new StringBuilder(substring);
                    sb.insert(4, ":");
                    updatedTextList.add(sb.toString());
                } else {
                    updatedTextList.add(substring);
                }
            }
        }

        return updatedTextList;
    }
    public static List<String> sortTextListByPrefix(List<String> textList) {
        List<String> sortedTextList = new ArrayList<>(textList);

        Collections.sort(sortedTextList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String s1Prefix = s1.length() >= 2 ? s1.substring(0, 2) : s1;
                String s2Prefix = s2.length() >= 2 ? s2.substring(0, 2) : s2;
                return s1Prefix.compareTo(s2Prefix);
            }
        });

        return sortedTextList;
    }
    public static List<String> modifyTextList(List<String> textList) {
        List<String> modifiedTextList = new ArrayList<>();

        for (String text : textList) {
            if (!text.isEmpty()) {
                int length = text.length();
                int kk = length % 6;
                String modifiedText = text.substring(kk);
                modifiedTextList.add(modifiedText);
            } else {
                modifiedTextList.add("");
            }
        }

        return modifiedTextList;
    }
    public static List<String> removeWhitespaceFromList(List<String> textList) {
        List<String> cleanedTextList = new ArrayList<>();

        for (String text : textList) {
            String cleanedText = text.replaceAll("\\s+", "");
            cleanedTextList.add(cleanedText);
        }

        return cleanedTextList;
    }
    public static String getText(Context context, Bitmap bitmap) {
String getTxt = "0";
return  getTxt;
    }
    public static List<String> cleanTextList(List<String> textList) {
        List<String> cleanedTextList = new ArrayList<>();

        for (String text : textList) {
            String cleanedText = text.replaceAll("[^a-zA-Z0-9\\s+]", "");
            cleanedTextList.add(cleanedText);
        }

        return cleanedTextList;
    }
}