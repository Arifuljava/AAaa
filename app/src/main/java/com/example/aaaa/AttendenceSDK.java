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

public class AttendenceSDK {
    public interface SuccessCallback {
        void onSuccess(List<String> processedTextList);
    }

    public interface FailureCallback {
        void onFailure(String e);
    }
    static TextRecognitionManager textRecognitionManager ;
    static  TextClassificationManager  textClassificationManager;
    static TimeDataManagement timeDataManagement;
    static Datamanagement datamanagement;
    static DateManagement dateManagement;
    static FirstTimeManagement firstTimeManagement;
    static SecondDateManagement secondDateManagement;
    static MargedateAndTime margedateAndTime;
    static CheckinTwoRationmanagement checkinTwoRationmanagement;
    public  static  int count_index = 0 ;
    public  static  void SendBitmap(Bitmap bitmapw, Context context,
                                    AttendenceSDK.SuccessCallback successCallback, AttendenceSDK.FailureCallback failureCallback) {
        count_index = 0;
        textRecognitionManager = new TextRecognitionManager();
        textClassificationManager = new TextClassificationManager();
        datamanagement=new Datamanagement();
        timeDataManagement=new TimeDataManagement();
        dateManagement=new DateManagement();
        firstTimeManagement=new FirstTimeManagement();
        secondDateManagement=new SecondDateManagement();
        margedateAndTime=new MargedateAndTime();
        checkinTwoRationmanagement=new CheckinTwoRationmanagement();

        Bitmap bitmap = bitmapw;
        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String getfromextractapi,List<String>xlist,List<String>ylist) {
                getfromextractapi=textClassificationManager.replaced(getfromextractapi);
                List<Map<String, List<String>>> groupsList = textClassificationManager.rowManagement(getfromextractapi);
                List<String> listfromx=checkinTwoRationmanagement.checkingxandy(groupsList,ylist);
               /*
                List<String> listfromy=checkinTwoRationmanagement.checkingxandy(groupsList,ylist);
                if(listfromx.size()>listfromy.size())
                {

                }
                else{

                }
                */
                   /*
                    groupsList=datamanagement.datamanagementfromrationxandy(groupsList,ylist);
                if(groupsList.size()>0)
                {
                    List<String> finaltimelist = firstTimeManagement.managementTime(groupsList);
                    Log.e("finalResultFromSDK",""+finaltimelist);
                   // List<String> datelist =    secondDateManagement.managementdate(groupsList);
                   // List<String> finalResultFromSDK=   margedateAndTime.dateandtimemarge(finaltimelist,datelist);

                    //Log.e("datelist",""+finalResultFromSDK);

                }
                    */


            }

            @Override
            public void onError(String errorMessage) {
                failureCallback.onFailure(errorMessage);
            }
        });
    }


}
