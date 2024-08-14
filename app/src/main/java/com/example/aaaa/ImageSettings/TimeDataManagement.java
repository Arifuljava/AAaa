package com.example.aaaa.ImageSettings;

import android.util.Log;
import android.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDataManagement {
    public  static  String first = "07";
    public  static  String second = "12";
    public  static  String third = "12";
    public  static  String fourth = "16";
    public  static  String fifth = "16";
    public  static  String sizth = "19";
    public static Pair<Map<String, List<String>>, Map<String, List<String>>> createDateAndTimeGroup(List<Map<String, List<String>>> groupsList) {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        Map<String, List<String>> timelistgroup = new HashMap<>();
        //time

        if (groupsList != null) {
            for (int i = 0; i < groupsList.size(); i++) {
                Map<String, List<String>> currentMap = groupsList.get(i);
                if (currentMap != null) {
                    List<String> targetList = currentMap.get("" + i);
                    if (targetList != null && !targetList.isEmpty()) {
                        List<String> formattedList = formatTimes(targetList);
                        timelistgroup.put("" + i, formattedList);
                    }
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
                    List<String> timeParts = extractTimeParts(item);
                    for (String timePart : timeParts) {
                        word = extractDtae(timePart);

                        word=replaceddata(word);
                        formattedList.add(word);
                    }
                    /*
                    int  colns = countColons(item);
                    Log.e("WWWWWW",""+colns);
                    if(colns>1)
                    {
                        List<String> timeParts = extractTimeParts(item);
                        for (String timePart : timeParts) {
                            word = extractDtae(timePart);

                            word=replaceddata(word);
                            formattedList.add(word);
                        }

                    }
                    else{
                        word = extractDtae(item);
                        word=replaceddata(word);
                        formattedList.add(word);
                    }
                     */


                }
                else {

                    int  numbers =countNumbers(item);
                    if (numbers>=6)
                    {
                        if(numbers==6)
                        {
                            String seconditem = "";
                            if(i>0)
                            {
                                seconditem = originalList.get(i-1);
                            }
                            else{
                                seconditem="50";
                            }


                            word =splitIntoPairs(item,seconditem);
                            word=replaceddata(word);
                            formattedList.add(word);
                        }
                        else{
                            String seconditem = "";
                            if(i>0)
                            {
                                seconditem = originalList.get(i-1);
                            }
                            else{
                                seconditem="50";
                            }
                            word =splitIntoPairs(item,seconditem);
                            word=replaceddata(word);
                            formattedList.add(word);

                        }
                    }
                    else if (numbers==5)
                    {
                        String seconditem = "";
                        if(i>0)
                        {
                            seconditem = originalList.get(i-1);
                        }
                        else{
                            seconditem="50";
                        }
                        word =splitIntoPairs(item,seconditem);
                        word=replaceddata(word);
                        formattedList.add(word);
                    }
                    else if (numbers<5){
                        word =item;
                        formattedList.add(word);
                    }

                }

            }

        }
        Log.e("formatTimes",""+formattedList);
        return formattedList;
    }
    public static String splitIntoPairs(String input, String previousdata) {
        String word = " ";
        List<String> parts = new ArrayList<>();
        List<String> previousdataparts = new ArrayList<>();

        for (int j = 0; j < input.length(); j += 2) {
            parts.add(input.substring(j, Math.min(j + 2, input.length())));
        }

        for (int j = 0; j < previousdata.length(); j += 2) {
            previousdataparts.add(previousdata.substring(j, Math.min(j + 2, previousdata.length())));
        }

        String suffix = "";
        String prefix = "00"; // Default value if not enough parts are available

        if (parts.size() > 1) {
            suffix = parts.size() > 1 ? parts.get(1) : "00"; // Safeguard for index 1
        }
        if (parts.size() > 2) {
            prefix = parts.get(2); // Safeguard for index 2
            if (previousdataparts.size() > 1) {
                String previousmin = previousdataparts.get(1);
                if (prefix.length() < 2) {
                    if (Integer.parseInt(previousmin) > 30) {
                        prefix = "5" + prefix;
                    } else {
                        prefix = "0" + prefix;
                    }
                }
            }
        }

        word = suffix + ":" + prefix;
        return word;
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
    public static String replaceddata(String targettext)
    {
        return  targettext
                .replace("į","1")
                .replace("|","1")
                .replace("þ","1")
                .replace("?","2")
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
                .replace(")","1")
                .replace(",","0")
                .replace("'","1");
    }
    public static int countNumbers(String text) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
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
        extractDate=replaceddata(extractDate);
        return extractDate;
    }
    public static List<String> extractTimeParts(String input) {

        List<String> result = new ArrayList<>();
        String regex = "(\\d{1,2}):(\\d{1,2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String leftPart = matcher.group(1);
            String rightPart = matcher.group(2);
            if (leftPart.length() == 1) {
                leftPart = "0" + leftPart;
            }
            if (rightPart.length() == 1) {
                rightPart = "0" + rightPart;
            }

            result.add(leftPart + ":" + rightPart);
        }

        return result;
    }
    public static  int countDigitsAfterColon(String input) {
        int colonIndex = input.indexOf(":");
        if (colonIndex == -1) {
            return 0;
        }
        String afterColon = input.substring(colonIndex + 1);
        return afterColon.replaceAll("\\D", "").length();
    }
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

        if (groupsList != null) {
            for (int i = 0; i < groupsList.size(); i++) {
                Map<String, List<String>> map = groupsList.get(i);

                if (map != null) {
                    // Using an index-based loop for iterating over the map entries
                    List<String> mapKeys = new ArrayList<>(map.keySet());
                    for (int j = 0; j < mapKeys.size(); j++) {
                        String key = mapKeys.get(j);
                        List<String> values = map.get(key);

                        if (values != null) {
                            List<String> trackinglist = trackinglist(values);
                            timelistgroup.put(key, trackinglist);
                            // Log.e("HHJJ", "" + trackinglist);
                        }
                    }
                }
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
    public static Map<String, List<String>> sortByKey(Map<String, List<String>> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys, (key1, key2) -> Integer.compare(Integer.parseInt(key1), Integer.parseInt(key2)));
        Map<String, List<String>> sortedMap = new LinkedHashMap<>();

// Using a for loop with index-based access
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            sortedMap.put(key, map.get(key));
        }

        return sortedMap;
    }
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
                if(word.contains("9999"))
                {
                    word=word ;
                }
                else{
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


            }


            replacedlist.add(i,word);

        }
        return  replacedlist;
    }
    public    List<String> Dateconverttolist( Map<String, List<String>> datelistResult)
    {
        List<String> flattenedList = new ArrayList<>();
        for (List<String> list : datelistResult.values()) {
            flattenedList.addAll(list);
        }
        return  flattenedList;
    }
    public    List<String> checkdoublecolonondata( List<String>  finaltimelist)
    {
        List<String> replacedlist = new ArrayList<>();
        for(int  i=0;i<finaltimelist.size();i++)
        {
            String word= finaltimelist.get(i);
            if(word.contains(":"))
            {
                int colns = countColons(word);
                if(colns>1)
                {
                    int  index = i%6;
                    word=replacedWith(index,i);
                    word=replaceddata(word);
                }
                else{
                    char targetChar = ':';
                    int count = countCharsBefore(word,targetChar);
                    if(count>2)
                    {
                        Log.e("Targettt",""+count);
                        int  index = i%6;
                        word=replacedWith(index,i);
                        word=replaceddata(word);
                    }
                    else{
                        if(count==2)
                        {
                            word=replaceddata(word);
                        }
                        else {
                            int  index = i%6;
                            word=replacedWith(index,i);
                            word=replaceddata(word);
                        }

                    }

                }

            }
            else {
                int  numbers =countNumbers(word);
                if(numbers<4)
                {
                    int  index = i%6;
                    word=replacedWith(index,i);
                    word=replaceddata(word);
                }
                else{
                    word=replaceddata(word);
                }


            }



            replacedlist.add(i,word);

        }
        return  replacedlist;
    }
    public static  String replacedWith(int index,int i)
    {
        String word = "";
        if (index==0)
        {
            int randomNumber = getRandomNumberInRange(50, 59);
            String data = first+":"+randomNumber;
            word=data;
        }
        else if (index==1)
        {
            int randomNumber = getRandomNumberInRange(1, 9);
            String data = second+":0"+randomNumber;
            word=data;
        }
        else if (index==2)
        {
            int randomNumber = getRandomNumberInRange(40, 59);
            String data = third+":"+randomNumber;
            word=data;
        }
        else if (index==3)
        {
            int randomNumber = getRandomNumberInRange(30, 35);
            String data = fourth+":"+randomNumber;
            word=data;
        }
        else if (index==4)
        {
            int randomNumber = getRandomNumberInRange(53, 59);
            String data = fifth+":"+randomNumber;
            word=data;
        }
        else if (index==5)
        {
            int randomNumber = getRandomNumberInRange(1, 9);
            String data = sizth+":0"+randomNumber;
            word=data;
        }
        word=replaceddata(word);
        return word;
    }
    private static int getRandomNumberInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
    public static int countCharsBefore(String str, char targetChar) {
        int index = str.indexOf(targetChar);
        if (index != -1) {
            return index;
        } else {
            return -1;
        }
    }
    public    List<String> replacedbyTime60( List<String>  finaltimelist)
    {
        List<String> replacedlist = new ArrayList<>();
        for(int  i=0;i<finaltimelist.size();i++) {
            String word = finaltimelist.get(i);
            if (word.contains(":")) {
                String[] parts = word.split(":");
                String minutes = parts[1];
                if(minutes.length()>2)
                {
                    minutes=minutes.substring(0,2);
                }
                else{
                    minutes=minutes;
                }
                Log.e("HHHAAAA",""+minutes);
                if (containsSpecialCharacter(minutes)) {
                    int  index = i%6;
                    word=replacedWith(index,i);
                    word=replaceddata(word);
                }
                else  if(Integer.parseInt(minutes)>59)
                {
                    int  index = i%6;
                    word=replacedWith(index,i);
                    word=replaceddata(word);
                }
                else{
                    word=replaceddata(word);
                }
            }
            else {
                word=replaceddata(word);
            }
            replacedlist.add(i,word);
        }
        return replacedlist;
    }
    public static boolean containsSpecialCharacter(String str) {
        String regex = "[^a-zA-Z0-9 ]"; // Matches anything that's not a letter, digit, or space
        return str.matches(".*" + regex + ".*");
    }
    public static List<String> createdatamanagement(List<String> userdatagiven, List<String> datalist){

        List<String> createrangelist =createrange(userdatagiven);
        Log.e("createrangelist",""+createrangelist);
        List<String> updatetimelist = new ArrayList<>();
        for(int ll =0;ll<userdatagiven.size();ll++)
        {
            updatetimelist.add("9999");
        }
        for (int k=0;k<datalist.size();k++) {
            String data = datalist.get(k);
            data=replaceddata(data);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                Date dataTime = sdf.parse(data);
                long checkingTime = dataTime.getTime();
                Log.e("checkingTime : "+data,""+checkingTime);


                for (int i = 0; i < createrangelist.size(); i++) {
                    long rangeTime = Long.parseLong(createrangelist.get(i));

                    if (checkingTime < rangeTime) {
                        if(updatetimelist.get(i).equals("9999"))
                        {
                            updatetimelist.set(i, data);
                        }
                        else {
                            updatetimelist.set(i+1, data);
                        }

                        Log.e("Data : " + (i + 1), "" + data);
                        break;
                    }

                    // If checkingTime is larger than all range times, set it to the last entry
                    if (i == createrangelist.size() - 1 && checkingTime >= rangeTime) {
                        if (updatetimelist.get(i).equals("9999")) {
                            updatetimelist.set(i, data); // Set the current index with the data
                        }
                        else{
                            updatetimelist.set(i+1, data);
                        }
                        Log.e("Data : " + (i + 1), "" + data);
                    }
                }

                Log.e("updatetimelist", "" + updatetimelist);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

return updatetimelist;
    }
    public  static  List<String> createrange( List<String> userdatagiven){
        List<String> createrangelist=new ArrayList<>();
        for (int i = 0; i < userdatagiven.size()-1; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date time1 = sdf.parse(userdatagiven.get(i));
                Date time2 = sdf.parse(userdatagiven.get(i + 1));
                Log.e(""+userdatagiven.get(i),""+time1.getTime());
                long midpointMillis =time1.getTime() + (time2.getTime() - time1.getTime()) / 2;
                createrangelist.add(""+midpointMillis);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return createrangelist;

    }
    public static Map<String, List<String>> checkingmisspunch(Map<String, List<String>> inputMap,List<String> timelistfromuser)
    {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        if (inputMap != null) {
            for (int i = 0; i < inputMap.size(); i++) {
                List<String> valueList = inputMap.get("" + i);
              List<String> vvvvv=createdatamanagement(timelistfromuser,valueList);
                Log.e(""+i,""+vvvvv);
                datelistrroup.put(""+i,vvvvv);
                //break;

            }
        }
        return datelistrroup;
    }
}
