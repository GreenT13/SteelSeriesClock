package com.apon.server;

public class ServerOperatorException extends RuntimeException {
    public ServerOperatorException(String message) {
        super(message);
    }

    public ServerOperatorException(Throwable cause) {
        super(cause);
    }
}
