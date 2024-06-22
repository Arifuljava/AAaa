package com.example.aaaa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aaaa.localdatabase.AsiaDateTimeHelper;
import com.example.aaaa.localdatabase.HomeActivity;
import com.example.aaaa.localdatabase.ItemAdapter;
import com.example.aaaa.localdatabase.LoginActivityManager;
import com.example.aaaa.localdatabase.ProfileUpgradeManager;
import com.example.aaaa.localdatabase.RegistrationAcivity;
import com.example.aaaa.localdatabase.SharedPreferencesManager;
import com.example.aaaa.localdatabase.UserModel;
import com.example.aaaa.localdatabase.WorkManager;
import com.google.mlkit.vision.common.InputImage;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.util.UUID;
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
    RecyclerView listview;
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
    private ItemAdapter itemAdapter;
    private List<UserModel> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        resulttext=findViewById(R.id.resulttext);
        listview=findViewById(R.id.listview);
        //
        listview.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList);
        listview.setAdapter(itemAdapter);
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
    @NonNull
    private List<Bitmap> segmentImage(@NonNull Bitmap binaryImage) {
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

        // Create an InputImage object from a Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_26);
        /*
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


        //addItemToSharedPreferencesList("ArifulIslam");

       /*
        List<String> sampleList = Arrays.asList("item1", "item2", "item3");
        SharedPreferencesManager.saveListToSharedPreferences(MainActivity2.this, "MyPrefs", sampleList, "myListKey");
        String retrievedText = SharedPreferencesManager.retrieveTextFromSharedPreferences(MainActivity2.this, "MyPrefs", "myListKey");
        if (retrievedText != null) {
            // Do something with the retrieved text
            System.out.println("Retrieved Text: " + retrievedText);
        } else {
            System.out.println("No text found for the given key.");
        }
        */




         TextRecognitionManager textRecognitionManager;
        textRecognitionManager = new TextRecognitionManager();


        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String resultText) {
                 Log.d("Extracted Text", resultText);
                String[] words = resultText.split("\\s+");
                ArrayList<String> filteredWords = new ArrayList<>();
                for (String word : words) {
                    if (word.length() >= 6) {
                        if (containsEnglishAlphabet(word))
                        {

                        }
                        else {
                            filteredWords.add(word);
                        }
                    }
                }

                //remove special character from first
                removeSpecialCharacters(filteredWords);

                // remove white space
                //detectedTextList= removeWhitespaceFromList(filteredWords);

                //remove special character from list
                processStrings(filteredWords);

                // Print the modified strings
                filteredWords = splitAndProcess(filteredWords);

                // Print the result
                filteredWords = splitBy7(filteredWords);
                filteredWords = checkspecialindexxx(filteredWords);
                determinePercentage(filteredWords, resultList -> {
                    System.out.println("Resulting List:"+resultList);
                    System.out.println("Resulting List:"+resultList.size());
                });
                Log.d("haveeeee", ""+filteredWords);
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
                Log.e("Text Recognition Error", errorMessage);
            }
        });

