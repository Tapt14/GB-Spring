package Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Component
public class Server {

    @Value("${server.port}")
    private int port;
    private BaseAuthService authService;
    private List<ClientHandler> clients;

    @Autowired
    public void setAuthService(BaseAuthService authService) {
        this.authService = authService;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(this.port)) {

            clients = new ArrayList<>();
            authService.start();

            while(true) {
                System.out.println("Server.Server started on port: " + port);
                System.out.println("Server.Server is waiting for clients...");
                Socket socket = server.accept();
                System.out.println(String.format("Client connected: %s", socket.toString()));
                new ClientHandler().initialize(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong during server startup");
            e.printStackTrace();

        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized boolean isNickBusy(String nickname) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getName().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean isNickFree(String nickname) {
        return !isNickBusy(nickname);
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public synchronized void sendMsgByNick(String nick, String msg) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                o.sendMessage(msg);
                return;
            }
        }
        System.out.println("There is no user with this nickname...");
    }


    public AuthService getAuthService() {
        return authService;
    }
}
