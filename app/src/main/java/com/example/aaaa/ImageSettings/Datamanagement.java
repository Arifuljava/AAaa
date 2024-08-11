package com.example.aaaa.ImageSettings;

import static com.example.aaaa.ImageSettings.ImageSettingsActivity.areInSameRow;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class Datamanagement {
    //logic 8  management using x and y size
    public static    List<Map<String, List<String>>>  datamanagementfromrationxandy(List<Map<String, List<String>>> groupsList, List<String> xlist){

        List<Integer> integerList = convertStringListToIntegerList(xlist);
        integerList = sortListAscending(integerList);
        integerList=filterList(integerList,400);
        Map<String, List<String>> crerateMapusingSizeMap=crerateMapusingSize(integerList,80);
        crerateMapusingSizeMap= cearedatalistfromsize(crerateMapusingSizeMap,groupsList,xlist);
        List<Map<String, List<String>>> result = convertMapToList(crerateMapusingSizeMap);
        Log.e("integerList",""+integerList);
        Log.e("Sorted data",""+result);
        return result;
    }
    public static List<Integer> filterList(List<Integer> list, int maxDifference) {
        for (int i = 0; i < list.size()-1; i++) {
            int differ = list.get(i+1) - list.get(i);
            Log.e("differ",""+differ);
            if(differ>maxDifference)
            {
                list.remove(i);
            }
        }

        return list;
    }
    public static List<Map<String, List<String>>> convertMapToList(Map<String, List<String>> inputMap) {
        List<Map<String, List<String>>> listOfMaps = new ArrayList<>();
        if (inputMap != null) {
            for (int i = 0; i < inputMap.size(); i++) {
                List<String> valueList = inputMap.get("" + i);
                Map<String, List<String>> singleEntryMap = new HashMap<>();
                singleEntryMap.put(""+i, valueList);
                listOfMaps.add(singleEntryMap);
            }
        }
        /*
        for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
            Map<String, List<String>> singleEntryMap = new HashMap<>();
            singleEntryMap.put(entry.getKey(), entry.getValue());
            listOfMaps.add(singleEntryMap);
        }
         */

        return listOfMaps;
    }
    public static   Map<String, List<String>> cearedatalistfromsize( Map<String, List<String>> crerateMapusingSizeMap,List<Map<String, List<String>>> groupsList,List<String> xlist)
    {
        Map<String, List<String>> datalistmapp = new HashMap<>();
        int  index = 0;
        if (crerateMapusingSizeMap != null) {
            for (int i = 0; i < crerateMapusingSizeMap.size(); i++) {
                List<String> valueList = crerateMapusingSizeMap.get("" + i);
                List<String> listfrommain= makelistfromsubandlist(valueList,xlist,groupsList);
                datalistmapp.put(""+i,listfrommain);
            }
        }

        /*
        for (Map.Entry<String, List<String>> entry : crerateMapusingSizeMap.entrySet()) {
            String key = entry.getKey();
            List<String> valueList = entry.getValue();
            List<String> listfrommain= makelistfromsubandlist(valueList,xlist,groupsList);
            datalistmapp.put(""+index,listfrommain);
            index++;
        }
         */
        System.out.println("Value List: " + datalistmapp);
        return datalistmapp;
    }
    public static List<String> mergeLists(List<String> list1, List<String> list2) {
        List<String> mergedList = new ArrayList<>(list1);
        mergedList.addAll(list2);
        return mergedList;
    }
    public  static  List<String> makelistfromsubandlist(List<String> valueList,List<String> xlist,List<Map<String, List<String>>> groupsList)
    {
        List<String> mergedList = new ArrayList<>();
        for(int i=0;i<valueList.size();i++)
        {
            String word=valueList.get(i);
            int index = xlist.indexOf(word);
            List<String> targetList = groupsList.get(index).get(""+index);

            mergedList=mergeLists(mergedList,targetList);
        }
        System.out.println("Key: " + mergedList);
        return mergedList;
    }
    public static Map<String, List<String>> crerateMapusingSize(List<Integer> integerList, int detector) {
        Map<String, List<String>> xlistmap = new HashMap<>();
        int index = 0;
        int i = 0;
        while (i < integerList.size()) {
            List<String> currentGroup = new ArrayList<>();
            int currentValue = integerList.get(i);
            currentGroup.add("" + currentValue);
            while (i + 1 < integerList.size() && Math.abs(integerList.get(i + 1) - currentValue) < detector) {
                i++;
                currentValue = integerList.get(i);
                currentGroup.add("" + currentValue);
            }
            xlistmap.put("" + index, currentGroup);
            index++;
            i++;
        }

        return xlistmap;
    }
    public static List<Integer> convertStringListToIntegerList(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();
        for (String s : stringList) {
            try {
                integerList.add(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer string: " + s);
            }
        }
        return integerList;
    }
    public static List<Integer> sortListAscending(List<Integer> list) {
        List<Integer> sortedList = new ArrayList<>(list);
        sortedList.sort(Integer::compareTo);
        return sortedList;
    }
}
