package top.rogermaster.hive.entity.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Roger
 * @description: 部门实体类
 * @date: 2020/8/20 9:57 下午
 */
@ApiModel(value = "top-rogermaster-hive-Dept")
@Data
@TableName("dept")
public class Dept {
    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号")
    @TableField("deptno")
    private Integer deptno;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @TableField("dname")
    private Object dname;

    /**
     * 部门所在的城市
     */
    @ApiModelProperty(value = "部门所在的城市")
    @TableField("loc")
    private Object loc;
}