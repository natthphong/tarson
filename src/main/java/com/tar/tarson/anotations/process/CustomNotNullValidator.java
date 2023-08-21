package com.tar.tarson.anotations.process;

import com.tar.tarson.anotations.validation.CustomValidEmpty;
import com.tar.tarson.utils.NumberUtils;
import com.tar.tarson.utils.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;


public class CustomNotNullValidator implements ConstraintValidator<CustomValidEmpty, Object> {
    private boolean checkIsBlank;
    private boolean checkIsEmpty;
    private Class<? extends RuntimeException> exceptionClass;
    private HttpStatus httpStatus;
    private String errorCode;
    private String errorDesc;
    private String errorMessage;
    private String errorTitle;
    @Override
    public void initialize(CustomValidEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        checkIsBlank = constraintAnnotation.checkIsBlank();
        checkIsEmpty = constraintAnnotation.checkIsEmpty();
        exceptionClass = constraintAnnotation.exception();
        httpStatus = constraintAnnotation.httpStatus();
        errorCode = constraintAnnotation.errorCode();
        errorDesc = constraintAnnotation.errorDesc();
        errorMessage = constraintAnnotation.errorMessage();
        errorTitle = constraintAnnotation.errorTitle();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            throwException();
            return false;
        }
        if (checkIsBlank && value instanceof String && StringUtils.isBlank((String) value)) {
            throwException();
            return false;
        }
        if (checkIsEmpty && value instanceof String && StringUtils.isEmpty((String) value)) {
            throwException();
            return false;
        }
        return true;
    }

    private void throwException() {
        RuntimeException exception;
        try {
            exception = exceptionClass.getDeclaredConstructor(
                            HttpStatus.class, String.class, String.class, String.class, String.class)
                    .newInstance(httpStatus, errorCode, errorDesc, errorMessage, errorTitle);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to instantiate custom exception", e);
        }
        System.out.println(exception);
        throw exception;
    }
}


