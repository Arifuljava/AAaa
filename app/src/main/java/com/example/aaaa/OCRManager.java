package com.example.aaaa;

import android.graphics.Rect;
import android.util.Log;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OCRManager {
    private static final String TAG = "OCRManager";
    private final List<String> detectedTextList = new ArrayList<>();

    public interface OCRCallback {
        void onOCRComplete(List<String> detectedTextList);
    }

    public void performOCR(InputImage image, OCRCallback callback) {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    List<String> result = processTextLines(visionText);
                    Log.d(TAG, "OCR Result: " + result);
                    detectedTextList.addAll(result);
                    if (callback != null) {
                        callback.onOCRComplete(result);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error performing OCR: " + e.getMessage());
                    if (callback != null) {
                        callback.onOCRComplete(detectedTextList);
                    }
                });
    }

    private List<String> processTextLines(Text visionText) {
        List<String> result = new ArrayList<>();
        List<Text.TextBlock> blocks = visionText.getTextBlocks();
        List<TextObservation> observations = new ArrayList<>();

        for (Text.TextBlock block : blocks) {
            for (Text.Line line : block.getLines()) {
                StringBuilder text = new StringBuilder();
                for (Text.Element element : line.getElements()) {
                    Rect boundingBox = element.getBoundingBox();
                    if (boundingBox != null && !boundingBox.isEmpty()) {
                        text.append(transformText(element.getText())).append(" ");
                    }
                }
                String lineText = text.toString().trim();
                if (!lineText.isEmpty()) {
                    observations.add(new TextObservation(lineText, line.getBoundingBox()));
                }
                result.add(lineText);
            }
        }

        List<List<String>> columns = groupTextByColumns(observations);
        for (List<String> column : columns) {
            detectedTextList.addAll(column);
        }

        if (detectedTextList.isEmpty()) {
            detectedTextList.add("0");
        }

        return result;
    }

    private String transformText(String text) {
        return text.replace("L", "1")
                .replace("l", "1")
                .replace("s", "5")
                .replace("S", "5")
                .replace("a", "8")
                .replace("A", "8")
             ;
    }

    private List<List<String>> groupTextByColumns(List<TextObservation> observations) {
        Collections.sort(observations, Comparator.comparingInt(o -> o.boundingBox.left));

        List<List<String>> columns = new ArrayList<>();
        List<String> currentColumn = new ArrayList<>();
        int previousX = -1;

        for (TextObservation observation : observations) {
            int currentX = observation.boundingBox.left;
            if (previousX == -1 || Math.abs(currentX - previousX) < 20) {
                currentColumn.add(observation.text);
            } else {
                while (currentColumn.size() < 16) {
                    currentColumn.add("0");
                }
                columns.add(currentColumn);
                currentColumn = new ArrayList<>();
                currentColumn.add(observation.text);
            }
            previousX = currentX;
        }

        if (!currentColumn.isEmpty()) {
            while (currentColumn.size() < 16) {
                currentColumn.add("0");
            }
            columns.add(currentColumn);
        }

        return columns;
    }

    private static class TextObservation {
        String text;
        Rect boundingBox;

        TextObservation(String text, Rect boundingBox) {
            this.text = text;
            this.boundingBox = boundingBox;
        }
    }
}
