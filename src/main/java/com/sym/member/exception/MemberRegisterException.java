package com.sym.member.exception;

public class MemberRegisterException extends RuntimeException {


    public MemberRegisterException() {
        super();
    }

    public MemberRegisterException(String message) {
        super(message);
    }

    public MemberRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberRegisterException(Throwable cause) {
        super(cause);
    }
}
