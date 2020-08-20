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
import top.rogermaster.hive.service.EmpService;

import javax.validation.constraints.NotNull;

/**
 * @Author: Roger
 * @description: 雇员Controller
 * @date: 2020/8/20 9:57 下午
 */
@RestController
@Slf4j
@Api(tags = "雇员管理")
@Validated
public class EmpController {
    @Autowired
    private EmpService empService;

    @ApiOperation("查询所有雇员")
    @GetMapping("/employeres")
    public ResponseEntity<Object> getEmployeres(@RequestParam(required = false) @NotNull(message = "offset不能为空") @ApiParam("当前页") Integer offset,
                                                @RequestParam(required = false) @NotNull(message = "limit不能为空") @ApiParam("分页大小") Integer limit) {
        log.info("EmpController：getEmployeres：分页获取所有雇员，参数：offset:{}, limit:{}", offset, limit);
        ResultVo<Object> resultVo = empService.getAllEmployeres(offset, limit);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }
}
