package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private static final Map<String, String> goodsByCategory = TSVReader.readTSV(new File("categories.tsv"));
    private static final Map<String, Long> spending;
    // открываемый порт сервера
    private static final int port = 8989;

    static {
        if (!(new File("spending.json").exists())) {
            spending = MySpending.categoryHashMap(goodsByCategory);
        } else {
            spending = MySpending.loadDataFromJSON();
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port);) { // стартуем сервер один(!) раз
            System.out.println("Server started...");
            while (true) { // в цикле(!) принимаем подключения
                System.out.println("Client connected!");
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    MySpending mySpending = new MySpending(spending);
                    String input = in.readLine();
                    DataRequest dataRequest = DataRequest.readJSON(input);
                    if (dataRequest.getTitle().equals("end")) {
                        out.println("The server operation is completed");
                        break;
                    }

                    if (dataRequest.getSum() < 0) {
                        out.println("You have entered a negative amount");
                    } else {
                        mySpending.sumNewSpending(goodsByCategory, dataRequest);
                        System.out.println(spending);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("The server cannot start");
            e.printStackTrace();
        }
    }
}