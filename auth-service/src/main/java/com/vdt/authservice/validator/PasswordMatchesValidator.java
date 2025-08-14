package com.vdt.authservice.validator;

import com.vdt.authservice.dto.request.ChangePasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, ChangePasswordRequest> {
    @Override
    public boolean isValid(ChangePasswordRequest changePasswordRequest, ConstraintValidatorContext constraintValidatorContext) {
        if(changePasswordRequest == null) return false;
        return changePasswordRequest.newPassword().equals(changePasswordRequest.confirmPassword());
    }
}
