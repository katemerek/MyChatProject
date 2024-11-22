package com.github.katemerek.clients.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnError(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        List<FieldError> allErrors = bindingResult.getFieldErrors();
        for (FieldError error : allErrors) {
            errors.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append("; ");
        }
        throw new PersonNotCreated(errors.toString());
    }
}
