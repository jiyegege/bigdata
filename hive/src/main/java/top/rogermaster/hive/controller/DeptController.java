package top.rogermaster.hive.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hive.entity.param.AddedDept;
import top.rogermaster.hive.service.DeptService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: Roger
 * @description: 部门Controller
 * @date: 2020/8/20 11:41 下午
 */
@RestController
@Slf4j
@Api(tags = "部门管理")
@Validated
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询所有部门
     *
     * @return 所有部门
     */
    @ApiOperation("查询所有部门")
    @GetMapping("/departments")
    public ResponseEntity<Object> getDepts() {
        log.info("DeptController：getDepts：获取所有部门");
        ResultVo<Object> resultVo = deptService.deptService();
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据部门编号查询部门
     *
     * @param deptno 部门编号
     * @return 部门信息
     */
    @ApiOperation("根据部门编号查询部门")
    @GetMapping("/department/{deptno}")
    public ResponseEntity<Object> getDeptWithId(@PathVariable @NotNull(message = "deptno不能为空") @ApiParam("部门编号") Integer deptno) {
        log.info("DeptController：getDepts：根据部门编号查询部门，参数：deptno:{}", deptno);
        ResultVo<Object> resultVo = deptService.getDeptWithId(deptno);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 新增部门
     *
     * @param addedDept 新增参数
     * @return 部门信息
     */
    @ApiOperation("根据部门编号查询部门")
    @PostMapping("/department")
    public ResponseEntity<Object> addDept(@RequestBody @Valid AddedDept addedDept) {
        log.info("DeptController：addDept：新增部门，参数：addedDept:{}", JSONObject.wrap(addedDept));
        ResultVo<Object> resultVo = deptService.addDept(addedDept);
        return new ResponseEntity<>(resultVo, HttpStatus.CREATED);
    }
}
