package com.sym.sympathy.exception;

public class NotEnoughCounselorException extends RuntimeException{
    public NotEnoughCounselorException() {
        super();
    }

    public NotEnoughCounselorException(String message) {
        super(message);
    }

    public NotEnoughCounselorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughCounselorException(Throwable cause) {
        super(cause);
    }
}
