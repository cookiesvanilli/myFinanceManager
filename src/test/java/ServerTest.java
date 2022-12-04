import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.fail;

public class ServerTest {
    private static Thread serverThread;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @BeforeAll
    static void ServerInit() throws InterruptedException {
        serverThread = new ServerThread();
        serverThread.setPriority(java.lang.Thread.NORM_PRIORITY + 1);
        serverThread.start();
        Thread.sleep(1000);
    }

    @BeforeEach
    void ServerStart() throws IOException {
        socket = new Socket("localhost", 8989);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.setSoTimeout(1000);
    }

    @Test
    void DataAccept() {
        System.out.println("sending test packet");
        out.println("{\"title\": \"морковь\", \"date\": \"2022.11.15\", \"sum\": 20}");
        try {
            System.out.println("client received: " + in.readLine());
        } catch (SocketTimeoutException e) {
            fail("No response from server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void ConnectionsClose() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @AfterAll
    static void ServerClose() {
        try (
                Socket socket = new Socket("localhost", 8989);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
            socket.setSoTimeout(1000);
            out.close();
            socket.close();
            Thread.sleep(2000);
        } catch (SocketException | InterruptedException e) {
            fail("No connection to the server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
