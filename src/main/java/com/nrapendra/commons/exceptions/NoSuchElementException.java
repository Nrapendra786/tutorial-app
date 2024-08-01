package com.nrapendra.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchElementException extends ResponseStatusException {

    private static final String NOT_FOUND = "not found";

    public NoSuchElementException(HttpStatusCode status, String message) {
        super(HttpStatus.NOT_FOUND, NOT_FOUND);
    }
}
