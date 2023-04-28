package com.group5.gymder;

public class Message {
    private String message;
    private boolean senderIsHost;

    public Message(String message, boolean senderIsHost){
        this.message = message;
        this.senderIsHost = senderIsHost;
    }

    public String getMessage() {
        return message;
    }

    public boolean senderIsHost(){
        return senderIsHost;
    }
}
