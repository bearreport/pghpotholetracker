package com.techelevator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserDoesNotOwnPotholeException  extends Exception {
    private static final long serialVersionUID = 1L;

    public UserDoesNotOwnPotholeException(){
        super("User does not own pothole.");
    }

}
