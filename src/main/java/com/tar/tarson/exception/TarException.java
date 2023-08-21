package com.tar.tarson.exception;

import org.springframework.http.HttpStatus;

public class TarException extends RuntimeException {

    protected HttpStatus httpStatus;
    private String errorCode;
    private String errorTitle;
    private String errorDesc;
    private String errorMessage;
    private String suffixMsg;

    public TarException(String message) {
        super(message);
    }

    public TarException(HttpStatus httpStatus, String errorCode, String errorDesc, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.errorMessage = errorMessage;
    }

    public TarException(HttpStatus httpStatus, String errorCode, String errorDesc, String errorMessage, String errorTitle) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.errorMessage = errorMessage;
        this.errorTitle = errorTitle;
    }


    public TarException(HttpStatus httpStatus, String errorCode, String suffixMsg) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorDesc = suffixMsg;
        this.errorMessage = suffixMsg;
        this.suffixMsg = suffixMsg;
    }

    public TarException(HttpStatus httpStatus, String errorCode) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorTitle() {
        return this.errorTitle;
    }

    public String getErrorDesc() {
        return this.errorDesc;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getSuffixMsg() {
        return this.suffixMsg;
    }


    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSuffixMsg(String suffixMsg) {
        this.suffixMsg = suffixMsg;
    }


}
