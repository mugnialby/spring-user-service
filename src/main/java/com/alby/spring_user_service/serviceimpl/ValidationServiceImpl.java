package com.alby.spring_user_service.serviceimpl;

import com.alby.spring_user_service.service.ValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final Validator validator;

    @Override
    public void validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (!constraintViolations.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    
}
