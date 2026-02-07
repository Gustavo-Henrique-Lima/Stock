package com.gustavonascimento.stock.usecases.exceptions;

public class ValidDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidDataException(String msg) {
        super(msg);
    }
}