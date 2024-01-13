package ca.gse.guesswho.components;

import javax.swing.JTextPane;

public class ChatMessage extends JTextPane {
    private boolean messageRight;
    private String userName;

    public ChatMessage(boolean messageRight, String userName) {
        this.messageRight = messageRight;
        this.userName = userName;
    }

    public boolean isMessageRight() {
        return messageRight;
    }

    public void setMessageRight(boolean messageRight) {
        this.messageRight = messageRight;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
