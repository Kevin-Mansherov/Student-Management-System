package org.example.stage1.Exceptions;

public class NotExists extends RuntimeException{
    public NotExists(String message) {
        super(message);
    }
}
