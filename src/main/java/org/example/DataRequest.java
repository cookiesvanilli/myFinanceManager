package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class DataRequest {
    @JsonProperty("title")
    private String title;
    @JsonProperty("date")
    private String date;
    @JsonProperty("sum")
    private long sum;

    public static DataRequest readJSON(String input) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(input, DataRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error reading JSON: %s", e.getMessage()));
        }
    }
}
