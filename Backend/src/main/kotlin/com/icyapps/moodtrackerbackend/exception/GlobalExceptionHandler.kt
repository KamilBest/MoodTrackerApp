package com.icyapps.moodtrackerbackend.exception

import com.icyapps.moodtrackerbackend.dto.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.dao.DataIntegrityViolationException
import org.hibernate.exception.ConstraintViolationException
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ErrorResponse> {
        logger.warn("Business exception occurred: ${ex.message}")
        
        return ResponseEntity.status(ex.status)
            .body(ErrorResponse(
                message = ex.message ?: "Unknown business error",
                timestamp = LocalDateTime.now(),
                status = ex.status.value()
            ))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.warn("Validation exception occurred: ${ex.message}")
        
        val errors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }
        val errorMessage = errors.joinToString(", ")
        
        return ResponseEntity.badRequest()
            .body(ErrorResponse(
                message = errorMessage,
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value()
            ))
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        logger.warn("Data integrity violation occurred: ${ex.message}")
        
        // Check if this is a constraint violation (like unique constraint)
        val cause = ex.cause
        if (cause is ConstraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse(
                    message = "Mood already submitted today for this device",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.CONFLICT.value()
                ))
        }
        
        return ResponseEntity.badRequest()
            .body(ErrorResponse(
                message = "Data integrity violation occurred",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value()
            ))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error occurred", ex)
        
        return ResponseEntity.internalServerError()
            .body(ErrorResponse(
                message = "An unexpected error occurred. Please try again later.",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.INTERNAL_SERVER_ERROR.value()
            ))
    }
} 