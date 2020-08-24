package top.rogermaster.hive.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Roger
 * @description: 新增部门参数类
 * @date: 2020/8/24 10:01 下午
 */
@Data
@ApiModel(value = "新增部门参数类")
public class AddedDept {
    /**
     * 部门名称
     */
    @NotNull(message = "dname不能为空")
    @ApiModelProperty(value = "部门名称")
    private Object dname;

    /**
     * 部门所在的城市
     */
    @NotNull(message = "loc不能为空")
    @ApiModelProperty(value = "部门所在的城市")
    private Object loc;
}
