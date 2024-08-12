package com.example.aaaa.ImageSettings;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckinTwoRationmanagement {
    static  Datamanagement datamanagement;
    static FirstTimeManagement firstTimeManagement;
    static SecondDateManagement secondDateManagement;
    static MargedateAndTime margedateAndTime;
    public  static    List<String> checkingxandy( List<Map<String, List<String>>> groupsList,List<String>ylist){
        datamanagement=new Datamanagement();
        firstTimeManagement=new FirstTimeManagement();
        secondDateManagement=new SecondDateManagement();
        margedateAndTime=new MargedateAndTime();
        List<String> finalResultFromSDK=new ArrayList<>();

        groupsList=datamanagement.datamanagementfromrationxandy(groupsList,ylist);
        if(groupsList.size()>0)
        {
            List<String> userdatagiven = new ArrayList<>();
            userdatagiven.add("08:00");
            userdatagiven.add("12:00");
            userdatagiven.add("13:00");
            userdatagiven.add("17:00");
            userdatagiven.add("18:00");
            userdatagiven.add("20:00");
            //List<String> finaltimelist = firstTimeManagement.managementTime(groupsList,userdatagiven);
            //Log.e("finalResultFromSDK",""+finaltimelist);
            List<String> datelist =    secondDateManagement.managementdate(groupsList,userdatagiven);
         //  finalResultFromSDK=   margedateAndTime.dateandtimemarge(finaltimelist,datelist);
        }
        return finalResultFromSDK;
    }
}
