package com.example.aaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ImageToTextActivity extends AppCompatActivity {
    TextView resulttext;
    ImageView realimage;
    Bitmap bitmap;
    TextRecognitionManager textRecognitionManager;
    List<String> extractRowList = new ArrayList<>();
    List<String> extractSpaceList = new ArrayList<>();
    List<String> spiltpartcount = new ArrayList<>();
    List<String> timeList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    List<String> finaldatelist = new ArrayList<>();
    List<String> finalresult = new ArrayList<>();
    String previousdata = "";
    int count_date = 0;
    private Matrix matrix_zoom;
    private float scale = 1f;
    private static final float ZOOM_IN_SCALE = 1.2f;
    private static final float ZOOM_OUT_SCALE = 0.8f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_text);
        init();
    }
    public Bitmap addWhiteBackground(Bitmap originalBitmap) {
        Bitmap whiteBackgroundBitmap = Bitmap.createBitmap(
                originalBitmap.getWidth(),
                originalBitmap.getHeight(),
                originalBitmap.getConfig()
        );
        Canvas canvas = new Canvas(whiteBackgroundBitmap);
        canvas.drawColor(Color.RED);
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        return whiteBackgroundBitmap;
    }
    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public static Bitmap toGrayscale1(Bitmap srcImage) {

        Bitmap bmpGrayscale = Bitmap.createBitmap(srcImage.getWidth(), srcImage.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmpGrayscale);
        Paint paint = new Paint();

        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(srcImage, 0, 0, paint);

        return bmpGrayscale;
    }
    public Bitmap zoomBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        // Create a new bitmap with the specified width and height.
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

        return scaledBitmap;
    }
    public void init() {
        resulttext = findViewById(R.id.resulttext);
        realimage = findViewById(R.id.realimage);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.realbitmap_blue);
      // bitmap= toGrayscale(bitmap);
        //bitmap=zoomBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight() * 2);
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            realimage.setImageBitmap(bitmap);
            textRecognitionManager = new TextRecognitionManager();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void enablebluetooth(View view) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        realimage.setColorFilter(filter);

     /*
        AttendenceSDK.SendBitmap(bitmap, ImageToTextActivity.this, new AttendenceSDK.SuccessCallback() {
            @Override
            public void onSuccess(List<String> processedTextList) {
                Log.e("Failed",""+processedTextList);
            }
        }, new AttendenceSDK.FailureCallback() {
            @Override
            public void onFailure(String e) {
                Log.e("Failed","failed");

            }
        });

      */


    }

    private void extractFromRow() {
        for (int i =0; i < extractRowList.size(); i++) {
            String tasktargetrow = extractRowList.get(i);
            if(tasktargetrow.length()<3)
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
            if(alphabets>=1)
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
            String kk = date+""+time;
            makeupwithpercentage(kk,i);

        }
        for (int i =0; i < finalresult.size(); i++) {
            String date = finalresult.get(i);
            timee=timee+" "+date;
        }
Log.e("eeee"+timeList.size(),""+finaldatelist.size());
        resulttext.setText(timee);

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
    private void makeupwithpercentage(String word, int index) {
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

    int counting6 = 0;
    int datestart15 = 16;
    int datestart = 1;
    int  tracking = 0 ;
    int countzeroto15 = 0;
    int count15to31 = 0 ;

    private static int countAlphabets(String text) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
    public static List<String> extractAllTimeParts(String input) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ':') {
                // Check if we have 2 digits before and 2 digits after the colon
                if (i >= 2 && i + 3 < input.length()) {
                    String potentialTime = input.substring(i - 2, i + 3);
                    if (isValidTimeFormat(potentialTime)) {
                        result.add(potentialTime);
                    }
                }
            }
        }

        return result;
    }

    private static boolean isValidTimeFormat(String time) {
        // Check if the time is in the format HH:MM
        // where HH is between 00 and 23, and MM is between 00 and 59
        if (time.length() != 5 || time.charAt(2) != ':') {
            return false;
        }

        try {
            int hours = Integer.parseInt(time.substring(0, 2));
            int minutes = Integer.parseInt(time.substring(3, 5));

            return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
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
    private void checkingcolumn(String targetword) {
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

    private void extractbySpaceAndStoredinArray(String rowselected) {
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

    private String replaced(String getText) {
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

    public List<String> rowmanagement(String primanytext) {
        List<String> extractTextList = new ArrayList<>();
        String[] rows = primanytext.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i].trim();
            if (row.length() > 2) {
                extractTextList.add(row);
            }
            Log.e("Row " + (i + 1), row);

        }
        return  extractTextList;

    }
}
/*

 */