package com.example.aaaa.ImageSettings;

import static com.example.aaaa.AttendenceSDK.count_index;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aaaa.AttendenceSDK;
import com.example.aaaa.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
            realimage.setImageBitmap(bitmap);
        }catch (Exception exception)
        {

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
    public static List<String> extractTimeParts(String input) {
        List<String> result = new ArrayList<>();
        int firstColon = input.indexOf(':');
        int lastColon = input.lastIndexOf(':');
        if (firstColon != -1 && lastColon != -1 && firstColon != lastColon) {
            if (firstColon >= 2) {
                result.add(input.substring(firstColon - 2, firstColon + 3));
            }
            if (lastColon + 3 <= input.length() && lastColon - 2 >= 0) {
                result.add(input.substring(lastColon - 2, lastColon + 3));
            }
        }

        return result;
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
    public void imagesettings(View view) {


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