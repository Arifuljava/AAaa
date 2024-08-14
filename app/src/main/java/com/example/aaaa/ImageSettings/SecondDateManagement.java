package com.example.aaaa.ImageSettings;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondDateManagement {
    static DateManagement dateManagement;
    public  static   List<String> managementdate(List<Map<String, List<String>>> groupsList,List<String> userdatagiven){
        List<String> datelist = new ArrayList<>();
        if(datelist!=null)
        {
            datelist.clear();
        }
        if(!groupsList.isEmpty())
        {
            dateManagement=new DateManagement();
            Pair<Map<String, List<String>>, Map<String, List<String>>> resultDate = null;
            if (resultDate != null) {
                if (resultDate.first != null) {
                    resultDate.first.clear();
                }
                if (resultDate.second != null) {
                    resultDate.second.clear();
                }
                resultDate = null;
            }
            resultDate= dateManagement.createDateAndTimeGroup(groupsList);
            Map<String, List<String>> datelistResult=new HashMap<>();
            if(datelistResult!=null)
            {
                datelistResult.clear();
            }
            datelistResult = resultDate.first;
            datelistResult=dateManagement.sortByKey(datelistResult);
            datelistResult=dateManagement.margeDateList(datelistResult);
            datelistResult=dateManagement.sortByKey(datelistResult);
            datelistResult=dateManagement.replacedAndMakeDateClear(datelistResult);
            datelistResult=dateManagement.sortByKey(datelistResult);

            List<String> flatteneddateList= new ArrayList<>();
            if(flatteneddateList!=null)
            {
                flatteneddateList.clear();
            }
            flatteneddateList   = dateManagement.Dateconverttolist(datelistResult);
            int detector =  dateManagement.determinecount(flatteneddateList);
            datelistResult=dateManagement.replacedDate(datelistResult,detector,userdatagiven.size());
            datelistResult=dateManagement.sortByKey(datelistResult);
            datelist=dateManagement.Dateconverttolist(datelistResult);;
            Log.e("JJJJJJJ",""+datelist);

        }
        return datelist;

    }
}