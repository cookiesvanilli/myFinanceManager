package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ToString
public class MySpending {
    @JsonProperty("spending")
    private Map<String, Long> spending;
    private static final String DATA_FILE = "spending.json";

    public MySpending(Map<String, Long> spending) {
        this.spending = spending;
    }

    public Map<String, Long> getSpending() {
        return spending;
    }

    public void setSpending(Map<String, Long> spending) {
        this.spending = spending;
    }

    public static Map<String, Long> categoryHashMap(Map<String, String> categories) {
        Map<String, Long> spending = new HashMap<>();
        for (Map.Entry<String, String> category : categories.entrySet()) {
            spending.put(category.getValue(), 0L);
        }
        spending.put("другое", 0L);
        return spending;
    }

    //подсчёт новых трат
    public void sumNewSpending(Map<String, String> categories, DataRequest dataRequest) {
        Optional<String> stringOptional = categories.keySet().stream()
                .filter(value -> dataRequest.getTitle().equals(value)).findFirst();
        if (stringOptional.isPresent()) {
            String category = categories.get(stringOptional.get());
            long price = (spending.get(category) + dataRequest.getSum());
            spending.put(category, price);
        } else {
            String category = "другое";
            long price = spending.get(category) + dataRequest.getSum();
            spending.put(category, price);
        }
    }

    public static Map<String, Long> loadDataFromJSON() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
            MySpending mySpending = objectMapper.readValue(new File(DATA_FILE), MySpending.class);
            return mySpending.getSpending();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public void saveDataToJSON() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(DATA_FILE), this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}