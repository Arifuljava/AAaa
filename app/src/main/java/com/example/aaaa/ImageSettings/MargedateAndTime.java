package com.example.aaaa.ImageSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MargedateAndTime {
    public  static   List<String> dateandtimemarge(List<String> finaltimelist ,List<String> datelist){
        List<String> finalResultFromSDK= new ArrayList<>();
        if(!finaltimelist.isEmpty()){
          List<String> finalresultwithpercentages   = makefinallistfordateandTime(datelist,finaltimelist);
            finalResultFromSDK   = determinepercentages(finalresultwithpercentages);
        }
        return finalResultFromSDK ;

    }

    public static List<String> makefinallistfordateandTime(List<String> finalDateList, List<String> finaltimelist)
    {
        List<String> finalresultlist = new ArrayList<>();
        int size = 0;
        int sizedate = finalDateList.size();
        int sizetime = finaltimelist.size();
        if(sizetime==sizedate)
        {
            size=sizedate;
        }
        else {
            if(sizetime>sizedate)
            {
                int less = sizetime-sizedate;
                sizetime=sizetime-less;
                size=sizetime;
            }
            else {
                int less = sizedate-sizetime;
                sizedate=sizedate-less;
                size=sizedate;
            }
        }
        for(int i=0;i<size;i++)
        {
            String date = finalDateList.get(i);
            String time = finaltimelist.get(i);
            String createnew=date+""+time;
            finalresultlist.add(createnew);
        }

        return finalresultlist;
    }
    public static  List<String> determinepercentages( List<String> finalresultwithpercentages)
    {
        List<String> finalresultlist = new ArrayList<>();
        for(int i=0;i<finalresultwithpercentages.size();i++)
        {
            String datass = finalresultwithpercentages.get(i);
            int numbers = countNumbers(datass);
            if(numbers==6)
            {
                String percentages = datass+"(100%)";
                finalresultlist.add(i,percentages);
            }
            else   if(numbers>6)
            {
                String percentages = datass+"(80%)";
                finalresultlist.add(i,percentages);
            }
            else{
                String percentages = datass+"(80%)";

                finalresultlist.add(i,percentages);
            }
        }
        return  finalresultlist;
    }
    private static int countNumbers(String text) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
