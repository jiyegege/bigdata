package top.rogermaster.common.exception;

import lombok.Data;

/**
 * @Author: Roger
 * @description: 自定义异常
 * @date: 2020/8/19 11:05 下午
 */
@Data
public class CustomServiceException extends RuntimeException {
    private Integer code;
    private String msg;

    public CustomServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomServiceException(String msg) {
        this(111, msg);
    }
}
