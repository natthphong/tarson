package com.tar.tarson.anotations.validation;

import com.tar.tarson.anotations.process.CustomNotNullValidator;
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
@Constraint(validatedBy = CustomNotNullValidator.class)
public @interface CustomValidEmpty {
    String message() default "";
    String errorCode() default "9999";
    String errorDesc() default "Validation failed";
    String errorMessage() default "Validation failed";
    String errorTitle() default "Validation failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean checkIsBlank() default true;
    boolean checkIsEmpty() default true;
    int size() default -1;

    Class<? extends RuntimeException> exception() default TarException.class;
    HttpStatus httpStatus() default HttpStatus.BAD_REQUEST;
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface List {
        CustomValidEmpty[] value();
    }
}
