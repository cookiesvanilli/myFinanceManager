import org.example.Server;

public class ServerThread extends Thread {
    private final Server server;

    public ServerThread() {
        server = new Server();
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void run() {
        Server.run();
    }
}