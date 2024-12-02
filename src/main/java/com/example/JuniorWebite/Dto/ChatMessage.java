package com.example.JuniorWebite.Dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private String sender;  // L'expéditeur du message
    private String content; // Contenu du message
    private String recipient; // Destinataire du message
    private LocalDateTime timestamp; // Date et heure d'envoi du message

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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

