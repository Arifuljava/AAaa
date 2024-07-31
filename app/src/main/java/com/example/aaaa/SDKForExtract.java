
package com.example.aaaa;

import java.util.List;
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
public class SDKForExtract {
    public interface SuccessCallback {
        void onSuccess(List<String> processedTextList);
    }

    public interface FailureCallback {
        void onFailure(String e);
    }

    static TextRecognitionManager textRecognitionManager ;
    static int counting6 = 0;
    static  int datestart15 = 16;
    static int datestart = 1;
    static int  tracking = 0 ;
    static int countzeroto15 = 0;
    static int count15to31 = 0 ;
    static List<String> extractRowList = new ArrayList<>();
    static  List<String> extractSpaceList = new ArrayList<>();
    static List<String> spiltpartcount = new ArrayList<>();
    static List<String> timeList = new ArrayList<>();
    static List<String> dateList = new ArrayList<>();
    static List<String> finaldatelist = new ArrayList<>();
    static List<String> finalresult = new ArrayList<>();
    public  static  void SendBitmap(Bitmap bitmapw, Context context,
                                    SDKForExtract.SuccessCallback successCallback, SDKForExtract.FailureCallback failureCallback) {
        //clean
        extractRowList.clear();
        extractSpaceList.clear();
        spiltpartcount.clear();
        timeList.clear();
        dateList.clear();
        finaldatelist.clear();
        finalresult.clear();
        counting6 = 0;
        datestart15 = 16;
        datestart = 1;
        tracking = 0 ;
        countzeroto15 = 0;
        count15to31 = 0 ;
        //clean

        Bitmap bitmap = bitmapw;
        Log.e("KKKBitmap",""+bitmap);
        textRecognitionManager = new TextRecognitionManager();
        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String getText) {
                getText=replaced(getText);
                extractRowList = rowmanagement(getText);
                extractFromRow();
                successCallback.onSuccess(finalresult);

            }

