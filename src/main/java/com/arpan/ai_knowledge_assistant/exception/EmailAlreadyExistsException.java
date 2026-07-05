package com.arpan.ai_knowledge_assistant.exception;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;


public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
