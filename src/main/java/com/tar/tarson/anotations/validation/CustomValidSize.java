package com.tar.tarson.anotations.validation;

import com.tar.tarson.anotations.process.CustomCheckSizeValidator;
import com.tar.tarson.exception.TarException;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomCheckSizeValidator.class)
@Deprecated
public @interface CustomValidSize {
    String message() default "";
    String errorCode() default "9999";
    String errorDesc() default "Out of Size";
    String errorMessage() default "Out of Size";
    String errorTitle() default "Validation failed";
    Class<?>[] groups() default {};
    int size() default -1;
    Class<? extends RuntimeException> exception() default TarException.class;
    HttpStatus httpStatus() default HttpStatus.BAD_REQUEST;
    Class<? extends Payload>[] payload() default {};
}
