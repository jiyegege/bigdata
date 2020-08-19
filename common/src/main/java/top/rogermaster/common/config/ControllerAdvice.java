package top.rogermaster.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.common.exception.CustomException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Roger
 * @description: Controller层全局统一异常拦截
 * @date: 2020/7/25 5:00 下午
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    //参数检验错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultVo<Object> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Error:", e);
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        StringBuilder errorMessages = new StringBuilder();
        objectErrors.forEach(objectError -> errorMessages.append(objectError.getDefaultMessage()).append(";"));
        return new ResultVo<>("A0400", String.valueOf(errorMessages), null);
    }

    //参数检验错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultVo<Object> handlerConstraintViolationException(final ConstraintViolationException e) {
        log.error("Error:", e);
        String errorMessages = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return new ResultVo<>("A0400", String.valueOf(errorMessages), null);
    }

    //参数检验错误 validate失败后抛出的异常是BindException异常。
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultVo<Object> handlerConstraintViolationException(final BindException e) {
        log.error("Error:", e);
        String errorMessages = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return new ResultVo<>("A0400", String.valueOf(errorMessages), null);
    }

    @ExceptionHandler(CustomException.class)
    public ResultVo<String> testExceptionHandler(CustomException e) {
        return new ResultVo<String>("B0001", "失败", e.getMsg());
    }
}
