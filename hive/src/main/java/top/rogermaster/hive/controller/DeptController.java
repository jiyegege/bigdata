package top.rogermaster.hive.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hive.service.DeptService;

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

    @ApiOperation("查询所有部门")
    @GetMapping("/departments")
    public ResponseEntity<Object> getDepts(@RequestParam(required = false) @NotNull(message = "offset不能为空") @ApiParam("当前页") Integer offset,
                                           @RequestParam(required = false) @NotNull(message = "limit不能为空") @ApiParam("分页大小") Integer limit) {
        log.info("DeptController：getEmployeres：分页获取所有部门，参数：offset:{}, limit:{}", offset, limit);
        ResultVo<Object> resultVo = deptService.deptService(offset, limit);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }
}
