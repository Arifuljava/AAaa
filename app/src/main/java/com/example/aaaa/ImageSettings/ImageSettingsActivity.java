package com.example.aaaa.ImageSettings;

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
import java.util.List;

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
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.realbitmap_blue);
            bitmap=imageBitmapManager.make90degress(bitmap);
            bitmap=imageBitmapManager.toGrayscale(bitmap);
            bitmap=imageBitmapManager.toBloodBlack(bitmap);
            bitmap=imageBitmapManager.zoomInBitmap(bitmap);
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
    public void imagesettings(View view) {
        int  colns = countColons(data);

        if(colns>1)
        {
            List<String> timeParts = extractTimeParts(data);
            Log.e("DDD",""+timeParts);
        }
        else{

        }

        AttendenceSDK.SendBitmap(bitmap, ImageSettingsActivity.this, new AttendenceSDK.SuccessCallback() {
            @Override
            public void onSuccess(List<String> processedTextList) {
                Log.e("GetData",""+processedTextList);
            }
        }, new AttendenceSDK.FailureCallback() {
            @Override
            public void onFailure(String e) {
                Log.e("Failed","failed");

            }
        });


    }
}