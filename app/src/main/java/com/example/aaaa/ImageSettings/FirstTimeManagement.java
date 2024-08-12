package com.example.aaaa.ImageSettings;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstTimeManagement {
    static TimeDataManagement timeDataManagement;
    public static  List<String> managementTime(List<Map<String, List<String>>> groupsList,List<String> timelistfromuser){
        timeDataManagement=new TimeDataManagement();
        List<String> finaltimelist = new ArrayList<>();
        if(!groupsList.isEmpty())
        {
            Pair<Map<String, List<String>>, Map<String, List<String>>> resultTime = timeDataManagement.createDateAndTimeGroup(groupsList);
            Map<String, List<String>> timelistResult = resultTime.second;

            List<Map<String, List<String>>> finaltimetList = timeDataManagement.convertMapToList(timelistResult);
            timelistResult=timeDataManagement.removenulldatafromtimelist(finaltimetList);
            timelistResult=timeDataManagement.sortByKey(timelistResult);
            timelistResult=timeDataManagement.checkingmisspunch(timelistResult,timelistfromuser);


           finaltimelist   = timeDataManagement.Dateconverttolist(timelistResult);
            finaltimelist   = timeDataManagement.replacedTime(finaltimelist);
          finaltimelist   = timeDataManagement.checkdoublecolonondata(finaltimelist);
           finaltimelist   = timeDataManagement.replacedbyTime60(finaltimelist);
            Log.e("timelistResult",""+finaltimelist);
        }
        return finaltimelist;
    }
}
