package com.example.aaaa;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Pair;

import com.example.aaaa.ImageSettings.Datamanagement;
import com.example.aaaa.ImageSettings.DateManagement;
import com.example.aaaa.ImageSettings.FirstTimeManagement;
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

        Bitmap bitmap = bitmapw;
        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String getfromextractapi,List<String>xlist,List<String>ylist) {
                getfromextractapi=textClassificationManager.replaced(getfromextractapi);
                List<Map<String, List<String>>> groupsList = textClassificationManager.rowManagement(getfromextractapi);
               groupsList=datamanagement.datamanagementfromrationxandy(groupsList,ylist);

                Log.e("timelistResult",""+groupsList);
                if(groupsList.size()>0)
                {
                    List<String> finaltimelist = firstTimeManagement.managementTime(groupsList);
                    Log.e("finalResultFromSDK",""+finaltimelist);
                    List<String> datelist =    secondDateManagement.managementdate(groupsList);
                    List<String> finalresultwithpercentages   = textClassificationManager.makefinallistfordateandTime(datelist,finaltimelist);
                    List<String> finalResultFromSDK   = textClassificationManager.determinepercentages(finalresultwithpercentages);
                    Log.e("datelist",""+finalResultFromSDK);
                    //FirstPartTime
                   /*
                    Pair<Map<String, List<String>>, Map<String, List<String>>> resultTime = timeDataManagement.createDateAndTimeGroup(groupsList);
                    Map<String, List<String>> timelistResult = resultTime.second;
                    Log.e("timelistResult",""+timelistResult);
                    */
                    //secondpart date
                  //  Pair<Map<String, List<String>>, Map<String, List<String>>> resultDate= dateManagement.createDateAndTimeGroup(groupsList);
                   // Map<String, List<String>> datelistResult = resultDate.first;
//
                   // datelistResult=dateManagement.margeDateList(datelistResult);
                  //  datelistResult=dateManagement.replacedAndMakeDateClear(datelistResult);
                    //List<String> flattenedList   = dateManagement.Dateconverttolist(datelistResult);
                    //nt detector =  dateManagement.determinecount(flattenedList);
                    //datelistResult=textClassificationManager.replacedDate(datelistResult,detector);
                    //create list for max and second max value
                    //List<String> maxvaluelist=dateManagement.makeMaxStringList(datelistResult);
                    /*
                    datelistResult = DateManagement.updateMapValues(datelistResult,detector);
                    flattenedList   = dateManagement.Dateconverttolist(datelistResult);
                    flattenedList=dateManagement.sortDateListAscending(flattenedList);
                     */
                    /*
                    datelistResult=dateManagement.sortByKey(datelistResult);
                    List<String> finalDateList   = dateManagement.Dateconverttolist(datelistResult);
                    finalDateList=dateManagement.sortDateListAscending(finalDateList);
                     */



                 //   Log.e("datelistResult",""+datelistResult);
                  /*

                    List<Map<String, List<String>>> finaltimetList = timeDataManagement.convertMapToList(timelistResult);
                    timelistResult=timeDataManagement.removenulldatafromtimelist(finaltimetList);
                    timelistResult=timeDataManagement.sortByKey(timelistResult);
                    List<String> finaltimelist   = timeDataManagement.Dateconverttolist(timelistResult);
                    finaltimelist   = timeDataManagement.replacedTime(finaltimelist);
                    finaltimelist   = timeDataManagement.checkdoublecolonondata(finaltimelist);
                    finaltimelist   = timeDataManagement.replacedbyTime60(finaltimelist);

                    Log.e("timelistResult",""+finaltimelist);
                    Log.e("finalResultFromSDK",""+finaltimelist);
                   */

                    /*
                    Pair<Map<String, List<String>>, Map<String, List<String>>> result = textClassificationManager.createDateAndTimeGroup(groupsList);
                    Map<String, List<String>> datelistResult = result.first;
                    Map<String, List<String>> timelistResult = result.second;

                    List<Map<String, List<String>>> finaltimetList = textClassificationManager.convertMapToList(timelistResult);
                     timelistResult=textClassificationManager.removenulldatafromtimelist(finaltimetList);
                     timelistResult=textClassificationManager.sortByKey(timelistResult);

                    List<String> finaltimelist   = textClassificationManager.Dateconverttolist(timelistResult);
                    finaltimelist   = textClassificationManager.replacedTime(finaltimelist);
                    finaltimelist   = textClassificationManager.checkdoublecolonondata(finaltimelist);
                    finaltimelist   = textClassificationManager.replacedbyTime60(finaltimelist);

                    //date

                    datelistResult=textClassificationManager.margeDateList(datelistResult);
                    datelistResult=textClassificationManager.replacedAndMakeDateClear(datelistResult);
                    List<String> flattenedList   = textClassificationManager.Dateconverttolist(datelistResult);
                    int detector =  textClassificationManager.determinecount(flattenedList);
                  datelistResult=textClassificationManager.replacedDate(datelistResult,detector);
                    datelistResult=textClassificationManager.sortByKey(datelistResult);
                    List<String> finalDateList   = textClassificationManager.Dateconverttolist(datelistResult);
                   List<String> finalresultwithpercentages   = textClassificationManager.makefinallistfordateandTime(finalDateList,finaltimelist);
                   List<String> finalResultFromSDK   = textClassificationManager.determinepercentages(finalresultwithpercentages);

                    successCallback.onSuccess(finalResultFromSDK);
                  Log.e("finalResultFromSDK",""+flattenedList);
                     */




                }


            }

            @Override
            public void onError(String errorMessage) {
                failureCallback.onFailure(errorMessage);
            }
        });
    }


}
