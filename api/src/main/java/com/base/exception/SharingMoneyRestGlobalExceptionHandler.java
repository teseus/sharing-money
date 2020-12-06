package com.base.exception;

import com.base.dto.ErrorMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SharingMoneyRestGlobalExceptionHandler {
    @ExceptionHandler(value = {ConstraintViolationException.class,
            IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorMessageDTO> catchIllegalArgument(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(assbleErrorMessageDTO(ex, ex.getMessage()));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<ErrorMessageDTO> catchDataViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(assbleErrorMessageDTO(ex, "같은 토큰으로는 두번이상 받을 수 없습니다."));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessageDTO> catchBadRequest(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(assbleErrorMessageDTO(ex, ex.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessageDTO> catchOthers(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(assbleErrorMessageDTO(ex, ex.getMessage()));
    }

    private ErrorMessageDTO assbleErrorMessageDTO(Exception ex, String message) {
        log.error("{}", ex.getMessage(), ex);
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
        errorMessageDTO.addMessage("error", ex.getClass().getName());
        errorMessageDTO.addMessage("reason", message);
        return errorMessageDTO;
    }
}
