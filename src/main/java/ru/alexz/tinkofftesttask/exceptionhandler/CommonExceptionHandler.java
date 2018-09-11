package ru.alexz.tinkofftesttask.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.alexz.tinkofftesttask.exception.CommonException;
import ru.alexz.tinkofftesttask.exception.ErrorCode;
import ru.alexz.tinkofftesttask.model.resource.ErrorCodeResource;
import ru.alexz.tinkofftesttask.model.resource.ErrorResource;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResource> handleException(Exception ex) {
        ErrorResource errorResource = new ErrorResource();

        if (ex instanceof CommonException) {
            CommonException commonException = (CommonException) ex;
            ErrorCode errorCode = commonException.getErrorCode();
            log.error("runtime error occurred! errorCode={}", errorCode, commonException);
            String paramName = commonException.getParamName();
            ErrorCodeResource errorCodeResource;
            if (paramName == null) {
                errorCodeResource = getErrorCodeResource(errorCode);
            } else {
                errorCodeResource = getErrorCodeResource(errorCode, paramName);
            }
            errorResource.setError(errorCodeResource);
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            log.error("Common error during parameters binding", ex);
            errorResource.setError(getErrorCodeResource(ErrorCode.INVALID_URI));
        } else if (ex instanceof HttpMessageNotReadableException) {
            log.error("Invalid request body", ex);
            errorResource.setError(getErrorCodeResource(ErrorCode.INVALID_BODY));
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            log.error("Unsupported media type", ex);
            errorResource.setError(getErrorCodeResource(ErrorCode.INVALID_CONTENT_TYPE));
        } else {
            log.error("Common error found", ex);
            errorResource.setError(getErrorCodeResource(ErrorCode.INTERNAL_ERROR));
        }

        return new ResponseEntity<>(errorResource, errorResource.getError().getHttpStatus());
    }

    @ExceptionHandler({ NoHandlerFoundException.class, MissingServletRequestParameterException.class })
    @ResponseBody
    public ResponseEntity<ErrorResource> handleResourceNotFoundException(Exception ex) {
        log.error("Common error found", ex);
        return getCommonErrorResponse();
    }

    private ResponseEntity<ErrorResource> getCommonErrorResponse() {
        ErrorResource errorResource = new ErrorResource();
        errorResource.setError(getErrorCodeResource(ErrorCode.INVALID_URI));
        return new ResponseEntity<>(errorResource, errorResource.getError().getHttpStatus());
    }

    private ErrorCodeResource getErrorCodeResource(ErrorCode errorCode) {
        return ErrorCodeResource.builder()
                .message(errorCode.getMessage())
                .httpStatus(errorCode.getHttpStatus())
                .build();
    }

    private ErrorCodeResource getErrorCodeResource(ErrorCode errorCode, String paramName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(ErrorCode.PARAMETER_NAME, paramName);
        return ErrorCodeResource.builder()
                .message(StringSubstitutor.replace(errorCode.getMessage(), paramMap).trim())
                .httpStatus(errorCode.getHttpStatus())
                .build();
    }
}
