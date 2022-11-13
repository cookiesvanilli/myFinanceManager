import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    private static final int PORT = 8989;
    private OutputStream serverOut;
    private InputStream serverIn;

    @Test
    void serverTest() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        listen(server);

        Socket client = new Socket("localhost", PORT);
        OutputStream clientOut = client.getOutputStream();
        InputStream clientIn = client.getInputStream();

        write(clientOut, "Hi");
        assertRead(serverIn, "Hi");

        write(serverOut, "Hello");
        assertRead(clientIn, "Hello");

        printWrite(clientOut, "Test printWrite");
        assertRead(serverIn, "Test printWrite");

        printWrite(serverOut, "Test printWrite again");
        assertRead(clientIn, "Test printWrite again");

        client.close();
        server.close();
    }

    private void write(OutputStream out, String str) throws IOException {
        out.write(str.getBytes());
        out.flush();
    }

    private void printWrite(OutputStream out, String str) {
        PrintWriter pw = new PrintWriter(out);
        pw.print(str);
        pw.flush();
    }

    private void assertRead(InputStream in, String expected) throws IOException {
        assertEquals(Float.parseFloat("Too few bytes available for reading: "), expected.length(), in.available());

        byte[] buf = new byte[expected.length()];
        in.read(buf);
        assertEquals(expected, new String(buf));
    }

    private void listen(ServerSocket server) {
        new Thread(() -> {
            try {
                Socket socket = server.accept();
                System.out.println("Incoming connection: " + socket);

                serverOut = socket.getOutputStream();
                serverIn = socket.getInputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
