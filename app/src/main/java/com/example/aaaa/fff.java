package com.example.aaaa;

public class fff {
    /*
     /*
                    timelistResult=textClassificationManager.margeDateList(timelistResult);
                    timelistResult=textClassificationManager.sortByKey(timelistResult);
                    List<String> finaltimelist   = textClassificationManager.Dateconverttolist(timelistResult);
                    finaltimelist   = textClassificationManager.replacedTime(finaltimelist);
                     */
    //for date
                    /*
                    datelistResult=textClassificationManager.margeDateList(datelistResult);
                    datelistResult=textClassificationManager.replacedAndMakeDateClear(datelistResult);
                    List<String> flattenedList   = textClassificationManager.Dateconverttolist(datelistResult);
                    int detector =  textClassificationManager.determinecount(flattenedList);
                    datelistResult=textClassificationManager.replacedDate(datelistResult,detector);
                    datelistResult=textClassificationManager.sortByKey(datelistResult);
                    List<String> finalDateList   = textClassificationManager.Dateconverttolist(datelistResult);
                    List<String> finalresultwithpercentages   = textClassificationManager.makefinallistfordateandTime(finalDateList,finaltimelist);
                    List<String> finalResultFromSDK   = textClassificationManager.determinepercentages(finalresultwithpercentages);
                    System.out.println("detector : "+finalResultFromSDK);
                    successCallback.onSuccess(finalResultFromSDK);
                     */

    //Log.e("datelistResult",""+listOfLists);

     /*
        listview.setLayoutManager(new LinearLayoutManager(this));
        rowDataList = new ArrayList<>();
        adapter = new TableAdapter(rowDataList);
        listview.setAdapter(adapter);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_29);
      //  bitmap = adjustBrightnessAndRotate(bitmap,1.5f,90);
        changeimage.setImageBitmap(bitmap);
         TextRecognitionManager textRecognitionManager;
        textRecognitionManager = new TextRecognitionManager();
        textRecognitionManager.recognizeText(bitmap, new TextRecognitionManager.TextRecognitionCallback() {
            @Override
            public void onSuccess(String resultText) {
                Log.d("haveeeee", ""+resultText);
                String[] lines = resultText.split("\n");
                ArrayList<String> linesList = new ArrayList<>(Arrays.asList(lines));
                ArrayList<String> resultList = new ArrayList<>();
                for(int i = 1; i <= 31; i++) {
                    String result = "";
                    String target = "";
                    if (i < 10) {
                        target = "0" + i;
                    } else {
                        target = "" + i;
                    }

                    for (String line : linesList) {
                        int count = 0;
                        int index = line.indexOf(target);
                        while (index != -1) {
                            count++;
                            index = line.indexOf(target, index + 1);
                        }
                        if (count > 3) {
                            result = line;
                            break;
                        }
                    }

                    if (!result.isEmpty()) {

                        resultList.add(result);
                        linesList.remove(result);  // Remove the found result from linesList
                    } else {
                        System.out.println("No line contains '" + target + "' more than 3 times.");
                    }
                }

// Convert ArrayList back to array if needed
                lines = linesList.toArray(new String[0]);
                System.out.println(resultList);
         */

              /*
                String[] lines = resultText.split("\n");
                String targetLineStart = "15";
                for (String line : lines) {
                    if (line.startsWith(targetLineStart)) {
                        String processedLine = processLine(line);


                        Log.d("haveeeee", ""+processedLine);
                        break;
                    }
                }

               */
    //third

    //second approch
                /*
                   String[] words = resultText.split("\\s+");

                ArrayList<String> filteredWords = new ArrayList<>();
                ArrayList<String> mainlistttt = new ArrayList<>();
                for (String word : words) {
                    if (word.length() >=2) {
                        filteredWords.add(word);
                    }
                }

                for(int i=0;i<filteredWords.size();i++)
                {
                    String word = filteredWords.get(i);
                    if (word.length() >= 6) {
                        if (containsEnglishAlphabet(word))
                        {
                            int englishAlphabetCount =  countEnglishAlphabets(word);

                            if (englishAlphabetCount == 1 )
                            {
                                mainlistttt.add(word);
                            }
                        }
                        else {
                            mainlistttt.add(word);
                        }
                    }
                }

                removeSpecialCharacters(mainlistttt);
                processStrings(mainlistttt);
                mainlistttt = splitAndProcess(mainlistttt);

                // Print the result
                mainlistttt = splitBy7(mainlistttt);
                mainlistttt = checkspecialindexxx(mainlistttt);
                ArrayList<String> finalFilteredWords = mainlistttt;
                ArrayList<String> finalMainlistttt = mainlistttt;
                determinePercentage(mainlistttt, resultList -> {
                    System.out.println("Resulting List:"+resultList);
                    System.out.println("Resulting List:"+resultList.size());
                    for (int i = 0; i < finalMainlistttt.size(); i += 6) {
                        String column1 = i < finalMainlistttt.size() ? finalMainlistttt.get(i) : "";
                        String column2 = i + 1 < finalMainlistttt.size() ? finalMainlistttt.get(i + 1) : "";
                        String column3 = i + 2 < finalMainlistttt.size() ? finalMainlistttt.get(i + 2) : "";
                        String column4 = i + 3 < finalMainlistttt.size() ? finalMainlistttt.get(i + 3) : "";
                        String column5 = i + 4 < finalMainlistttt.size() ? finalMainlistttt.get(i + 4) : "";
                        String column6 = i + 5 < finalMainlistttt.size() ? finalMainlistttt.get(i + 5) : "";

                        RowData rowData = new RowData(column1, column2, column3, column4, column5, column6);
                        rowDataList.add(rowData);
                    }

                    // Notify adapter about data changes
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                });
                 */

