package top.rogermaster.hdfs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hdfs.service.HdfsService;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: Roger
 * @description: HdfsController
 * @date: 2020/8/26 11:06 下午
 */
@RestController
@Slf4j
@Api(tags = "Hdfs管理")
@Validated
public class HdfsController {
    @Autowired
    private HdfsService hdfsService;

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    @ApiOperation("创建文件夹")
    @PostMapping(value = "/mkdir")
    @ResponseBody
    public ResponseEntity mkdir(@RequestParam("path") @NotEmpty(message = "path不能为空") @ApiParam("文件夹地址") String path) throws Exception {
        log.info("HdfsController：mkdir：开始创建文件夹,参数:{}", path);
        ResultVo resultVo = hdfsService.mkdir(path);
        return new ResponseEntity<ResultVo>(resultVo, HttpStatus.OK);
    }
}
