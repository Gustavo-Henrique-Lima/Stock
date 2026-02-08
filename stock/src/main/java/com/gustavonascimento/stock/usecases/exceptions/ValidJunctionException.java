package com.gustavonascimento.stock.usecases.exceptions;

public class ValidJunctionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidJunctionException(String msg) {
        super(msg);
    }
}