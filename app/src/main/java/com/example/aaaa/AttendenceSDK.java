package com.example.aaaa;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import com.example.aaaa.ImageSettings.CheckinTwoRationmanagement;
import com.example.aaaa.ImageSettings.Datamanagement;
import com.example.aaaa.ImageSettings.DateManagement;
import com.example.aaaa.ImageSettings.FirstTimeManagement;
import com.example.aaaa.ImageSettings.MargedateAndTime;
import com.example.aaaa.ImageSettings.SecondDateManagement;
import com.example.aaaa.ImageSettings.TimeDataManagement;

import java.util.List;
import java.util.Map;



import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendenceSDK {
    public interface SuccessCallback {
        void onSuccess(List<String> processedTextList);
    }

    public interface FailureCallback {
        void onFailure(String e);
    }
    static  TextRecognitionManager textRecognitionManager ;
    static    TextClassificationManager  textClassificationManager;
    static  Datamanagement datamanagement;
    public  static  int count_index = 0 ;
    static TimeDataManagement timeDataManagement;
    static  FirstTimeManagement firstTimeManagement;
    static   SecondDateManagement secondDateManagement;
    static  CheckinTwoRationmanagement checkinTwoRationmanagement;
    public  static  List<String> datalistfromuser = new ArrayList<>();
    public static void SendBitmap(List<String> usergivendtaa, Bitmap bitmapw, Context context,
                                  SuccessCallback successCallback, FailureCallback failureCallback) {
        count_index = 0;
        textRecognitionManager = new TextRecognitionManager();
        textClassificationManager = new TextClassificationManager();
        datamanagement=new Datamanagement();
        timeDataManagement=new TimeDataManagement();
        firstTimeManagement=new FirstTimeManagement();
        secondDateManagement=new SecondDateManagement();
        checkinTwoRationmanagement=new CheckinTwoRationmanagement();
        datalistfromuser.clear();;
        Bitmap bitmap = bitmapw;
        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String getfromextractapi,List<String>xlist,List<String>ylist) {
                getfromextractapi=textClassificationManager.replaced(getfromextractapi);
                Log.e("getfromextractapi",""+getfromextractapi);
                List<Map<String, List<String>>> groupsList=new ArrayList<>();
                groupsList.clear();
                groupsList = textClassificationManager.rowManagement(getfromextractapi);
                Log.e("groupsList",""+groupsList);
                List<String> listfromx = new ArrayList<>();
                listfromx.clear();
                listfromx=checkinTwoRationmanagement.checkingxandy(groupsList,xlist,usergivendtaa);

                List<String> listfromy=new ArrayList<>();
                listfromy.clear();
                listfromy=checkinTwoRationmanagement.checkingxandy(groupsList,ylist,usergivendtaa);
                datalistfromuser=usergivendtaa;
                if(listfromx.size()>listfromy.size())
                {
                    int detector = firstTimeManagement.countspecificnumber(listfromx);
                    if(detector>3)
                    {
                        List<String> processedTextList =new ArrayList<>();
                        processedTextList.add("Please take image again");
                        successCallback.onSuccess(processedTextList);
                    }
                    else{
                        successCallback.onSuccess(listfromx);
                    }
                    Log.e("getfromextractapi",""+detector);

                }
                else{
                    int detector = firstTimeManagement.countspecificnumber(listfromy);
                    if(detector>3)
                    {
                        List<String> processedTextList =new ArrayList<>();
                        processedTextList.add("Please take image again");
                        successCallback.onSuccess(processedTextList);
                    }
                    else{
                        successCallback.onSuccess(listfromy);
                    }
                    Log.e("getfromextractapi",""+detector);

                }



            }

            @Override
            public void onError(String errorMessage) {
                failureCallback.onFailure(errorMessage);
            }
        });
    }


}


