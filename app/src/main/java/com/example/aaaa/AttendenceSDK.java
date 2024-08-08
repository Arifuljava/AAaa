package com.example.aaaa;

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
    static TextRecognitionManager textRecognitionManager ;
    static  TextClassificationManager  textClassificationManager;
    public  static  int count_index = 0 ;
    public  static  void SendBitmap(Bitmap bitmapw, Context context,
                                    AttendenceSDK.SuccessCallback successCallback, AttendenceSDK.FailureCallback failureCallback) {
        count_index = 0;
        textRecognitionManager = new TextRecognitionManager();
        textClassificationManager = new TextClassificationManager();
        Bitmap bitmap = bitmapw;
        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String getfromextractapi) {
                getfromextractapi=textClassificationManager.replaced(getfromextractapi);
                Log.e("getfromextractapi",""+getfromextractapi);
                List<Map<String, List<String>>> groupsList = textClassificationManager.rowManagement(getfromextractapi);
                Log.e("getfromextractapi",""+groupsList);

                if(groupsList.size()>0)
                {
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
                   /*
                     Log.e("TimeList",""+finaltimelist);
                    Log.e("TimeList",""+finaltimelist.size());
                    */
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
                 //   System.out.println("detector : "+finalResultFromSDK);
                   // System.out.println("detector : "+detector);
                    int itemsPerRow = 6;

                    for (int i = 0; i < finalResultFromSDK.size(); i++) {
                        System.out.print(finalResultFromSDK.get(i) + "\t");

                        // Print a new line after every 6 items
                        if ((i + 1) % itemsPerRow == 0) {
                            System.out.println();  // Move to the next line
                        }
                    }
                    successCallback.onSuccess(finalResultFromSDK);



                }


            }

            @Override
            public void onError(String errorMessage) {
                failureCallback.onFailure(errorMessage);
            }
        });
    }


}
