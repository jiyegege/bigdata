package top.rogermaster.common.common;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("00000", "成功"),
    FAILED("A0001", "失败");

    private final String code;
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
