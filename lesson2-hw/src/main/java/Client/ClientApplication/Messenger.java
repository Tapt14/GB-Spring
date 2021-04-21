package Client.ClientApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Component
public class Messenger extends JFrame {

    private DataInputStream in;
    private DataOutputStream out;
    private String nick;
    private MessagePanel messagePanel;
    private SendMessagePanel sendMessagePanel;
    @Autowired
    public void setMessagePanel(MessagePanel messagePanel) {
        this.messagePanel = messagePanel;
    }

    @Autowired
    public void setSendMessagePanel(SendMessagePanel sendMessagePanel) {
        this.sendMessagePanel = sendMessagePanel;
    }

    public void run() {
        connectToServer();
        getMessenger();
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 8564);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to connect to server...");
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                while (true) {
                    String str = in.readUTF();
                    if (str.startsWith("/authok ")) {
                        nick = str.split("\\s")[1];
                    }
                    messagePanel.getSendTextArea().append(str + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void getMessenger() {
        setTitle("Messenger");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 150, 400, 500);
        add(sendMessagePanel, BorderLayout.PAGE_END);
        add(messagePanel, BorderLayout.CENTER);
        sendMessagePanel.getSendMessageTextField().addActionListener(new SendMessageActionListener());
        sendMessagePanel.getSendMessageButton().addActionListener(new SendMessageActionListener());
        sendMessagePanel.setMinimumSize(new Dimension(200, 250));
        setVisible(true);
    }

    public void sendMessage() throws IOException {
        out.writeUTF(sendMessagePanel.getSendMessageTextField().getText());
        out.flush();
        sendMessagePanel.getSendMessageTextField().setText("");
    }

    class SendMessageActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                sendMessage();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    public String getNick() {
        return nick;
    }
}
