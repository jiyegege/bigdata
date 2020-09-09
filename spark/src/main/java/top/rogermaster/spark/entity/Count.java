package top.rogermaster.spark.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Roger
 * @description: Count
 * @date: 2020/9/3 1:42 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Count {
    /**
     * 单词
     */
    private String word;
    /**
     * 计数
     */
    private long count;
}
