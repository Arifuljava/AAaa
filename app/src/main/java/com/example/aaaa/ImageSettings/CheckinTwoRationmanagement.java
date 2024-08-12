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
            List<String> finaltimelist = firstTimeManagement.managementTime(groupsList);
            Log.e("finalResultFromSDK",""+finaltimelist);
            List<String> datelist =    secondDateManagement.managementdate(groupsList);
           finalResultFromSDK=   margedateAndTime.dateandtimemarge(finaltimelist,datelist);
        }
        return finalResultFromSDK;
    }
}
