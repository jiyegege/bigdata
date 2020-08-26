package top.rogermaster.common.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Roger
 * @description: 统一返回实体
 * @date: 2020/7/25 7:51 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {
    /**
     * 状态：1.成功，0：失败
     */
    private String code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回内容
     */
    private T data;
}
