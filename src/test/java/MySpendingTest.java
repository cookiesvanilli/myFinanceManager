import org.example.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MySpendingTest {
    private MySpending mySpending;
    private Map<String, String> categories;
    private DataRequest dataRequest;
    private static String date;

    @Test
    void load() {
        categories = TSVReader.readTSV(new File("categories.tsv"));
        mySpending = new MySpending(MySpending.categoryHashMap(categories));
        date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        dataRequest = new DataRequest("морковь", date, 25);
    }

    @Test
    void sumSpending() {
        mySpending.sumNewSpending(categories, dataRequest);
        Map<String, Long> spending = new HashMap<>();
        for (Map.Entry<String, String> category : categories.entrySet()) {
            spending.put(category.getValue(), 0L);
        }
        spending.put("другое", 50L);
        assertEquals(spending, mySpending.getSpending());
    }
}
