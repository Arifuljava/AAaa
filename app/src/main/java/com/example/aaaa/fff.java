package com.example.aaaa;

public class fff {
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
1 |0107:50 2112:03 212:27 2116:32 |0117:00 |0119:04
                                                                                                    2 |0207:52 2212:02 2212:29
                                                                                                    0216:31
                                                                                                    |0217:02
                                                                                                    0219:02
                                                                                                    3 0307:50 0312:01 2312:29 0316:30 0317:01 2319:05
                                                                                                    4 0407:54 0412:03 2412:23 0416:32 0417:02 0419:08
                                                                                                    5 0507:56 2512:01 2512:26 2516:35 0516:57 0519:09
                                                                                                    50607:51 2612:04 2612:26 2616:340616:58 2619:05
                                                                                                    7 0707:52 2712:02 0712:27 2716:330716:59 2719:08
                                                                                                    3 0807:52 2812:02 2812:28 0816 32 0817:00 2819:05
                                                                                                    |0907:54 2912:00 2912:8 2916:34 |2916:59 2919:08
                                                                                                    1007:57 012:03 1012:5 1016:30 1017:00 1019:09
                                                                                                    107:55
                                                                                                    112:02 12:16 1u16:32 1116:56
                                                                                                    h19:00
                                                                                                    1207:52 1212:00 1212:27 İ216:31 1216:57 1219:03
                                                                                                    1307:53 İ312:02 312:28 316:32 1316:58 1319:02
                                                                                                    1407:55 1412:01 1412:25 1416:31 416:59 1419:0I
                                                                                                    I507:55 512:00 1512:76 İ516:30 1517:00 1519:00
 */