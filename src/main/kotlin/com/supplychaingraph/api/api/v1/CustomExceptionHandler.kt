package com.supplychaingraph.api.api.v1

import com.supplychaingraph.api.InvalidEdgeException
import com.supplychaingraph.api.NoEdgesFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

@RestControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val fieldErrors: List<FieldError> = ex.fieldErrors
        val errorMapping = fieldErrors.associate { it.field to it.defaultMessage }
        val errorDetails =
            ErrorDetails(
                timestamp = LocalDateTime.now(),
                message = "Validation Failed",
                details = errorMapping,
            )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoEdgesFoundException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCustomValidationError(e: NoEdgesFoundException) =
        ResponseEntity(
            ErrorDetails(
                timestamp = LocalDateTime.now(),
                message = e.message ?: "",
                details = mapOf(),
            ),
            HttpStatus.BAD_REQUEST,
        )

    @ExceptionHandler(InvalidEdgeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidEdgeException(e: InvalidEdgeException) =
        ResponseEntity(
            ErrorDetails(
                timestamp = LocalDateTime.now(),
                message = e.message ?: "",
                details = mapOf(),
            ),
            HttpStatus.BAD_REQUEST,
        )

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationError(e: ConstraintViolationException) =
        ResponseEntity(
            ErrorDetails(
                timestamp = LocalDateTime.now(),
                message = e.message ?: "",
                details = mapOf(),
            ),
            HttpStatus.BAD_REQUEST,
        )
}

data class ErrorDetails(
    val timestamp: LocalDateTime,
    val message: String,
    val details: Map<String, String?>,
)
