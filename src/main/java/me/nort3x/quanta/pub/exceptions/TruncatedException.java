package me.nort3x.quanta.pub.exceptions;

public class TruncatedException extends RuntimeException{
    public TruncatedException() {
    }

    public TruncatedException(String message) {
        super(message);
    }

    public TruncatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TruncatedException(Throwable cause) {
        super(cause);
    }

    public TruncatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