/*
UserModel userModel=new UserModel("name", "email", "phonenumber"
        ,"uuid", "time", "username","password",
        "whatsappnumber", "depositbalance",
        "currentbalance22", "lastclick", "refername",
        "withdrawtotal", "sendmoneytotal") ;
 */
     //  String uiui = UUID.randomUUID().toString();
        //addItemToSharedPreferencesList(userModel);
      //  displayFullList22();


    }
    private static ArrayList<String> checkspecialindexxx(ArrayList<String> inputList) {
        ArrayList<String> result = new ArrayList<>();

        for (String input : inputList) {
            if (input.contains(":")) {
                result.add(input);

            }
            else {
                String modifiedString = input.substring(0, 4) + ":" + input.substring(input.length() - 2);
                result.add(modifiedString);
            }


        }

        return result;
    }
    private static ArrayList<String> splitBy7(ArrayList<String> inputList) {
        ArrayList<String> result = new ArrayList<>();

        for (String input : inputList) {
            if (input.length() >= 7) {
                // Split the string into parts of length 7
                for (int i = 0; i < input.length(); i += 7) {
                    // Ensure the substring doesn't exceed the string length
                    String part = input.substring(i, Math.min(i + 7, input.length()));
                    result.add(part);
                }
            } else {
                // If the string is less than 7 characters, add it as is
                result.add(input);
            }
        }

        return result;
    }
    private static ArrayList<String> splitAndProcess(ArrayList<String> inputList) {
        ArrayList<String> result = new ArrayList<>();

        for (String input : inputList) {
            // Split by any special character except ':'
            String[] parts = input.split("[^a-zA-Z0-9:.]");

            // Add each part to result
            result.addAll(Arrays.asList(parts));
        }

        return result;
    }
    private static void processStrings(ArrayList<String> stringsList) {
        for (int i = 0; i < stringsList.size(); i++) {
            String originalString = stringsList.get(i);
            int indexOfColon = originalString.indexOf(':');

            if (indexOfColon != -1) { // Ensure ':' exists in the string
                String beforeColon = originalString.substring(0, indexOfColon);
                if (beforeColon.length() > 4) {
                    // Trim the string before ':' to 4 characters
                    String trimmedString = beforeColon.substring(beforeColon.length() - 4);
                    // Update the original string in the list
                    stringsList.set(i, trimmedString + originalString.substring(indexOfColon));
                }
            }
        }
    }
    private static void removeSpecialCharacters(ArrayList<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (word.length() > 0 && !Character.isLetterOrDigit(word.charAt(0))) {
                // Remove the first character if it's a special character
                words.set(i, word.substring(1));
            }
        }
    }
    private static List<String> splitText(String text) {
        String[] words = text.split(" ");
        List<String> parts = new ArrayList<>();

        int length = words.length;
        int partSize = (length > 7) ? (int) Math.ceil((double) length / 8) : length;

        for (int i = 0; i < length; i += partSize) {
            StringBuilder part = new StringBuilder();
            for (int j = i; j < i + partSize && j < length; j++) {
                if (j > i) {
                    part.append(" ");
                }
                part.append(words[j]);
            }
            parts.add(part.toString());
        }

        return parts;
    }
    private boolean containsEnglishAlphabet(String word) {
        return word.matches(".*[a-zA-Z]+.*");
    }
    private void displayFullList22() {
        List<UserModel> fullList = SharedPreferencesManager.retrieveListFromSharedPreferences(this, PREFS_NAME, LIST_KEY, UserModel.class);
        if (fullList != null) {
            itemAdapter.setItemList(fullList);} else {
            itemAdapter.setItemList(new ArrayList<UserModel>());}
        Log.e("HJHHJH",""+fullList);
    }
    private void displayFullList() {

        List<UserModel> fullList = SharedPreferencesManager.retrieveListFromSharedPreferences(this, PREFS_NAME, LIST_KEY, UserModel.class);
        if (fullList != null && !fullList.isEmpty()) {
            StringBuilder listDisplay = new StringBuilder();
            for (UserModel item : fullList) {
                listDisplay.append(item).append("\n");
            }
            resulttext.setText(listDisplay.toString());
        } else {
            resulttext.setText("No items found.");
        }
    }
    private static final String PREFS_NAME = "MyPrefs";
    private static final String LIST_KEY = "myListKey1";
    private void addItemToSharedPreferencesList(UserModel newItem) {

        List<UserModel> currentList = SharedPreferencesManager.retrieveListFromSharedPreferences(this, PREFS_NAME, LIST_KEY, UserModel.class);
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        // Add the new item to the list
        currentList.add(newItem);
        // Save the updated list back to SharedPreferences
        SharedPreferencesManager.saveListToSharedPreferences(this, PREFS_NAME, currentList, LIST_KEY);
        Toast.makeText(this, "Item added: " + newItem, Toast.LENGTH_SHORT).show();
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