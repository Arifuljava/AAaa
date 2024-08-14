package com.example.aaaa.ImageSettings;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckinTwoRationmanagement {
    static  Datamanagement datamanagement;
    static FirstTimeManagement firstTimeManagement;
    static SecondDateManagement secondDateManagement;
    static MargedateAndTime margedateAndTime;
    public      List<String> checkingxandy( List<Map<String, List<String>>> groupsList,List<String>ylist,List<String> usergivendtaa){
        datamanagement=new Datamanagement();
        firstTimeManagement=new FirstTimeManagement();
        secondDateManagement=new SecondDateManagement();
        margedateAndTime=new MargedateAndTime();
        List<String> finalResultFromSDK=new ArrayList<>();
        if(finalResultFromSDK!=null)
        {
            finalResultFromSDK.clear();
        }

        groupsList=datamanagement.datamanagementfromrationxandy(groupsList,ylist);
        if(groupsList.size()>0)
        {
            List<String> finaltimelist=new ArrayList<>();
            if(finaltimelist!=null)
            {
                finaltimelist.clear();
            }
            finaltimelist = firstTimeManagement.managementTime(groupsList,usergivendtaa);
            int detector = firstTimeManagement.countspecificnumber(finaltimelist);

            Log.e("detector",""+detector);
            List<String> datelist=new ArrayList<>();
            if(datelist!=null)
            {
                datelist.clear();
            }
            datelist =    secondDateManagement.managementdate(groupsList,usergivendtaa);
            finalResultFromSDK=   margedateAndTime.dateandtimemarge(finaltimelist,datelist);

        }
        return finalResultFromSDK;
    }
}
