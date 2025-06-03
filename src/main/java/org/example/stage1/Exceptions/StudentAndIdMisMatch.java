package org.example.stage1.Exceptions;

public class StudentAndIdMisMatch extends RuntimeException{
    public StudentAndIdMisMatch(String message) {
        super(message);
    }
}
