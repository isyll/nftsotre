package com.isyll.nftstore.Exception;

import jakarta.validation.ValidationException;

public class UniqueConstraintViolationException extends ValidationException {

    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}
