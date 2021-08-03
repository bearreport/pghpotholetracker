package com.techelevator;

import com.techelevator.model.Pothole;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Pothole Not Found")
public class PotholeNotFoundException extends Exception {

    public PotholeNotFoundException() {super("Pothole Not Found");}
}
