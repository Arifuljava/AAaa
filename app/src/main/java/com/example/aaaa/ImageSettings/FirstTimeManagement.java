package com.example.aaaa.ImageSettings;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstTimeManagement {
    static TimeDataManagement timeDataManagement;
    public   List<String> managementTime(List<Map<String, List<String>>> groupsList,List<String> timelistfromuser){
        timeDataManagement=new TimeDataManagement();
        List<String> finaltimelist = new ArrayList<>();
        if (finaltimelist != null) {
            finaltimelist.clear();
        }

        if(!groupsList.isEmpty())
        {
            Pair<Map<String, List<String>>, Map<String, List<String>>> resultTime = null;
            if (resultTime != null) {
                if (resultTime.first != null) {
                    resultTime.first.clear();
                }
                if (resultTime.second != null) {
                    resultTime.second.clear();
                }
                resultTime = null;
            }
            resultTime = timeDataManagement.createDateAndTimeGroup(groupsList);
            Map<String, List<String>> timelistResult=new HashMap<>();
            if (timelistResult != null) {
                timelistResult.clear();
            }
            timelistResult = resultTime.second;
            List<Map<String, List<String>>> finaltimetList=new ArrayList<>();
            if (finaltimetList != null) {
                finaltimetList.clear();
            }
            finaltimetList = timeDataManagement.convertMapToList(timelistResult);
            timelistResult=timeDataManagement.removenulldatafromtimelist(finaltimetList);
            timelistResult=timeDataManagement.sortByKey(timelistResult);
            List<String>timerangelist=timeDataManagement.createrange(timelistfromuser);
            timelistResult=timeDataManagement.checkingmisspunch(timelistResult,timerangelist);


            finaltimelist   = timeDataManagement.Dateconverttolist(timelistResult);
            finaltimelist   = timeDataManagement.replacedTime(finaltimelist);
            finaltimelist   = timeDataManagement.checkdoublecolonondata(finaltimelist);
            finaltimelist   = timeDataManagement.replacedbyTime60(finaltimelist);


            Log.e("timelistResult",""+finaltimelist);
        }
        return finaltimelist;
    }
    public  int countspecificnumber( List<String> finaltimelist){
        int count = 0;
        for(int i=0;i<finaltimelist.size();i++)
        {
            if(finaltimelist.get(i).contains("9999")){
                count++;
            }

        }
        return count;

    }
}
