package com.example.aaaa;

import static com.example.aaaa.AttendenceSDK.count_index;


import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextClassificationManager {
    public static String replaceddata(String targettext)
    {
        return  targettext
                .replace("%","3")
                .replace("p","2")
                .replace("h","1")
                .replace("I","1")
                .replace("İ","1")
                .replace("i","1")
                .replace("*","1")
                .replace("L", "1")
                .replace("l", "1")
                .replace("s", "5")
                .replace("S", "5")
                .replace("a", "8")
                .replace("A", "8")
                .replace("o","0")
                .replace("O","0")
                .replace("B","3")
                .replace("b","2")
                .replace("Þ","2")
                .replace("P","9")
                .replace("D","0")
                .replace("$","3")
                .replace(".","2")
                .replace("\"", "1")
                .replace("(","1")
                .replace(")","1");
    }
    public  String replaced(String  targettext)
    {
        return  targettext
                .replace("İ","1")
                .replace("i","1")
                .replace("*",":")
                .replace("L", "1")
                .replace("l", "|")
                .replace("s", "5")
                .replace("S", "5")
                .replace("a", "8")
                .replace("A", "8")
                .replace("o","0")
                .replace("O","0")
                .replace("B","3")
                .replace("b","2")
                .replace("Þ","2")
                .replace("P","9")
                .replace("D","0")
                .replace("$","3")
                .replace(".",":")
                .replace("\"", ":")
                .replace("(","|")
                .replace(")","|");
    }
    public  List<Map<String, List<String>>> rowManagement(String primaryText) {
        List<Map<String, List<String>>> groupsList = new ArrayList<>();
        String[] rows = primaryText.split("\n");
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i].trim();
            if (row.length() > 3) {

                Map<String, List<String>> group = createGroup(row, count_index);
               // System.out.println("Geroup " + (i + 1) + ": " + group);
                groupsList.add(group);
                count_index++;
            }
           // System.out.println("Row " + (i + 1) + ": " + row);
        }
       // System.out.println("Row " +groupsList);
        return groupsList;
    }
    public static Map<String, List<String>> createGroup(String row,int index) {
        String groupNumber ="";
        List<String> dataPoints = new ArrayList<>();
        if (row.contains(" ")) {
            String[] parts = row.split(" ");
            /*
            row = "1 "+row;
            String[] parts = row.split(" ");
            String  data="";

            for (int i = 1; i < parts.length; i++) {
                String  words = parts[i];

                dataPoints.add(words);
            }
             */
            String lastYearMonth = "";

            for (String item : parts) {

                if (item.matches("\\d{4}:")) {
                    lastYearMonth = item; // Update last year-month
                }
                else if (item.matches("\\d{2}")) {
                    if (!lastYearMonth.isEmpty()) {
                        dataPoints.add(lastYearMonth + item);
                    } else {
                        dataPoints.add(item);
                    }
                }
                else {
                    dataPoints.add(item);
                }

            }
        }
        else {
            groupNumber = row;
            dataPoints.add(groupNumber);

        }

Log.e("parts ",""+dataPoints);
        Map<String, List<String>> group = new HashMap<>();
        group.put(""+index, dataPoints);
        return group;
    }
    public static Pair<Map<String, List<String>>, Map<String, List<String>>> createDateAndTimeGroup(List<Map<String, List<String>>> groupsList) {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        Map<String, List<String>> timelistgroup = new HashMap<>();
        
        for (int i = 0; i < groupsList.size(); i++) {
            if(i<groupsList.size())
            {
                List<String> targetList = groupsList.get(i).get(""+i);

                if(targetList.size()>0&&targetList!=null)
                {
                    Pair<List<String>, List<String>> result = createFromEachTarget(targetList);

                    List<String> dateList_final = result.first;
                    List<String> timeList_final = result.second;
                    datelistrroup.put(""+i, dateList_final);
                    //timelistgroup.put(""+i, timeList_final);
                }

            }
        }
        //time

        for (int i = 0; i < groupsList.size(); i++) {
            if(i<groupsList.size())
            {
                List<String> targetList = groupsList.get(i).get(""+i);

                if(targetList.size()>0&&targetList!=null)
                {
                    List<String> formattedList = formatTimes(targetList);
                    timelistgroup.put(""+i, formattedList);
                }
            }
        }
        //time


        return new Pair<>(datelistrroup, timelistgroup);

    }
    public static List<String> formatTimes(List<String> originalList) {
        List<String> formattedList = new ArrayList<>();

        for (int i = 0; i < originalList.size(); i++) {
            String item = originalList.get(i);
            if(item.length()>2)
            {  String word ="";
                if(item.contains(":"))
                {
                     word = extractDtae(item);
                }
                else {

                   int  numbers =countNumbers(item);
                   if (numbers>=6)
                   {
                       if(numbers==6)
                       {
word =splitIntoPairs(item);
                       }
                       else{
                           word =splitIntoPairs(item);

                       }
                   }
                   else if (numbers==5)
                   {
                       word =splitIntoPairs(item);
                   }

                }
                formattedList.add(word);
            }

        }
Log.e("KKKKKK",""+formattedList);
        return formattedList;
    }
    public static  String splitIntoPairs(String input) {
        String word = " ";
        List<String> parts = new ArrayList<>();
        for (int j = 0; j < input.length(); j += 2) {
            parts.add(input.substring(j, Math.min(j + 2, input.length())));
        }
        String suffix = "";
        String prefix = "";
        if (parts.size()>2)
        {
            suffix=parts.get(1);
            prefix=parts.get(2);
            if(prefix.length()<2)
            {
                prefix="0"+prefix;
            }
        }
        else{
            suffix=parts.get(1);
            prefix="00";
        }
        word=suffix+":"+prefix;
        return word;

    }
    private static String extractDtae(String targetword) {
        String extractDate ="";
        if (targetword.length()>=7)
        {
            if(targetword.length()==7)
            {
                if (targetword.matches("\\d{4}:\\d{2}")) {
                    extractDate = targetword.substring(2);
                }
                else{
                    extractDate = targetword.substring(2);
                }
            }
            else if(targetword.length()>=7)
            {
                if (targetword.matches("\\d{4}:\\d{2}")) {
                    extractDate = targetword.substring(3);
                }
                else{
                    extractDate = targetword.substring(3);
                }
            }

        }
       else if (targetword.length()==6)
        {
            int count = countDigitsAfterColon(targetword);

            if(count>1)
            {
                extractDate = targetword.substring(1);
            }
            else {
                Log.e("KKIIIII"+targetword,""+count);
                extractDate = targetword.substring(2);
                extractDate=extractDate+"0";
            }


        }
        else {
            extractDate = targetword.substring(0);
        }
        return extractDate;
    }
    public static  int countDigitsAfterColon(String input) {
        int colonIndex = input.indexOf(":");
        if (colonIndex == -1) {
            return 0;
        }
        String afterColon = input.substring(colonIndex + 1);
        return afterColon.replaceAll("\\D", "").length();
    }
    private static int countColons(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == ':') {
                count++;
            }
        }
        return count;
    }
    public static List<String> extractTimeParts(String input) {
        List<String> result = new ArrayList<>();
        int firstColon = input.indexOf(':');
        int lastColon = input.lastIndexOf(':');
        if (firstColon != -1 && lastColon != -1 && firstColon != lastColon) {
            if (firstColon >= 2) {
                result.add(input.substring(firstColon - 2, firstColon + 3));
            }
            if (lastColon + 3 <= input.length() && lastColon - 2 >= 0) {
                result.add(input.substring(lastColon - 2, lastColon + 3));
            }
        }

        return result;
    }
    private static Pair<List<String>, List<String>> checkingcolumn(String targetword) {
        List<String> dateList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();

        if(targetword.length()>=6) {
            if (targetword.contains(":")) {
                int  colns = countColons(targetword);
                if(colns>1)
                {
                    List<String> timeParts = extractTimeParts(targetword);
                    String  dateword= targetword.substring(0,2);
                    for (String timePart : timeParts) {
                        timePart=replaceddata(timePart);
                        timeList.add(timePart);
                        dateList.add(dateword);
                    }
                }
                else {
                    int colonIndex = targetword.indexOf(":");
                    int prefix_start = colonIndex - 2;
                    int suffixend = colonIndex + 3;

                    String prefix = targetword.substring(prefix_start,colonIndex);
                    String suffiex="";
                    if (suffixend>targetword.length())
                    {
                        suffiex= targetword.substring(colonIndex,targetword.length());
                    }
                    else {
                        suffiex= targetword.substring(colonIndex,suffixend);
                    }
                    String timeword = prefix+""+suffiex;
                    //checking date
                    int total_length = targetword.length();
                    int time_length = timeword.length();
                    int gettings = total_length - time_length;
                    String dateword= targetword.substring(0,gettings);
                    //checking date
                    timeword=replaceddata(timeword);
                    timeList.add(timeword);
                    dateList.add(dateword);

                }

            }
            else{
                String dateword= targetword.substring(0,2);
                String timeword = targetword.substring(2,targetword.length());
                timeword=replaceddata(timeword);
                timeList.add(timeword);
                dateList.add(dateword);
            }

        }
        else {
            if(targetword.length()>=2)
            {
                String dateword= targetword.substring(0,2);
                String timeword = targetword.substring(2,targetword.length());
                timeword=replaceddata(timeword);
                timeList.add(timeword);
                dateList.add(dateword);
            }

        }
        return new Pair<>(dateList, timeList);
        }
    private static Pair<List<String>, List<String>> createFromEachTarget(List<String> targetList) {
        List<String> dateList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        for (int i =0; i < targetList.size(); i++) {
            String targetword = targetList.get(i);
            Pair<List<String>, List<String>> result = checkingcolumn(targetword);

            List<String> dateList_target = result.first;
            List<String> timeList_target = result.second;
            for (String timePart : timeList_target) {
                timeList.add(timePart);
            }
            for (String datePart : dateList_target) {
                dateList.add(datePart);
            }


        }


        //System.out.println("HHHHHNNNNN" + timeList);

        return new Pair<>(dateList, timeList);
    }
    //remove null data
    public static List<Map<String, List<String>>> convertMapToList(Map<String, List<String>> map) {
        List<Map<String, List<String>>> list = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            Map<String, List<String>> singleEntryMap = new HashMap<>();
            singleEntryMap.put(entry.getKey(), entry.getValue());
            list.add(singleEntryMap);
        }

        return list;
    }
    public static  Map<String, List<String>>  removenulldatafromtimelist(List<Map<String, List<String>>> groupsList) {
        Map<String, List<String>> timelistgroup = new HashMap<>();

        for (Map<String, List<String>> map : groupsList) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                List<String> trackinglist = trackinglist(values);
                timelistgroup.put(key,trackinglist);
              //  Log.e("HHJJ",""+trackinglist);
            }
        }
        return  timelistgroup;

    }
    private static List<String> trackinglist(List<String> targetList) {
        List<String> timeList = new ArrayList<>();
        for (int i =0; i < targetList.size(); i++) {
            String targetword = targetList.get(i);
            if(targetword.length()==0 || targetword==null || targetword==""|| targetword.contains(" ")|| targetword==" ")
            {

            }
            else{
                timeList.add(targetword);
            }


        }


        System.out.println("HHHHHNNNNN" + timeList);

        return  timeList;
    }
    //remove null data
    //margeGeroup

    public   Map<String, List<String>> margeDateList( Map<String, List<String>> datelistResult)
    {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        List<String> datechecking = new ArrayList<>();
        int aaaa=0;

        for (int i = 0; i < datelistResult.size(); i++) {
            String key = String.valueOf(i);
            List<String> dates = datelistResult.get(key);

            if(dates.size()>=6)
            {
                if(datechecking.size()<6)
                {
                    int  last = i-1;
                    System.out.println("Targetjj " + datechecking.size());
                    System.out.println("Targetjj" + dates);

                    if(dates.size()>=6)
                    {
                        if(datechecking.size()<6)
                        {
                            if(datechecking.size()==0 || datechecking==null )
                            {

                            }
                            else {
                                int kk = aaaa ;
                                datelistrroup.put("" + kk, new ArrayList<>(datechecking));
                                aaaa++;
                                System.out.println("TargetMMAAA " + datelistrroup);

                                datechecking.clear();
                            }

                            //
                            datelistrroup.put("" + aaaa, new ArrayList<>(dates));
                            System.out.println("Target " + datelistrroup);
                            aaaa++;
                            //
                        }
                    }
                }
            }
            else {
                datechecking = checkingtwoelement(datechecking,dates);

                if(datechecking.size()>=6)
                {
                    System.out.println("Target " + datechecking.size());

                    datelistrroup.put("" + aaaa, new ArrayList<>(datechecking));
                    System.out.println("Target " + datelistrroup);
                    aaaa++;
                    datechecking.clear();

                }
            }
        }
        return  datelistrroup;
    }
    public static List<String>  checkingtwoelement(List<String> list1, List<String> list2) {
        List<String> datechecking = new ArrayList<>();
        if(list1.size()==0 || list1==null)
        {
            datechecking = list2;
        }
        else {
            datechecking=containsAllElements(list1,list2);
        }
        return datechecking;
    }


    public static List<String>  containsAllElements(List<String> list1, List<String> list2) {
        List<String> datechecking = new ArrayList<>();
        datechecking = list1;
        for(int i=0;i<list2.size();i++)
        {
            String  word = list2.get(i);
            datechecking.add(word);
        }
        return datechecking;
    }
    //marge
    //replaced
    public   Map<String, List<String>> replacedAndMakeDateClear( Map<String, List<String>> datelistResult) {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        List<String> datechecking = new ArrayList<>();
        int aaaa = 0;

        for (int i = 0; i < datelistResult.size(); i++) {
            String key = String.valueOf(i);
            List<String> dates = datelistResult.get(key);
            datechecking = replacedandlast2(dates);
            datelistrroup.put("" + i, new ArrayList<>(datechecking));
        }
        return  datelistrroup;

    }
    public static List<String>  replacedandlast2(List<String> dates) {
        List<String> datechecking = new ArrayList<>();
        if(dates.size()==0 || dates==null)
        {
            datechecking = dates;
        }
        else {
            datechecking=returnmodifylistafterreplaced(dates);
        }
        return datechecking;
    }
    public static List<String>  returnmodifylistafterreplaced(List<String> dates) {
        List<String> datechecking = new ArrayList<>();

        for(int i=0;i<dates.size();i++)
        {
            String  word = dates.get(i);
            if(word.length()>2)
            {
                word=word.substring(1,word.length());
            }
            else {
                word=word;
            }
            word=replaceddata(word);
            word=removeSpecialCharactersAndSpaces(word);
            datechecking.add(word);
        }
        return datechecking;
    }
    public static String removeSpecialCharactersAndSpaces(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.replaceAll("[^0-9]", "");
    }
    //replaced

    public    List<String> Dateconverttolist( Map<String, List<String>> datelistResult)
    {
        List<String> flattenedList = new ArrayList<>();
        for (List<String> list : datelistResult.values()) {
            flattenedList.addAll(list);
        }
        return  flattenedList;
    }

    public    int determinecount(  List<String> datelistResult)
    {
         int countzeroto15 = 0;
         int count15to31 = 0 ;
       int detector = 0;
        countzeroto15 = 0;
        count15to31 = 0 ;

        for (int i =0; i < datelistResult.size(); i++) {
            String kk = datelistResult.get(i);
            if(!TextUtils.isEmpty(kk))
            {
                if(Integer.parseInt(kk)>15)
                {
                    count15to31++;
                }
                else {
                    countzeroto15++;
                }
            }
        }
        if(countzeroto15>count15to31)
        {
            detector = 1;
        }
        else {
            detector = 16;
        }

        return  detector;
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
    public   Map<String, List<String>> replacedDate( Map<String, List<String>> datelistResult,int detector) {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        int kk = detector;
        List<String> datechecking = new ArrayList<>();
        for (int i = 0; i < datelistResult.size(); i++) {
            String key = String.valueOf(i);
            List<String> dates = datelistResult.get(key);
            datechecking = replacedwithcount(dates,kk);
            datelistrroup.put("" + i, new ArrayList<>(datechecking));
            kk++;
        }

        return  datelistrroup;
    }
    public static List<String>  replacedwithcount(List<String> dates,int detector) {
        List<String> datechecking = new ArrayList<>();
        if(dates.size()==0 || dates==null)
        {
            datechecking = dates;
        }
        else {
            int size= dates.size();
            if(size>6)
            {
                size = 6 ;
            }
            String element ="";
            if(detector>9)
            {
                element=""+detector;
            }
            else {
                element="0"+detector;
            }
            for(int i=0;i<size;i++)
            {
                datechecking.add(element);
            }
        }
        return datechecking;
    }
    //sort
    public static Map<String, List<String>> sortByKey(Map<String, List<String>> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys, (key1, key2) -> Integer.compare(Integer.parseInt(key1), Integer.parseInt(key2)));
        Map<String, List<String>> sortedMap = new LinkedHashMap<>();
        for (String key : keys) {
            sortedMap.put(key, map.get(key));
        }
        return sortedMap;
    }
    //sort
    //make final list
    public static  List<String> makefinallistfordateandTime( List<String> finalDateList,List<String> finaltimelist)
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
    //make final list
//make percentages
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
    //make percentages
//
    public    List<String> replacedTime( List<String>  finaltimelist)
    {
        List<String> replacedlist = new ArrayList<>();
       for(int  i=0;i<finaltimelist.size();i++)
       {
           String word= finaltimelist.get(i);
           if(word.contains(":"))
           {
               word=replaceddata(word);
           }
           else {
               String firsttwo = word.substring(0,2);
               if(word.length()<3)
               {
                   String second=firsttwo;
                   word=firsttwo ;
               }
               else {
                   String second=word.substring(3,word.length());
                   word=firsttwo+""+second;
               }

           }


           replacedlist.add(i,word);

       }
        return  replacedlist;
    }
    //


}
