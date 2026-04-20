package com.cuongtay.blog_app.excaption;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;

@ControllerAdvice // dùng để xử lý ngoại lệ toàn cục cho tất cả các controller
public class ErrorHandler extends ResponseEntityExceptionHandler
    implements AuthenticationEntryPoint, AccessDeniedHandler {
    // xử lí ngoại lệ chung cho tất cả các Lỗi trong server (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception ex
    ){
        var message = "Đã xảy ra lỗi trong server: " + ex.getMessage();
        var errorResponse = new ErrorResponse(message, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // xử lí ngoại lệ cho các api có kiểu dữ liệu không hợp lệ (400)
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            org.springframework.beans.TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var message = "Kiểu dữ liệu không hợp lệ cho trường: " + ex.getPropertyName();
        var errorResponse = new ErrorResponse(message, null);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    // xử lí ngoại lệ cho các api thiếu tham số yêu cầu (400)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            org.springframework.web.bind.MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var message = "Thiếu tham số yêu cầu: " + ex.getParameterName();
        var errorResponse = new ErrorResponse(message, null);
        return new ResponseEntity<>(errorResponse, headers, status);
    }
    // xử lí ngoại lệ cho các api không đúng định dạng (phai là json) (415)
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            org.springframework.web.HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var message = "Loại kiểu dữ liệu" + ex.getContentType() + " không được hỗ trợ.";
        var errorResponse = new ErrorResponse(message, null);
        return new ResponseEntity<>(errorResponse, headers, status);
    }
    // xử lí ngoại lệ cho các api không tồn tại hoặc sai phương thức
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            org.springframework.web.HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        var message = "Phương thức " + ex.getMethod() + " không được hỗ trợ cho tài nguyên này.";
        var errorResponse = new ErrorResponse(message, null);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        var message = "Dữ liệu không hợp lệ";
        var details = new LinkedHashMap<String, String>();
        for(var error : ex.getFieldErrors()){
            var key = error.getField();
            var value = error.getDefaultMessage();
            details.put(key, value);
        }
        var errorResponse = new ErrorResponse(message, details);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    // xử lý ngoại lệ ConstraintViolationException
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex
    ){
        var message = "Dữ liệu không hợp lệ";
        var details = new LinkedHashMap<String, String>();
        for(var error : ex.getConstraintViolations()){
            var key = error.getPropertyPath().toString();
            var value = error.getMessage();
            details.put(key, value);
        }
        var errorResponse = new ErrorResponse(message, details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    // xử lý ngoại lệ xác thực không thành công (401 Unauthorized)
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException
    ) throws IOException, ServletException
    {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        var message = "Tài khoản hoặc mật khẩu không đúng. Vui lòng kiểm tra lại.";
        var error = new ErrorResponse(message,null);
        var out = response.getOutputStream();
        new ObjectMapper().writeValue(out, error);
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException
    {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        var message = "Quyền truy cập bị từ chối.";
        var error = new ErrorResponse(message,null);
        var out = response.getOutputStream();
        new ObjectMapper().writeValue(out, error);
    }
}
