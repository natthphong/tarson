package com.tar.tarson.anotations.process;

import com.tar.tarson.anotations.validation.CustomValidSize;
import com.tar.tarson.utils.NumberUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;

public class CustomCheckSizeValidator implements ConstraintValidator<CustomValidSize, Object> {
    private Class<? extends RuntimeException> exceptionClass;
    private HttpStatus httpStatus;
    private String errorCode;
    private String errorDesc;
    private String errorMessage;
    private String errorTitle;
    private int size;

    @Override
    public void initialize(CustomValidSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        exceptionClass = constraintAnnotation.exception();
        httpStatus = constraintAnnotation.httpStatus();
        errorCode = constraintAnnotation.errorCode();
        errorDesc = constraintAnnotation.errorDesc();
        errorMessage = constraintAnnotation.errorMessage();
        errorTitle = constraintAnnotation.errorTitle();
        size = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        System.out.println("check size" +  value);
        System.out.println(errorMessage);
        System.out.println("======");
        if (size >= 0 && !NumberUtils.hasCorrectSize(value, size)) {
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
