package Client.ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Messenger extends JFrame {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;
    private final MessagePanel messagePanel = new MessagePanel();
    private final SendMessagePanel sendMessagePanel = new SendMessagePanel();

    public Messenger() {
       connectToServer();
       getMessenger();
    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 8564);
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

                        //nickField.setText(nick);
                    }
                    messagePanel.getSendTextArea().append(str + "\n");
                    //chatArea.append(str);
                    //chatArea.append("\n");
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

}
