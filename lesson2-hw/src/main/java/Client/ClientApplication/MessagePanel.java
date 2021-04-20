package Client.ClientApplication;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {

    private final JTextArea sendTextArea = new JTextArea();

    MessagePanel () {
        setLayout(new BorderLayout());
        JScrollPane scrollPanelSendTextArea = new JScrollPane(sendTextArea);
        add(scrollPanelSendTextArea);
        sendTextArea.setBackground(new Color(230,230,230));
        sendTextArea.setEditable(false);
    }

    protected JTextArea getSendTextArea() {
        return sendTextArea;
    }
}
