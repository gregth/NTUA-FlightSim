import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.util.*;

public class MessagesPane extends JTextArea {
	public MessagesPane() {
		setLayout(new GridLayout(30,1,0,0));
		append("Starting New Simulation" + "\n");
	}

    // Renders unrendered messages
    public void renderNewMessages() {
        ArrayList<String> messages = Universe.getInstance().getMessages();
        for (String m : messages) {
            addMessage(m);
        }
        messages.clear();
    }

    // Appends a message in MessagePane
	public void addMessage(String message){
		append(message + "\n");
	}
}
