import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.util.*;

public class MessagesPane extends JTextArea {
	public MessagesPane() {
		setLayout(new GridLayout(30,1,0,0));
		append("Load World..." + "\n");
		append("Load Airports..." + "\n");
	}

    public void renderNewMessages() {
        ArrayList<String> messages = Universe.getInstance().getMessages();
        for (String m : messages) {
            addMessage(m);
        }
        messages.clear();
    }

	public void addMessage(String message){
		append(message + "\n");
	}
}
