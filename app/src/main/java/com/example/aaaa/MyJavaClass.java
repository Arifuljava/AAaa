package com.example.aaaa;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.mlkit.vision.common.InputImage;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// MyJavaClass.java
public class MyJavaClass {
    public interface SuccessCallback {
        void onSuccess(List<String> processedTextList);
    }

    public interface FailureCallback {
        void onFailure(String e);
    }
    static List<String> extractTextList = new ArrayList<>();
    static List<String> finalextractTextList = new ArrayList<>();
    static List<String> resultlist = new ArrayList<>();
    static TextRecognitionManager textRecognitionManager ;
    public  void SendBitmap(Bitmap bitmapw, Context context,
                                  SuccessCallback successCallback, FailureCallback failureCallback) {
        extractTextList.clear();
        finalextractTextList.clear();
        resultlist.clear();


        Bitmap bitmap = bitmapw;
        Log.e("KKKBitmap",""+bitmap);
        textRecognitionManager = new TextRecognitionManager();

        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String getText) {

                //
                getText =  getText
                        .replace("i","1")
                        .replace("*",":")
                        .replace("L", "1")
                        .replace("l", "1")
                        .replace("s", "5")
                        .replace("S", "5")
                        .replace("a", "8")
                        .replace("A", "8")
                        .replace("o","0")
                        .replace("O","0")
                        .replace("B","3")
                        .replace("b","2")
                        .replace("Þ","2")
                        .replace("P","9")
                        .replace("D","0")
                        .replace("$","3")
                        .replace(".",":")
                        .replace("\"", ":")
                        .replace("(","|")
                        .replace(")","|");
                Log.e("JJJJJ",""+getText);

                //extreact primary text task
                extractTextList = rowmanagement(getText);

                //text management
                String  message = "";
                String tasktargetrow;
                for (int i =0; i < extractTextList.size(); i++) {
                    Log.e("HHHAAA"+i+15,""+extractTextList.size());
                    if(extractTextList.get(i).length()<5)
                    {
                        extractTextList.remove(i);
                    }
                    else{
                        tasktargetrow = extractTextList.get(i);
                        message=message+"\n"+tasktargetrow;

                        String data = processTextInEveryrowAndReturnNewListWithElement(tasktargetrow);
                        Log.e("Fullcode",""+tasktargetrow);
                    }



                }
                String meeee="";

                for (int i =0; i < finalextractTextList.size(); i++) {
                    String  data = finalextractTextList.get(i);
                    if (data.length() < 4) {
                    }
                    else {
                        checking6value(data,i);
                    }
                }
                //
                for (int i =0; i < resultlist.size(); i++) {
                    String data = resultlist.get(i);
                    arrangementdata(data,i);
                    // meeee=meeee+" "+data;
                }
                for (int i =0; i < resultlist.size(); i++) {
                    String data = resultlist.get(i);
                    determinePercentage(data,i);
                }
                for (int i =0; i < resultlist.size(); i++) {
                    String data = resultlist.get(i);
                    meeee=meeee+" "+data;
                }
                //


                successCallback.onSuccess(resultlist);

            }

            @Override
            public void onError(String errorMessage) {
                failureCallback.onFailure(errorMessage);
            }
        });


    }
    private static void arrangementdata(String data, int index) {
        if(data.length()<7 && !TextUtils.isEmpty(data))
        {
            if(index==0)
            {
                String  prefix = resultlist.get(index+1);
                prefix = prefix.substring(0,2);
                data=prefix+""+data.substring(1,data.length());
                resultlist.remove(index);
                resultlist.add(index,data);
            }
            else {
                String  prefix = resultlist.get(index-1);
                prefix = prefix.substring(0,2);
                data=prefix+""+data.substring(1,data.length());
                resultlist.remove(index);
                resultlist.add(index,data);
                Log.e("KKKKKKK",""+prefix);

            }


        }
        else if(data.length()>=7 && !TextUtils.isEmpty(data)){
            int numbers = countNumbers(data);
            if(numbers>6)
            {

                String main = data.substring(0, 4) + ":" + data.substring(5);
                resultlist.remove(index);
                resultlist.add(index,main);
            }
            else {
                resultlist.remove(index);
                resultlist.add(index,data);
            }

        }
    }
    public static String processTextInEveryrowAndReturnNewListWithElement(String targetrow) {
        String dataelement ="" ;
        extract(targetrow);
        return dataelement;

    }
    public static void extract(String targetrow)
    {
        String[] stringArray = targetrow.split(" ");
        String tasktargetrow;
        for (int i =0; i < stringArray.length; i++) {
            tasktargetrow = stringArray[i];
            finalextractTextList.add(tasktargetrow);
        }
    }
    public static List<String> rowmanagement(String primanytext) {
        List<String> extractTextList = new ArrayList<>();
        String[] rows = primanytext.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i].trim();

                extractTextList.add(row);

            Log.e("Row " + (i + 1), row);

        }
        return  extractTextList;

    }
    public static String removeSpecialCharactersAndSpaces(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
    private static void checking6value(String data, int index) {
        String specialCharacters = "!@#$%^&*()_+-=[]{}|;:,.<>?/";
        String prefix  = data .substring(0,1);
        Log.e("fffff",""+prefix);
        if(data.length() > 0 && specialCharacters.contains(String.valueOf(data.charAt(0))))
        {
            System.out.println("The first character is a special character");
            data=data.substring(1,data.length());
        }
        if(data.length()>7)
        {
            if (data.contains("|")) {
                String[] parts = data.split("\\|");
                for (String part : parts) {
                    resultlist.add( part.trim());
                }

            }
            else {
                data=removeSpecialCharactersAndSpaces(data);
                List<String> substrings = new ArrayList<>();

                for (int i = 0; i < data.length(); i += 6) {
                    int endIndex = Math.min(i + 6, data.length());
                    substrings.add(data.substring(i, endIndex));

                }
                List<String> formattedSubstrings = new ArrayList<>();

                for (int i = 0; i < substrings.size(); i++) {
                    String originalString = substrings.get(i);
                    String formattedString;

                    if (originalString.length() > 4) {
                        formattedString = originalString.substring(0, 4) + ":" + originalString.substring(4);
                    } else {
                        formattedString = originalString;
                    }
                    Log.e("SUBStrings",""+formattedString);
                    resultlist.add( formattedString.trim());

                }

            }
        }
        else {

            resultlist.add(data.trim());
        }
    }
    private static int countNumbers(String text) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public static void determinePercentage(String word, int index){

        int numbers = countNumbers(word);

        if(numbers>=6)
        {
            String percentages = word+"(100%)";
            resultlist.remove(index);
            resultlist.add(index,percentages);
        }
        else {
            String percentages = word+"(80%)";
            resultlist.remove(index);
            resultlist.add(index,percentages);
        }


    }
}