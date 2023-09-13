//package com.sparta.spring_lv5.exception;
//
//import com.sparta.spring_lv5.dto.StatusResponseDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ExceptionHandler {
//
//    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<StatusResponseDto> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
//        BindingResult bindingResult = ex.getBindingResult();
//        FieldError fieldError = bindingResult.getFieldError();
//
//        StatusResponseDto message = new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage());
//        return new ResponseEntity<>(
//                // HTTP body
//                message,
//                // HTTP status code
//                HttpStatus.BAD_REQUEST
//        );
//    }
//
//    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<StatusResponseDto> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
//        StatusResponseDto message = new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
//        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//    }
//
//    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<StatusResponseDto> nullPointerExceptionHandler(NullPointerException ex) {
//        StatusResponseDto message = new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
//        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//    }
//}
