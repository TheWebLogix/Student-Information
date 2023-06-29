package com.example.studentinformation.Student;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException (String message){
        super(message);
    }
}
