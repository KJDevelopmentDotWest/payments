package com.epam.jwd.service.exception;

public class ServiceException extends Exception{

    private ExceptionCode errorCode = ExceptionCode.UNKNOWN_EXCEPTION_CODE;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServiceException(String message, ExceptionCode exceptionCode){
        super(message);
        this.errorCode = exceptionCode;
    }

    public ServiceException(ExceptionCode errorCode){
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode() {
        return errorCode;
    }
}
