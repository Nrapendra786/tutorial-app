package org.example;

public class TutorialException extends RuntimeException {

    private String message="";
    public TutorialException(String message){
        this.message = message;
    }
}
