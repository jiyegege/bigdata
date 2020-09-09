package top.rogermaster.spark.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.spark.service.SparkService;

/**
 * @Author: Roger
 * @description: Spark Controller
 * @date: 2020/9/3 1:34 下午
 */
@RestController
@Slf4j
@Api(tags = "Spark管理")
@Validated
public class SparkController {
    @Autowired
    private SparkService sparkService;

    /**
     * 统计单词数
     *
     * @return 200
     */
    @ApiOperation("创建文件夹")
    @PostMapping(value = "/count")
    public ResponseEntity count() {
        log.info("SparkController：count：开始统计单词数");
        ResultVo resultVo = sparkService.count();
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }
}
