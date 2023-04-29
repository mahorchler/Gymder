package com.group5.gymder;

public class Message {
    private String message;
    private boolean senderIsHost;

    private User sender;

    public Message(String message, boolean senderIsHost, User sender){
        this.message = message;
        this.senderIsHost = senderIsHost;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public boolean senderIsHost(){
        return senderIsHost;
    }

    public User getSender() { return sender; }
}
