package ru.alexz.tinkofftesttask.exception;

public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String paramName;

    public CommonException(ErrorCode errorCode, String  paramName) {
        this.errorCode = errorCode;
        this.paramName = paramName;
    }

    public CommonException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getParamName() {
        return paramName;
    }
}
