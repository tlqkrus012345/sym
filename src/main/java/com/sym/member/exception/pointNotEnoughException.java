package com.sym.member.exception;

public class pointNotEnoughException extends RuntimeException {
    public pointNotEnoughException() {
        super();
    }

    public pointNotEnoughException(String message) {
        super(message);
    }

    public pointNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public pointNotEnoughException(Throwable cause) {
        super(cause);
    }
}
