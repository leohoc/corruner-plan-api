package com.corruner.plan.api.controller;

import com.corruner.plan.api.controller.goal.exception.GoalNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleGoalNotFound_returns404ProblemDetail() {
        GoalNotFoundException ex = new GoalNotFoundException(UUID.randomUUID());

        ProblemDetail result = handler.handleGoalNotFound(ex);

        assertThat(result.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void handleGoalNotFound_titleIsGoalNotFound() {
        GoalNotFoundException ex = new GoalNotFoundException(UUID.randomUUID());

        ProblemDetail result = handler.handleGoalNotFound(ex);

        assertThat(result.getTitle()).isEqualTo("Goal Not Found");
    }

    @Test
    void handleGoalNotFound_detailContainsUUID() {
        UUID id = UUID.randomUUID();
        GoalNotFoundException ex = new GoalNotFoundException(id);

        ProblemDetail result = handler.handleGoalNotFound(ex);

        assertThat(result.getDetail()).contains(id.toString());
    }

    @Test
    void handleValidationErrors_returns400ProblemDetail() {
        MethodArgumentNotValidException ex = buildValidationException("goalType", "must not be null");

        ProblemDetail result = handler.handleValidationErrors(ex);

        assertThat(result.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void handleValidationErrors_titleIsValidationError() {
        MethodArgumentNotValidException ex = buildValidationException("goalType", "must not be null");

        ProblemDetail result = handler.handleValidationErrors(ex);

        assertThat(result.getTitle()).isEqualTo("Validation Error");
    }

    @Test
    void handleValidationErrors_errorsListContainsFieldAndMessage() {
        MethodArgumentNotValidException ex = buildValidationException("goalNumber", "must be greater than 0");

        ProblemDetail result = handler.handleValidationErrors(ex);

        @SuppressWarnings("unchecked")
        List<String> errors = (List<String>) result.getProperties().get("errors");
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0)).isEqualTo("goalNumber: must be greater than 0");
    }

    private MethodArgumentNotValidException buildValidationException(String field, String message) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError("request", field, message));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        return ex;
    }
}
