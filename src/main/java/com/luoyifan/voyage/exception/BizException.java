package com.luoyifan.voyage.exception;

/**
 * @author EvanLuo
  */
public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
