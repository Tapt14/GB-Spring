package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private ChatHistory chatHistory;

    public void initialize (Server server, Socket socket) {
        try {
            this.chatHistory = new ChatHistory();
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    doAuth();
                    readMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred during client handler initialization");
        }
    }


    public void doAuth() throws IOException {
        while (true) {
            if (in.available() > 0) {
                String str = in.readUTF();
                if (str.startsWith("/auth")) {
                    String[] parts = str.split("\\s", 3);
                    String nickname = server.getAuthService().getNickByLoginAndPass(parts[1], parts[2]).getNickname();
                    if (nickname != null) {
                        if (server.isNickFree(nickname)) {
                            sendMessage("/authok " + nickname);
                            name = nickname;
                            server.subscribe(this);
                            server.sendMsgByNick(name, chatHistory.loadLastOneHundredLines());
                            server.broadcastMessage(name + " come in chat");
                            return;
                        } else {
                            sendMessage(String.format("User with nickname [%s] is already exist", nickname));
                        }
                    } else {
                        sendMessage("Invalid username and/or password.");
                    }
                }
            }
        }
    }

    public void readMessage() throws IOException {
        while (true) {
            if (in.available() > 0) {
                String msg = in.readUTF();
                if (!msg.startsWith("/")) {
                    chatHistory.saveHistoryToFile((name + ": " + msg + "\n").toCharArray());
                }
                System.out.println(name + ": " + msg);
                if (msg.startsWith("/")) {
                    if (msg.equals("/end")) {
                        closeConnection();
                    } else if (msg.startsWith("/w")) {
                        String[] parts = msg.split("\\s", 3);
                        String userNick = parts[1];
                        String message = String.format("%s to %s: %s", name, userNick, parts[2]);
                        server.sendMsgByNick(userNick, message);
                    } else {
                        server.sendMsgByNick(name, "There is no user with this name in the chat");
                    }
                } else {
                    server.broadcastMessage(name + ": " + msg);
                }
            }
        }
    }

    public void closeConnection() {
        server.unsubscribe(this);
        server.broadcastMessage(name + " left chat");
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
