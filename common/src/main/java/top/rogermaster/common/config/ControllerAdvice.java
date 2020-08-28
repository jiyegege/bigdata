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
import top.rogermaster.common.exception.CustomClientException;

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

    public static final String ERROR = "Error:{}";
    public static final String PARAMS_ERROR = "A0400";

    /**
     * 参数检验错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e MethodArgumentNotValidException
     * @return rest json
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultVo<Object> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error(ERROR, e);
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        StringBuilder errorMessages = new StringBuilder();
        objectErrors.forEach(objectError -> errorMessages.append(objectError.getDefaultMessage()).append(";"));
        return new ResultVo<>(PARAMS_ERROR, String.valueOf(errorMessages), null);
    }

    /**
     * 参数检验错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *
     * @param e ConstraintViolationException
     * @return rest json
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultVo<Object> handlerConstraintViolationException(final ConstraintViolationException e) {
        log.error(ERROR, e);
        String errorMessages = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return new ResultVo<>(PARAMS_ERROR, errorMessages, null);
    }

    /**
     * 参数检验错误 validate失败后抛出的异常是BindException异常。
     *
     * @param e ConstraintViolationException
     * @return rest json
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultVo<Object> handlerConstraintViolationException(final BindException e) {
        log.error(ERROR, e);
        String errorMessages = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return new ResultVo<>(PARAMS_ERROR, errorMessages, null);
    }

    /**
     * 自定义异常捕获
     *
     * @param e CustomException
     * @return rest json
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomClientException.class)
    public ResultVo<String> handlerCustomException(CustomClientException e) {
        return new ResultVo<>("A0001", "失败", e.getMsg());
    }

    /**
     * 其他异常捕获
     *
     * @param e CustomException
     * @return rest json
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResultVo<String> handlerOtherException(Exception e) {
        return new ResultVo<>("B0001", "失败", e.getMessage());
    }
}
