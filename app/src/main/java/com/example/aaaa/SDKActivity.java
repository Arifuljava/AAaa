package com.example.aaaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SDKActivity extends AppCompatActivity {
    //SDK full code
    TextView resulttext;
    ImageView realimage;
    Bitmap bitmap;
    TextRecognitionManager textRecognitionManager;
    List<String> extractTextList = new ArrayList<>();
    List<String> finalextractTextList = new ArrayList<>();
    List<String> resultlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdkactivity);
        init();


    }

    public void init() {
        resulttext = findViewById(R.id.resulttext);
        realimage = findViewById(R.id.realimage);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jjj);
        try {
            realimage.setImageBitmap(bitmap);
            textRecognitionManager = new TextRecognitionManager();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void enablebluetooth(View view) {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jjj);
        MyJavaClass myJavaClass=new MyJavaClass();
        myJavaClass.SendBitmap(bitmap, SDKActivity.this, new MyJavaClass.SuccessCallback() {
            @Override
            public void onSuccess(List<String> processedTextList) {
                Log.e("Failed","Failed"+processedTextList);
            }
        }, new MyJavaClass.FailureCallback() {
            @Override
            public void onFailure(String e) {
                Log.e("Failed","Failed");
            }
        });
/*

        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String getText) {
                //
                getText =  getText
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

                //extreact primary text task
                extractTextList = rowmanagement(getText);

                //text management
                String  message = "";
                String tasktargetrow;
                for (int i =0; i < extractTextList.size(); i++) {
                    tasktargetrow = extractTextList.get(i);
                    message=message+"\n"+tasktargetrow;
                    resulttext.setText(message);
                    String data = processTextInEveryrowAndReturnNewListWithElement(tasktargetrow);
                    Log.e("Fullcode",""+tasktargetrow.length());
                    if(i==15)
                    {
                        break;
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

                resulttext.setText(meeee+"Array Size "+resultlist.size());


            }

            @Override
            public void onError(String errorMessage) {

            }
        });

 */
    }
    public  void determinePercentage(String word,int index){
        int alphabets = countAlphabets(word);
        int numbers = countNumbers(word);
        int specialCharacters = countSpecialCharacters(word);
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

    private void arrangementdata(String data,int index) {
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

    private void checking6value(String data,int index) {
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

            }
        }
        else {

            resultlist.add(data.trim());
        }
    }

    public void extract(String targetrow)
    {
        String[] stringArray = targetrow.split(" ");
        String tasktargetrow;
        for (int i =0; i < stringArray.length; i++) {
            tasktargetrow = stringArray[i];
            finalextractTextList.add(tasktargetrow);

        }
    }
    public String processTextInEveryrowAndReturnNewListWithElement(String targetrow) {
        String dataelement ="" ;
        extract(targetrow);

//deletingFirstTwoValue(targetrow);

        return dataelement;

    }
    public  void deletingFirstTwoValue(String targetRow)
    {
        int alphabets = countAlphabets(targetRow);
        int numbers = countNumbers(targetRow);
        int specialCharacters = countSpecialCharacters(targetRow);
        System.out.println("Numbers: " + numbers);
        System.out.println("specialCharacters: " + specialCharacters);
        System.out.println("alphabets: " + alphabets);
        if (numbers==36)
        {
            targetRow=removeallspace(targetRow);

            targetRow=removeSpecialCharactersAndSpaces(targetRow);
            targetRow= deletingFirstTwoValue(targetRow,(numbers+alphabets) - 36);
            String getID= targetRow.substring(0,1);
            if(Integer.parseInt(getID)>3)
            {
                targetRow=targetRow.substring(1,targetRow.length());
            }
            List<String> result = geteach6datafromeveryrow(targetRow, 6);

            String tasktargetrow;
            for (int i = 0; i < result.size(); i++) {
                tasktargetrow=result.get(i);
                String finalresultdata =makeupdata(tasktargetrow);
                finalextractTextList.add(finalresultdata);
                Log.e("KKK"+i,""+finalresultdata);
            }
            String showing="";
            for (int i = 0; i < finalextractTextList.size(); i++) {
                showing=showing+"  "+finalextractTextList.get(i);
            }


            resulttext.setText(showing);
        }
        else if (numbers>36)
        {


            targetRow=removeallspace(targetRow);
            targetRow=removeSpecialCharactersAndSpaces(targetRow);
            if(alphabets>0)
            {
                targetRow= deletingFirstTwoValue(targetRow,(numbers+alphabets) - 36);
            }
            else {
                targetRow= deletingFirstTwoValue(targetRow,(numbers+0) - 36);
            }


            targetRow=removeSpecialCharactersAndSpaces(targetRow);
            String getID= targetRow.substring(0,1);
            if(Integer.parseInt(getID)>3)
            {
                targetRow=targetRow.substring(1,targetRow.length());
            }
            List<String> result = geteach6datafromeveryrow(targetRow, 6);

            String tasktargetrow;
            for (int i = 0; i < result.size(); i++) {
            tasktargetrow=result.get(i);
               String finalresultdata =makeupdata(tasktargetrow);
               finalextractTextList.add(finalresultdata);
            Log.e("KKK"+i,""+finalresultdata);
            }
            String showing="";
            for (int i = 0; i < finalextractTextList.size(); i++) {
                showing=showing+"  "+finalextractTextList.get(i);
            }


            resulttext.setText(showing);



        }
        else if((numbers+alphabets) >= 36){
            targetRow=removeallspace(targetRow);
            targetRow=removeSpecialCharactersAndSpaces(targetRow);
            targetRow= deletingFirstTwoValue(targetRow,(numbers+alphabets) - 36);
            targetRow=removeSpecialCharactersAndSpaces(targetRow);
            String getID= targetRow.substring(0,1);
            if(Integer.parseInt(getID)>3)
            {
                targetRow=targetRow.substring(1,targetRow.length());
            }

            List<String> result = geteach6datafromeveryrow(targetRow, 6);
            String tasktargetrow;
            for (int i = 0; i < result.size(); i++) {
                tasktargetrow=result.get(i);
                String finalresultdata =makeupdata(tasktargetrow);
                finalextractTextList.add(finalresultdata);
                Log.e("KKK"+i,""+finalresultdata);
            }
            String showing="";
            for (int i = 0; i < finalextractTextList.size(); i++) {
                showing=showing+"  "+finalextractTextList.get(i);
            }

            resulttext.setText(showing);
        }

    }
    public  String makeupdata(String tobedone)
    {
        String finaldata= "";
        String substring1 = tobedone.substring(0, 4);
        String substring2 = ":";
        String substring3 = "";
        if (tobedone.length() >= 6) {
            substring3 = tobedone.substring(4, 6);
        }
        else {
            substring3 = tobedone.substring(4, tobedone.length());
        }

        finaldata = substring1+""+substring2+""+substring3;
        return   finaldata;

    }
    public  List<String> geteach6datafromeveryrow(String dataString, int length) {
        List<String> resultList = new ArrayList<>();

        String substring1 = dataString.substring(0, 6);


        String substring2 = dataString.substring(6, 12);
        String substring3 = dataString.substring(12, 18);
        String substring4 = dataString.substring(18, 24);
        String substring5 = dataString.substring(24, 30);
        String substring6 = "";
        if (dataString.length() >= 36) {
            substring6 = dataString.substring(30, 36);
        }
        else {
            substring6 = dataString.substring(30, dataString.length());
        }

        Log.e("OHHHHHHHHHHHH1",""+substring1);
        Log.e("OHHHHHHHHHHHH2",""+substring2);
        Log.e("OHHHHHHHHHHHH3",""+substring3);
        Log.e("OHHHHHHHHHHHH4",""+substring4);
        Log.e("OHHHHHHHHHHHH5",""+substring5);
        Log.e("OHHHHHHHHHHHH6",""+substring6);
        resultList.add(substring1);
        resultList.add(substring2);
        resultList.add(substring3);
        resultList.add(substring4);
        resultList.add(substring5);
        resultList.add(substring6);


        return resultList;
    }
    public static String removeallspace(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.replaceAll(" ", "");
    }
    public static String removeSpecialCharactersAndSpaces(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
    public String deletingFirstTwoValue(String targetRow,int indexDelete) {
        if (targetRow == null || targetRow.length() < 2) {
            return targetRow;
        }
        return targetRow.substring(indexDelete);
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
    private static int countNumbers(String text) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }


    private static int countSpecialCharacters(String text) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }


public List<String> rowmanagement(String primanytext) {
    List<String> extractTextList = new ArrayList<>();
    String[] rows = primanytext.split("\n");
    for (int i = 0; i < rows.length; i++) {
        String row = rows[i].trim();
        if (row.length() > 25) {
            extractTextList.add(row);
        }
        Log.e("Row " + (i + 1), row);

    }
    return  extractTextList;

}

}


