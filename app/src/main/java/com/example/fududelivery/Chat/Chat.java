package com.example.fududelivery.Chat;

public class Chat {
    private String chatSender;
    private String chatReceiver;
    private String time;
    private String bodyMessage;
    private String senderName;
    private String receiverName;

    public Chat(String chatSender, String chatReceiver, String time, String bodyMessage, String senderName, String receiverName) {
        this.chatSender = chatSender;
        this.chatReceiver = chatReceiver;
        this.time = time;
        this.bodyMessage = bodyMessage;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getChatReceiver() {
        return chatReceiver;
    }

    public String getChatSender() {
        return chatSender;
    }

    public String getTime() {
        return time;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public void setChatReceiver(String chatReceiver) {
        this.chatReceiver = chatReceiver;
    }

    public void setChatSender(String chatSender) {
        this.chatSender = chatSender;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
