package Client.ClientApplication;

import javax.swing.*;
import java.awt.*;

public class SendMessagePanel extends JPanel {

    private final JTextField sendMessageTextField = new JTextField();
    private final JButton sendMessageButton = new JButton("Send");

    SendMessagePanel() {

        setLayout(new BorderLayout());
        add(sendMessageTextField, BorderLayout.CENTER);
        add(sendMessageButton, BorderLayout.EAST);
    }

    public JTextField getSendMessageTextField() {
        return sendMessageTextField;
    }

    public JButton getSendMessageButton() {
        return sendMessageButton;
    }

}
