package com.exam.examserver.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginPasswordException extends RuntimeException{

    private String filedValue;
    public LoginPasswordException(String filedValue){
        super(String.format("Invalid Password For User ( Username : %s )",filedValue));
    }

}
