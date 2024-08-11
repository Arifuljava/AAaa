package com.example.aaaa.ImageSettings;

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

public class DateManagement {
    public static List<String> extractFirstTwoDigits(List<String> originalList) {
        List<String> extractedList = new ArrayList<>();

        for (String item : originalList) {
            // Remove special characters : and |
            String cleanedItem = item.replaceAll("[:|]", "");

            // Extract the first two digits
            String firstTwoDigits = cleanedItem.substring(0, 2);
            extractedList.add(firstTwoDigits);
        }

        return extractedList;
    }
    public static List<String> removeSpecialCharacters(List<String> originalList) {
        List<String> cleanedList = new ArrayList<>();

        // Regular expression to match special characters
        Pattern specialCharsPattern = Pattern.compile("[^a-zA-Z0-9\\s]"); // Keeps only alphanumeric and space characters

        for (String str : originalList) {
            // Remove special characters using regex
            String cleanedStr = specialCharsPattern.matcher(str).replaceAll("");
            cleanedList.add(cleanedStr);
        }

        return cleanedList;
    }
    public static Pair<Map<String, List<String>>, Map<String, List<String>>> createDateAndTimeGroup(List<Map<String, List<String>>> groupsList) {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        Map<String, List<String>> timelistgroup = new HashMap<>();
        if (groupsList != null) {
            for (int i = 0; i < groupsList.size(); i++) {
                List<String> valueList = groupsList.get(i).get(""+i);
                if(!valueList.isEmpty())
                {
                    Pair<List<String>, List<String>> result = createFromEachTarget(valueList);

                    List<String> dateList_final = result.first;
                    datelistrroup.put(""+i, dateList_final);
                }
            }
        }
        return new Pair<>(datelistrroup, timelistgroup);
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
                    String prefix = "";
                    if(prefix_start<0)
                    {
                        prefix = targetword.substring(0,colonIndex);
                    }
                    else{
                        prefix = targetword.substring(prefix_start,colonIndex);
                    }

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
    public static String replaceddata(String targettext)
    {
        return  targettext
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
                .replace(")","1");
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
    public   Map<String, List<String>> margeDateList( Map<String, List<String>> datelistResult)
    {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        List<String> datechecking = new ArrayList<>();
        int aaaa=0;

        for (int i = 0; i < datelistResult.size(); i++) {
            String key = String.valueOf(i);
            List<String> dates = datelistResult.get(key);
            System.out.println("AAAAAA " + datechecking.size());
            System.out.println("AAAAAA" + dates);
            if(dates.size()>=6)
            {
                if(datechecking.size()<6)
                {
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
                else if(i < datelistResult.size())
                {
                    if(datechecking.size()<6){
                        datelistrroup.put("" + aaaa, new ArrayList<>(datechecking));
                        System.out.println("Target " + datelistrroup);
                        aaaa++;
                        datechecking.clear();

                    }
                    System.out.println("Target " + datechecking);
                }

            }
        }
        return  datelistrroup;
    }
    public   Map<String, List<String>> replacedAndMakeDateClear( Map<String, List<String>> datelistResult) {
        Map<String, List<String>> datelistrroup = new HashMap<>();
        List<String> datechecking = new ArrayList<>();
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
    public    List<String> Dateconverttolist( Map<String, List<String>> datelistResult)
    {
        List<String> flattenedList = new ArrayList<>();
        for (List<String> list : datelistResult.values()) {
            flattenedList.addAll(list);
        }
        return  flattenedList;
    }
    public static List<List<String>> extractListsFromMap(Map<String, List<String>> datelistResult) {
        List<List<String>> extractedLists = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : datelistResult.entrySet()) {
            extractedLists.add(entry.getValue());
        }

        return extractedLists;
    }
    public static Map<String, List<String>> updateMapValues(Map<String, List<String>> map,int detector) {
        Map<String, List<String>> updatedMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> originalList = entry.getValue();

            // Find the most frequently occurring value
            String mostFrequentValue = findMostFrequentValue(originalList);

            // Get the frequency of the most frequent value
            int frequency = Collections.frequency(originalList, mostFrequentValue);
            Log.e("frequency",""+mostFrequentValue);
            List<String> updatedList = new ArrayList<>();
            int size=originalList.size();
            if(size>6)
            {
                size=6;
            }
            else if(size<3)
            {
                size=0;
            }


            if(size<3)
            {
                size=0;
            }
            else{
                if(Integer.parseInt(mostFrequentValue)<1)
                {
                    mostFrequentValue=  findSecondMostFrequentValue111(originalList);
                    Log.e("Failed","failed"+mostFrequentValue);
                }

                for(int i=0;i<size;i++)
                {
                    updatedList.add(mostFrequentValue);
                }
                updatedMap.put(entry.getKey(), updatedList);
            }

            Log.e("MakeUPP"+entry.getKey(),""+updatedMap);
        }

        return updatedMap;
    }
    private static String findSecondMostFrequentValue111(List<String> list) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String value : list) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        if (sortedEntries.size() > 1) {
            return sortedEntries.get(1).getKey();
        }
        return sortedEntries.isEmpty() ? null : sortedEntries.get(0).getKey();
    }
    public static String findMostFrequentValue(List<String> list) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String value : list) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        String mostFrequentValue = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentValue = entry.getKey();
            }
        }

        return mostFrequentValue;
    }
    public static Map<String, List<String>> sortByKey(Map<String, List<String>> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys, (key1, key2) -> Integer.compare(Integer.parseInt(key1), Integer.parseInt(key2)));
        Map<String, List<String>> sortedMap = new LinkedHashMap<>();
        for (String key : keys) {
            sortedMap.put(key, map.get(key));
        }
        return sortedMap;
    }
    /**
     * Sorts a list of strings in ascending order.
     *
     * @param dateList The list of strings to be sorted.
     * @return The sorted list.
     */
    public List<String> sortDateListAscending(List<String> dateList) {
        // Sort the list in ascending order
        Collections.sort(dateList);

        // Return the sorted list
        return dateList;
    }
    public List<String> removeZero(List<String> dateList) {
        for (int i = 0; i < dateList.size(); i++) {
            if (Integer.parseInt(dateList.get(i)) < 1) {
                dateList.remove(i);
            }
        }
        return dateList;
    }
    public   Map<String, List<String>> replacedDate( Map<String, List<String>> datelistResult,int detector) {

        Map<String, List<String>> datelistrroup = new HashMap<>();
        int kk = detector;
        List<String> datechecking = new ArrayList<>();
        int mainkey = 0;
        int i = 0;
        for (Map.Entry<String, List<String>> entry : datelistResult.entrySet()) {
            String key = entry.getKey();
            List<String> dates = entry.getValue();
            if (dates != null) {
                String element = "";
                if(kk<10)
                {
                    element="0"+kk;
                }
                else{
                    element=""+kk;
                }
                //Log.e("hhhhhh : "+element,""+dates);
                if(dates.contains(""+element))
                {
                    Log.e(kk+"gggggg : "+mainkey,""+dates);
                    datechecking = replacedwithcount(dates,kk);
                    datelistrroup.put("" + i, new ArrayList<>(datechecking));
                    mainkey++;
                }
                else{
                    Log.e(kk+"gggggg : "+dates,""+element);
                }
            }

            kk++;
            i++;

        }

      /*
        for (int i = 0; i < 15; i++) {
            String key = String.valueOf(mainkey);
            List<String> dates = datelistResult.get(key);
            Log.e("replacedDate",""+dates);
            if (dates != null) {
                String element = "";
                if(kk<10)
                {
                    element="0"+kk;
                }
                else{
                    element=""+kk;
                }
                //Log.e("hhhhhh : "+element,""+dates);
                if(dates.contains(""+element))
                {
                   Log.e(kk+"gggggg : "+mainkey,""+dates);
                     datechecking = replacedwithcount(dates,kk);
                    datelistrroup.put("" + i, new ArrayList<>(datechecking));
                    mainkey++;
                }
                else{

                }
            }

           kk++;
        }
       */


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
    public static List<String> makeMaxStringList(Map<String, List<String>> map) {
         List<String> updatedMap = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> originalList = entry.getValue();
            String mostFrequentValue = findMostFrequentValue(originalList);
            /*
            if (Integer.parseInt(mostFrequentValue) < 1) {
                mostFrequentValue = findSecondMostFrequentValue(originalList);
            }
             */
          updatedMap.add(mostFrequentValue);
            Log.e("MakeUPP"+entry.getKey(),""+updatedMap);
        }

        return updatedMap;
    }
    private static String findSecondMostFrequentValue(List<String> list) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Build frequency map
        for (String value : list) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        // Sort the frequency map by value in descending order
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        // Return the second most frequent value, if it exists
        if (sortedEntries.size() > 1) {
            return sortedEntries.get(1).getKey();
        }

        // If there is no second most frequent value, return the most frequent value
        return sortedEntries.isEmpty() ? null : sortedEntries.get(0).getKey();
    }
}
