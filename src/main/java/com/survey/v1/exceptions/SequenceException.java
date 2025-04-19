package com.survey.v1.exceptions;

import org.springframework.http.HttpStatus;

public class SequenceException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detailMessage;

    public SequenceException(String message, String detailMessage, ErrorCode errorCode) {
        super(message);
        this.detailMessage = detailMessage;
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getErrorTitle() {
        return errorCode.getErrorTitle();
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public enum ErrorCode {
        SEQUENCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Sequence not found");

        private final HttpStatus httpStatus;
        private final String errorTitle;

        ErrorCode(HttpStatus httpStatus, String errorTitle) {
            this.httpStatus = httpStatus;
            this.errorTitle = errorTitle;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public String getErrorTitle() {
            return errorTitle;
        }
    }
}
