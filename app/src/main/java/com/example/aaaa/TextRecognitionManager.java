package com.example.aaaa;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.List;

public class TextRecognitionManager {

    private static final String TAG = "TextRecognitionManager";

    private TextRecognizer textRecognizer;

    public TextRecognitionManager() {
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }

    public void recognizeText(Bitmap bitmap, final TextRecognitionCallback callback) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        textRecognizer.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        // Task completed successfully
                        //
                        String seconddata="";
                        List<String> xlist = new ArrayList<>();
                        List<String> ylist = new ArrayList<>();
                        for (Text.TextBlock block : visionText.getTextBlocks()) {
                            for (Text.Line line : block.getLines()) {
                                Rect boundingBox = line.getBoundingBox();
                                if (boundingBox != null) {

                                    int x = boundingBox.left;  // x-coordinate
                                    int y = boundingBox.top;   // y-coordinate
                                    String lineText = line.getText();
                                    if(lineText.length()>3)
                                    {
                                        xlist.add(""+x);
                                        ylist.add(""+y);
                                        if (seconddata==null || seconddata=="")
                                        {
                                            seconddata=seconddata+""+lineText;
                                        }
                                        else{
                                            seconddata=seconddata+"\n"+lineText;
                                        }


                                        Log.d("LineData", "Text: " + lineText + ", x: " + x + ", y: " + y);
                                    }

                                }
                            }
                        }
                        Log.d("LineData", ""+seconddata);

                        //

                        seconddata =  seconddata
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
                                .replace(")","|")
                                ;
                        callback.onSuccess(seconddata,xlist,ylist);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Log.e(TAG, "Text recognition failed: " + e.getMessage());
                        callback.onError(e.getMessage());
                    }
                });
    }

    public interface TextRecognitionCallback {
        void onSuccess(String resultText, List<String>xlist,List<String>ylist);
        void onError(String errorMessage);
    }
}
