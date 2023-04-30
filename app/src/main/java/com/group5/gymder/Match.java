package com.group5.gymder;

import java.util.ArrayList;

public class Match {
    private User u1;

    private User u2;

    private ArrayList<Message> messageList;

    public Match(User u1, User u2) {
        this.u1 = u1;
        this.u2 = u2;
        messageList = new ArrayList<Message>();
    }

    public User getU1() {
        return u1;
    }

    public User getU2() {
        return u2;
    }

    public void addMessage(Message m){
        messageList.add(m);
    }

    public ArrayList<Message> getMessages(){
        return this.messageList;
    }
}
