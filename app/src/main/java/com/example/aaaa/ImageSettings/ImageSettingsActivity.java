package com.example.aaaa.ImageSettings;

import static com.example.aaaa.AttendenceSDK.count_index;
import static com.example.aaaa.ImageSettings.TimeDataManagement.countDigitsAfterColon;
import static com.example.aaaa.ImageSettings.TimeDataManagement.countNumbers;
import static com.example.aaaa.ImageSettings.TimeDataManagement.replaceddata;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aaaa.AttendenceSDK;
import com.example.aaaa.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageSettingsActivity extends AppCompatActivity {
    ImageBitmapManager imageBitmapManager;
    TextView resulttext;
    ImageView realimage;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_settings);
        imageBitmapManager=new ImageBitmapManager();
        uiinit();

    }

    private void uiinit() {
        resulttext = findViewById(R.id.resulttext);
        realimage = findViewById(R.id.realimage);
        try {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imagemissing);
            /*
            bitmap=imageBitmapManager.make90degress(bitmap);
            bitmap=imageBitmapManager.toGrayscale(bitmap);
            bitmap=imageBitmapManager.toBloodBlack(bitmap);
            bitmap=imageBitmapManager.zoomInBitmap(bitmap);
             */
            bitmap = rotateBitmap(bitmap, 90);
            realimage.setImageBitmap(bitmap);
        }catch (Exception exception)
        {

        }
    }
    public  static  List<String> createrange( List<String> userdatagiven){
        List<String> createrangelist=new ArrayList<>();
        for (int i = 0; i < userdatagiven.size()-1; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date time1 = sdf.parse(userdatagiven.get(i));
                Date time2 = sdf.parse(userdatagiven.get(i + 1));
                Log.e(""+userdatagiven.get(i),""+time1.getTime());
               long midpointMillis =time1.getTime() + (time2.getTime() - time1.getTime()) / 2;
               createrangelist.add(""+midpointMillis);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return createrangelist;

    }
    public void createdatamanagement(){
        List<String> userdatagiven = new ArrayList<>();
        userdatagiven.add("08:00");
        userdatagiven.add("12:00");
        userdatagiven.add("13:00");
        userdatagiven.add("17:00");
       userdatagiven.add("18:00");
       userdatagiven.add("20:00");
        List<String> createrangelist =createrange(userdatagiven);
        Log.e("createrangelist",""+createrangelist);
        List<String> datalist = new ArrayList<>();
        List<String> updatetimelist = new ArrayList<>();
        for(int ll =0;ll<userdatagiven.size();ll++)
        {
            updatetimelist.add("9999");
        }
        for (int k=0;k<datalist.size();k++) {
            String data = datalist.get(k);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                Date dataTime = sdf.parse(data);
                long checkingTime = dataTime.getTime();
                Log.e("checkingTime : "+data,""+checkingTime);


                for (int i = 0; i < createrangelist.size(); i++) {
                    long rangeTime = Long.parseLong(createrangelist.get(i));

                    if (checkingTime < rangeTime) {
                        updatetimelist.set(i, data);
                        Log.e("Data : " + (i + 1), "" + data);
                        break;
                    }

                    // If checkingTime is larger than all range times, set it to the last entry
                    if (i == createrangelist.size() - 1 && checkingTime >= rangeTime) {
                        updatetimelist.set(i + 1, data);
                        Log.e("Data : " + (i + 2), "" + data);
                    }
                }

                Log.e("updatetimelist", "" + updatetimelist);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        /*
        int timelength = userdatagiven.size();
        List<String> datalist = new ArrayList<>();
        datalist.add("12:04");
        datalist.add("12:55");
        datalist.add("17:04");
        datalist.add("17:57");
        datalist.add("20:07");
      //  datalist.add("16:04");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        List<String> result = new ArrayList<>();//String data : datalist
        for (int k=0;k<datalist.size();k++) {
            String data=datalist.get(k);
            boolean added = false;
            for (int i = 0; i < userdatagiven.size()-1; i++) {
                try {
                    Date time1 = sdf.parse(userdatagiven.get(i));
                    Date time2 = sdf.parse(userdatagiven.get(i + 1));
                    Date dataTime = sdf.parse(data);
                    long midpointMillis =time1.getTime() + (time1.getTime() + time2.getTime()) / 2;
                    long midcheckingdate = dataTime.getTime();
                    if(midcheckingdate<midpointMillis)
                    {
                        Log.e("Get",""+data);
                        break;
                    }
                    else{
                        Log.e("Missing",""+userdatagiven.get(i));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
         */

    }
    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
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

String data = "1612:29161258";
    public static List<String> extractwhencolon1(String data) {
        // Splitting the string into two parts using the colon as a delimiter
        String[] parts = data.split(":");
        String part1 = parts[0];
        String part2 = parts[1];
        String time1 = part1.substring(2, 4) + ":" + part1.substring(4, 6);
        String time2 = part2.substring(0, 2) + ":" + part2.substring(2, 4);
        List<String> result = new ArrayList<>();
        result.add(time1);
        result.add(time2);
        return result;
    }
    public static List<String> extractTimeStrings(String input) {
        List<String> result = new ArrayList<>();
        String regex = "(\\d{1,2}):(\\d{1,2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String leftPart = matcher.group(1);
            String rightPart = matcher.group(2);
            if (leftPart.length() == 1) {
                leftPart = "0" + leftPart;
            }
            if (rightPart.length() == 1) {
                rightPart = "0" + rightPart;
            }

            result.add(leftPart + ":" + rightPart);
        }

        return result;
    }
    public static  boolean areInSameRow(List<Integer> numbers, int num1, int num2, int rowDifference) {
        int minNumber = Collections.min(numbers);
        int maxNumber = Collections.max(numbers);

        int currentRowStart = minNumber;
        while (currentRowStart <= maxNumber) {
            int currentRowEnd = currentRowStart + rowDifference - 1;
            if (num1 >= currentRowStart && num1 <= currentRowEnd &&
                    num2 >= currentRowStart && num2 <= currentRowEnd) {
                return true;
            }
            currentRowStart += rowDifference;
        }
        return false;
    }
    private  String extractDtae(String targetword) {
        String extractDate ="";
        if (targetword.length()>=7)
        {
            if(targetword.length()==7)
            {
                if (targetword.matches("\\d{4}:\\d{2}")) {
                    extractDate = targetword.substring(2);
                }
                else{
                    extractDate = targetword.substring(2);
                }
            }
            else if(targetword.length()>=7)
            {
                if (targetword.matches("\\d{4}:\\d{2}")) {
                    extractDate = targetword.substring(3);
                }
                else{
                    extractDate = targetword.substring(3);
                }
            }

        }
        else if (targetword.length()==6)
        {
            int count = countDigitsAfterColon(targetword);

            if(count>1)
            {
                extractDate = targetword.substring(1);
            }
            else {
                Log.e("KKIIIII"+targetword,""+count);
                extractDate = targetword.substring(2);
                extractDate=extractDate+"0";
            }


        }
        else {
            extractDate = targetword.substring(0);
        }
        extractDate=replaceddata(extractDate);
        return extractDate;
    }
    public  List<String> formatTimes(List<String> originalList) {
        List<String> formattedList = new ArrayList<>();

        for (int i = 0; i < originalList.size(); i++) {
            String item = originalList.get(i);
            Log.e("item",""+item);
            if(item.length()>2)
            {  String word ="";
                if(item.contains(":"))
                {
                    List<String> timeParts = extractTimeParts(item);
                    for (String timePart : timeParts) {
                        word = extractDtae(timePart);

                        word=replaceddata(word);
                        formattedList.add(word);
                    }
                    /*
                    int  colns = countColons(item);
                    Log.e("WWWWWW",""+colns);
                    if(colns>1)
                    {
                        List<String> timeParts = extractTimeParts(item);
                        for (String timePart : timeParts) {
                            word = extractDtae(timePart);

                            word=replaceddata(word);
                            formattedList.add(word);
                        }

                    }
                    else{
                        word = extractDtae(item);
                        word=replaceddata(word);
                        formattedList.add(word);
                    }
                     */


                }
                else {

                    int  numbers =countNumbers(item);
                    if (numbers>=6)
                    {
                        if(numbers==6)
                        {
                            String seconditem = "";
                            if(i>0)
                            {
                                seconditem = originalList.get(i-1);
                            }
                            else{
                                seconditem="50";
                            }


                            word =splitIntoPairs(item,seconditem);
                            word=replaceddata(word);
                            formattedList.add(word);
                        }
                        else{
                            String seconditem = "";
                            if(i>0)
                            {
                                seconditem = originalList.get(i-1);
                            }
                            else{
                                seconditem="50";
                            }
                            word =splitIntoPairs(item,seconditem);
                            word=replaceddata(word);
                            formattedList.add(word);

                        }
                    }
                    else if (numbers==5)
                    {
                        String seconditem = "";
                        if(i>0)
                        {
                            seconditem = originalList.get(i-1);
                        }
                        else{
                            seconditem="50";
                        }
                        //word =splitIntoPairs(item,seconditem);
                        String kk = item.substring(3,item.length());
                        word=item.substring(1,3)+":"+kk;

                        word=replaceddata(word);
                        formattedList.add(word);
                    }
                    else if (numbers<5){
                        word =item;
                        formattedList.add(word);
                    }

                }

            }

        }
        Log.e("formatTimes",""+formattedList);
        return formattedList;
    }
    public  String splitIntoPairs(String input, String previousdata) {
        String word = " ";
        List<String> parts = new ArrayList<>();
        List<String> previousdataparts = new ArrayList<>();

        for (int j = 0; j < input.length(); j += 2) {
            parts.add(input.substring(j, Math.min(j + 2, input.length())));
        }

        for (int j = 0; j < previousdata.length(); j += 2) {
            previousdataparts.add(previousdata.substring(j, Math.min(j + 2, previousdata.length())));
        }

        String suffix = "";
        String prefix = "00"; // Default value if not enough parts are available

        if (parts.size() > 1) {
            suffix = parts.size() > 1 ? parts.get(1) : "00"; // Safeguard for index 1
        }
        if (parts.size() > 2) {
            prefix = parts.get(2); // Safeguard for index 2
            if (previousdataparts.size() > 1) {
                String previousmin = previousdataparts.get(1);
                if (prefix.length() < 2) {
                    previousmin=removeSpecialCharactersAndSpaces(previousmin);
                    if (Integer.parseInt(previousmin) > 30) {
                        prefix = "5" + prefix;
                    } else {
                        prefix = "0" + prefix;
                    }
                }
            }
        }

        word = suffix + ":" + prefix;
        return word;
    }
    public  String removeSpecialCharactersAndSpaces(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.replaceAll("[^0-9]", "");
    }
    public static List<String> formatTimes11(List<String> targetList) {
        List<String> formattedTimes = new ArrayList<>();

        for (String data : targetList) {
            StringBuilder timeBuilder = new StringBuilder();
            String[] times = data.split("\\\\");

            for (String time : times) {
                // Extract the last 5 characters (for XX:XX format)
                if (time.length() >= 5) {
                    time = time.substring(time.length() - 5);
                }

                // Ensure time has a colon and is in the correct format
                if (time.contains(":") && time.length() >= 5) {
                    timeBuilder.append(time).append(" ");
                }
                else{
                    timeBuilder.append(time).append(" ");
                }
            }

            // Add the formatted times to the list
            if (timeBuilder.length() > 0) {
                formattedTimes.add(timeBuilder.toString().trim());
            }
        }

        // Join all formatted times into a single string with spaces and return the list
        String result = String.join(" ", formattedTimes).replaceAll(" +", " ").trim();
        formattedTimes.clear();
        formattedTimes.add(result);
        return formattedTimes;
    }
    public  List<String> extractTimeParts(String input) {

        List<String> result = new ArrayList<>();
        String regex = "(\\d{1,2}):(\\d{1,2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String leftPart = matcher.group(1);
            String rightPart = matcher.group(2);
            if (leftPart.length() == 1) {
                leftPart = "0" + leftPart;
            }
            if (rightPart.length() == 1) {
                rightPart = "0" + rightPart;
            }

            result.add(leftPart + ":" + rightPart);
        }

        return result;
    }
    public void imagesettings(View view) {
        List<String>targetList=new ArrayList<>();
        targetList.add("10755");
        targetList.add("112:06");
        targetList.add("|1112:57");
        targetList.add("|1117:02");
        targetList.add("117:571120:07");
        List<String> formattedTimes = new ArrayList<>();

        List<String> formattedList = formatTimes(targetList);
        Log.e("formattedList", formattedList.toString());

/*

        AttendenceSDK.SendBitmap(bitmap, ImageSettingsActivity.this, new AttendenceSDK.SuccessCallback() {
            @Override
            public void onSuccess(List<String> processedTextList) {
                //Log.e("GetData",""+processedTextList);
            }
        }, new AttendenceSDK.FailureCallback() {
            @Override
            public void onFailure(String e) {
                Log.e("Failed","failed");

            }
        });

 */


    }
    private static String findSecondMostFrequentValue(List<String> list) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String value : list) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        if (sortedEntries.size() > 1) {
            return sortedEntries.get(1).getKey();
        }
        return sortedEntries.isEmpty() ? null : sortedEntries.get(0).getKey();
    }
    public static Map<String, List<String>> crerateMapusingSize(List<Integer> integerList, int detector) {
        Map<String, List<String>> xlistmap = new HashMap<>();
        int index = 0;
        int i = 0;
        while (i < integerList.size()) {
            List<String> currentGroup = new ArrayList<>();
            int currentValue = integerList.get(i);
            currentGroup.add("" + currentValue);
            while (i + 1 < integerList.size() && Math.abs(integerList.get(i + 1) - currentValue) < detector) {
                i++;
                currentValue = integerList.get(i);
                currentGroup.add("" + currentValue);
            }
            xlistmap.put("" + index, currentGroup);
            index++;
            i++;
        }

        return xlistmap;
    }

    public static Map<String, List<String>> createGroup(String row, int index) {
        String groupNumber ="";
        List<String> dataPoints = new ArrayList<>();
        List<String> finaldataPoints = new ArrayList<>();
        if (row.contains(" ")) {
            row="0 "+row;
            String[] parts = row.split(" ");


            String lastYearMonth = "";
            String checkingdata ="";
            for (int i = 1; i < parts.length; i++) {
                String  item = parts[i];
                if(item.length()>=6)
                {
                    if(i + 1 < parts.length)
                    {
                        String nextItem = parts[i + 1];
                        if (nextItem.length() < 6) {
                            item=item+""+nextItem;
                            i++;
                        } else {
                            item=item+"";
                        }
                    }
                    else{
                        item=item;
                    }


                    if (item.matches("\\d{4}:")) {
                        lastYearMonth = item; // Update last year-month
                    }
                    else if (item.matches("\\d{2}")) {
                        if (!lastYearMonth.isEmpty()) {
                            dataPoints.add(lastYearMonth + item);
                        } else {
                            dataPoints.add(item);
                        }
                    }
                    else {
                        dataPoints.add(item);
                    }
                }
                else {
                    if(i + 1 < parts.length)
                    {
                        String nextItem = parts[i + 1];

                        if (nextItem.length() < 6) {
                            dataPoints.add(item + nextItem);
                            i++; // Skip the next item as it's merged
                        } else {
                            dataPoints.add(item);
                        }
                    }
                    else {
                        dataPoints.add(item);
                    }


                }

            }



        }
        else {
            groupNumber = row;
            dataPoints.add(groupNumber);

        }
        for (String item : dataPoints) {
            if (!item.isEmpty()) {
                finaldataPoints.add(item);
            }
        }
        Log.e("parts ",""+finaldataPoints);
        Map<String, List<String>> group = new HashMap<>();
        group.put(""+index, finaldataPoints);
        return group;
    }
}