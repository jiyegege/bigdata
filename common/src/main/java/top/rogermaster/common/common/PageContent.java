package top.rogermaster.common.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Roger
 * @description: 分页内容
 * @date: 2020/8/20 11:26 下午
 */
@Getter
@Setter
public class PageContent<T> {
    /**
     * 总条数
     */
    private long total;
    /**
     * 分页内容
     */
    private List<T> items;

    public PageContent(long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
