package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        String host = "127.0.0.1";
        int port = 8080;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product name and cost: ");
        String[] input = scanner.nextLine().split(" ");
        String product = input[0];
        long sum = Long.parseLong(input[1]);
        ClientRequest clientRequest = new ClientRequest(product, sum);
        String message = clientRequest.stringJSON();
        clientRequest.requestJSON(message);

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(message);
            String response = in.readLine();
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
