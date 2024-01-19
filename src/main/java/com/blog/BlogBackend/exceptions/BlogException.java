package com.blog.BlogBackend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogException extends RuntimeException {
    private HttpStatus status;
    public BlogException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
