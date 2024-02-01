package org.example.springbootenumvalidation

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.converter.HttpMessageNotReadableException

@ControllerAdvice
class GlobalExceptionHandler {
  
  @ExceptionHandler(ServerException::class)
  fun handleServerException(e: ServerException): ErrorResponse {
    return ErrorResponse(e.code, e.message)
  }
  
  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun notValid(ex: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<*> {
    val errors = ex.allErrors.map { it.defaultMessage ?: "Error" }
    val result = mapOf("errors" to errors)
    
    return ResponseEntity(result, HttpStatus.BAD_REQUEST)
  }
  
  @ExceptionHandler(HttpMessageNotReadableException::class)
  fun notReadable(ex: HttpMessageNotReadableException, request: HttpServletRequest): ResponseEntity<*> {
    val errors = ex.message
    val result = mapOf("errors" to errors)
    
    return ResponseEntity(result, HttpStatus.BAD_REQUEST)
  }
}

data class ErrorResponse(
  val code: Int,
  val message: String,
)
