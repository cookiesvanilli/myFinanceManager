package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TSVReader {
    public TSVReader() {
    }

    public static Map<String, String> readTSV(File file) {
        String s;
        Map<String, String> goodsByCategory = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while ((s = bufferedReader.readLine()) != null) {
                String[] productAndCategory = s.split("\t");
                for (int i = 0; i < productAndCategory.length - 1; i++) {
                    String product = productAndCategory[0];
                    String category = productAndCategory[1];
                    goodsByCategory.put(product, category);
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return goodsByCategory;
    }
}
