package com.chat.model;


public class ChatMessage {

    private String sender;
    private String content;
    private MessageType type;

    // Constructors
    public ChatMessage() {
    }

    public ChatMessage(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
    }

    public ChatMessage(String sender, MessageType type) {
        this.sender = sender;
        this.type = type;
    }

    // Getters and Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    // Builder class for ChatMessage
    public static class Builder {
        private String sender;
        private String content;
        private MessageType type;

        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }

        public ChatMessage build() {
            return new ChatMessage(sender, content, type);
        }
    }

    // Static method to create a builder
    public static Builder builder() {
        return new Builder();
    }
}