                 /*
                 Log.d("Extracted Text", ""+resultText);
                String[] words = resultText.split("\\s+");
                ArrayList<String> filteredWords = new ArrayList<>();
                for (String word : words) {
                    if (word.length() >= 6) {
                        if (containsEnglishAlphabet(word))
                        {
                            int englishAlphabetCount =  countEnglishAlphabets(word);

                            if (englishAlphabetCount == 1 )
                            {
                                filteredWords.add(word);
                            }
                        }
                        else {
                            filteredWords.add(word);
                        }
                    }
                }
                removeSpecialCharacters(filteredWords);
                Log.d("haveeeee", ""+filteredWords);
                  */
               /*
                Log.d("haveeeee", ""+filteredWords);
                //remove special character from first
                removeSpecialCharacters(filteredWords);

                // remove white space
                //detectedTextList= removeWhitespaceFromList(filteredWords);

                //remove special character from list
                processStrings(filteredWords);

                // Print the modified strings
                filteredWords = splitAndProcess(filteredWords);

                // Print the result
                filteredWords = splitBy7(filteredWords);
                filteredWords = checkspecialindexxx(filteredWords);
                ArrayList<String> finalFilteredWords = filteredWords;
                determinePercentage(filteredWords, resultList -> {
                    System.out.println("Resulting List:"+resultList);
                    System.out.println("Resulting List:"+resultList.size());
                    for (int i = 0; i < finalFilteredWords.size(); i += 6) {
                        String column1 = i < finalFilteredWords.size() ? finalFilteredWords.get(i) : "";
                        String column2 = i + 1 < finalFilteredWords.size() ? finalFilteredWords.get(i + 1) : "";
                        String column3 = i + 2 < finalFilteredWords.size() ? finalFilteredWords.get(i + 2) : "";
                        String column4 = i + 3 < finalFilteredWords.size() ? finalFilteredWords.get(i + 3) : "";
                        String column5 = i + 4 < finalFilteredWords.size() ? finalFilteredWords.get(i + 4) : "";
                        String column6 = i + 5 < finalFilteredWords.size() ? finalFilteredWords.get(i + 5) : "";

                        RowData rowData = new RowData(column1, column2, column3, column4, column5, column6);
                        rowDataList.add(rowData);
                    }

                    // Notify adapter about data changes
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                });
                */

           /*
            }

            @Override
            public void onError(String errorMessage) {
                // Handle error
                Log.e("Text Recognition Error", errorMessage);
            }
        });
            */

}


/*
16 1607:56 1612:01 1612:24 1616:32 161655 1619:06
                                                                                                    17 1707:57 712:00 |1712:21 1716:33 1716:56 1719:01
                                                                                                    18 1807:57 1812:02 1812:29 816:33 1816:57 1819:02
                                                                                                    19 1907:50 912:04 1912:28 1916:3 916:58 1919:03
                                                                                                    20 2007:51 2012:07 2012:29 p016:31 2016:59 2019:00
                                                                                                    21 2107:51 2112:05 2112:27 2116:32 2116:59 2119:01
                                                                                                    22 2207:52 2212:01 |2212:26 p216:34 2216:58 2219:02
                                                                                                    23 2307:45 2312:02 2312:25 2316:35 2316:59 2319:03
                                                                                                    24 407:49 2412:03 2412:28 2416:36 2416:59 2419:03
                                                                                                    25 2507:59 2512:05 2512:29 2516:33 2516:5| 2519:06
                                                                                                    26 2607:57 2612:06 2612:28 2616:35 2616:55 2619:02
                                                                                                    27 2707:56 2712:09 2712:27 27:6:3% 2716:57 2719:08
                                                                                                    28 2807:53 2812:06 2812:28 2816:33 2816:58 p819:04
                                                                                                    29 2907:52 2912:06 2912:29 9916:34 2916:58 2919:02
                                                                                                    30 3007:54 3012:04 3012:21 3016:34 3017:00 3019:02
                                                                                                    31 3107:55 3112:07 3112:23 3II6:35 3117:00 3119:03
 */