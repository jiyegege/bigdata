package top.rogermaster.hive.entity.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Roger
 * @description: 雇员实体类
 * @date: 2020/8/20 9:57 下午
 */
@ApiModel(value = "top-rogermaster-hive-Emp")
@Data
@TableName("emp")
public class Emp {
    @ApiModelProperty(value = "员工表编号")
    @TableField("empno")
    private Integer empno;

    @ApiModelProperty(value = "员工姓名")
    @TableField("ename")
    private Object ename;

    @ApiModelProperty(value = "职位类型")
    @TableField("job")
    private Object job;

    @ApiModelProperty(value = "")
    @TableField("mgr")
    private Integer mgr;

    @ApiModelProperty(value = "雇佣日期")
    @TableField("hiredate")
    private Date hiredate;

    @ApiModelProperty(value = "工资")
    @TableField("sal")
    private BigDecimal sal;

    @ApiModelProperty(value = "")
    @TableField("comm")
    private BigDecimal comm;

    @ApiModelProperty(value = "部门编号")
    @TableField("deptno")
    private Integer deptno;
}