            @Override
            public void onError(String errorMessage) {
                failureCallback.onFailure(errorMessage);
            }
        });

    }
    private static void extractbySpaceAndStoredinArray(String rowselected) {
        String[] stringArray = rowselected.split(" ");
        String tasktargetrowdata;
        int count_part = 0;
        for (int i =0; i < stringArray.length; i++) {
            tasktargetrowdata = stringArray[i];
            if(tasktargetrowdata.length()>2)
            {
                extractSpaceList.add(tasktargetrowdata);
                if(tasktargetrowdata.length()>4)
                {
                    count_part++;
                }


            }

        }
        spiltpartcount.add(""+count_part);


    }
    private static int countAlphabets(String text) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
    private static void extractFromRow() {
        for (int i =0; i < extractRowList.size(); i++) {
            String tasktargetrow = extractRowList.get(i);
            if(tasktargetrow.length()<4)
            {
                extractRowList.remove(i);
            }
            else{

                extractbySpaceAndStoredinArray(tasktargetrow);
            }



        }
        //checking ":"
        for (int i =0; i < extractSpaceList.size(); i++) {
            String targetword = extractSpaceList.get(i);
            checkingcolumn(targetword);
        }
        String timee= "";
        String specialCharacters = "!@#$%^&*()_+-=[]{}|;:,.<>?/";
        for (int i =0; i < dateList.size(); i++) {
            String kk = dateList.get(i);
            kk=replaced(kk);
            if(kk.length() > 0 && specialCharacters.contains(String.valueOf(kk.charAt(0))))
            {
                System.out.println("The first character is a special character");
                kk=kk.substring(1,kk.length());
            }
            int alphabets = countAlphabets(kk);
            if(alphabets<=1)
            {

            }
            else {
                if(Integer.parseInt(kk)>15)
                {
                    count15to31++;
                }
                else {
                    countzeroto15++;
                }
            }



        }
        if(countzeroto15>count15to31)
        {
            tracking = 1;
        }
        else {
            tracking = 0;
        }
        //
        for (int i =0; i < spiltpartcount.size(); i++) {
            String numbers=spiltpartcount.get(i);
            if(tracking==1)
            {
                String element = "" ;
                if(datestart<=9)
                {
                    element="0"+datestart;
                }
                else {
                    element = ""+datestart;
                }
                if(Integer.parseInt(numbers)<4)
                {
                    for(int j=0;j<Integer.parseInt(numbers);j++)
                    {
                        finaldatelist.add(element);
                        counting6++;
                    }
                    if(counting6>=6)
                    {
                        counting6 = 0;
                        datestart++;
                    }
                }
                else{
                    for(int j=0;j<Integer.parseInt(numbers);j++)
                    {
                        finaldatelist.add(element);
                    }
                    datestart++;
                }
            }
            else {

                String element = "" ;
                if(datestart15<=9)
                {
                    element="0"+datestart15;
                }
                else {
                    element = ""+datestart15;
                }
                if(Integer.parseInt(numbers)<4)
                {
                    for(int j=0;j<Integer.parseInt(numbers);j++)
                    {
                        finaldatelist.add(element);
                        counting6++;
                    }
                    if(counting6>=6)
                    {
                        counting6 = 0;
                        datestart15++;
                    }
                }
                else{
                    for(int j=0;j<Integer.parseInt(numbers);j++)
                    {
                        finaldatelist.add(element);
                    }
                    datestart15++;
                }


            }

        }
        //
        for (int i =0; i < finaldatelist.size(); i++) {
            String date = finaldatelist.get(i);
            String time= "";
            if(i<timeList.size())
            {
                time = timeList.get(i);
            }
            String replaceddate =  replacedDate(date);
            String kk = replaceddate+""+time;
            makeupwithpercentage(kk,i);

        }

        Log.e("eeee"+timeList.size(),""+finaldatelist.size());


    }
    public static List<String> extractTimeParts(String input) {
        List<String> result = new ArrayList<>();
        int firstColon = input.indexOf(':');
        int lastColon = input.lastIndexOf(':');

        if (firstColon != -1 && lastColon != -1 && firstColon != lastColon) {
            // Extract the first time part
            if (firstColon >= 2) {
                result.add(input.substring(firstColon - 2, firstColon + 3));
            }

            // Extract the second time part
            if (lastColon + 3 <= input.length() && lastColon - 2 >= 0) {
                result.add(input.substring(lastColon - 2, lastColon + 3));
            }
        }

        return result;
    }
    private static void checkingcolumn(String targetword) {
        if(targetword.length()>=6)
        {
            if (targetword.contains(":")) {
                int  colns = countColons(targetword);
                if(colns>1)
                {
                    List<String> timeParts = extractTimeParts(targetword);
                    String  dateword= targetword.substring(0,2);

                    for (String timePart : timeParts) {
                        timeList.add(timePart);
                        dateList.add(dateword);
                        System.out.println(timePart);
                    }
                }
                else {
                    int colonIndex = targetword.indexOf(":");
                    int prefix_start = colonIndex - 2;
                    int suffixend = colonIndex + 3;

                    String prefix = targetword.substring(prefix_start,colonIndex);
                    String suffiex="";
                    if (suffixend>targetword.length())
                    {
                        suffiex= targetword.substring(colonIndex,targetword.length());
                    }
                    else {
                        suffiex= targetword.substring(colonIndex,suffixend);
                    }
                    String timeword = prefix+""+suffiex;
                    //checking date
                    int total_length = targetword.length();
                    int time_length = timeword.length();
                    int gettings = total_length - time_length;
                    String dateword= targetword.substring(0,gettings);
                    //checking date
                    timeList.add(timeword);
                    dateList.add(dateword);

                    Log.e("HHHAAA"+targetword,""+dateword);
                }

            }
            else {
                String dateword= targetword.substring(0,2);
                String timeword = targetword.substring(2,targetword.length());
                timeList.add(timeword);
                dateList.add(dateword);
            }
        }
        else {
            String dateword= targetword.substring(0,2);
            String timeword = targetword.substring(2,targetword.length());
            timeList.add(timeword);
            dateList.add(dateword);
        }


    }
    private static int countColons(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == ':') {
                count++;
            }
        }
        return count;
    }

    private static void makeupwithpercentage(String word, int index) {
        int numbers = countNumbers(word);
        if(numbers>=6)
        {
            String percentages = word+"(100%)";
            finalresult.add(index,percentages);
        }
        else{
            String percentages = word+"(80%)";

            finalresult.add(index,percentages);
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
    public static List<String> rowmanagement(String primanytext) {
        List<String> extractTextList = new ArrayList<>();
        String[] rows = primanytext.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i].trim();
            if (row.length() > 3) {
                extractTextList.add(row);
            }
            Log.e("Row " + (i + 1), row);

        }
        return  extractTextList;

    }
    private static String replacedDate(String getText) {
        return getText

                .replace("İ", "1")
                .replace("p", "0")
                .replace("l", "1")
                .replace("|", "1")
                .replace("i", "1")
                .replace("*", ":")
                .replace("L", "1")
                .replace("l", "|")
                .replace("s", "5")
                .replace("S", "5")
                .replace("a", "8")
                .replace("A", "8")
                .replace("o", "0")
                .replace("O", "0")
                .replace("B", "3")
                .replace("b", "2")
                .replace("Þ", "2")
                .replace("P", "9")
                .replace("D", "0")
                .replace("$", "3")
                .replace(".", ":")
                .replace("\"", ":")
                .replace("(", "|")
                .replace(")", "|");
    }

    private static String replaced(String getText) {
        return  getText
                .replace("İ","1")
                .replace("i","1")
                .replace("*",":")
                .replace("L", "1")
                .replace("l", "|")
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
    }


}
