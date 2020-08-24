package top.rogermaster.hive.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hive.service.EmpService;

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

    /**
     * 获取所有的雇员
     *
     * @return 所有的雇员
     */
    @ApiOperation("查询所有雇员")
    @GetMapping("/employeres")
    public ResponseEntity<Object> getEmployeres() {
        log.info("EmpController：getEmployeres：获取所有雇员");
        ResultVo<Object> resultVo = empService.getAllEmployeres();
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }
}
