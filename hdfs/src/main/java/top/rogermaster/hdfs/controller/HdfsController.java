package top.rogermaster.hdfs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hdfs.service.HdfsService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
     * @param path 路径
     * @return 200
     * @throws Exception
     */
    @ApiOperation("创建文件夹")
    @PostMapping(value = "/folder:create")
    public ResponseEntity mkdir(@RequestParam("path") @NotEmpty(message = "path不能为空") @ApiParam("文件夹路径") String path) throws Exception {
        log.info("HdfsController：mkdir：开始创建文件夹,参数:{}", path);
        ResultVo resultVo = hdfsService.mkdir(path);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 读取文件夹信息
     *
     * @param path 路径
     * @return 文件夹信息
     * @throws Exception
     */
    @ApiOperation("读取文件夹信息")
    @GetMapping("/folder/info")
    public ResponseEntity readPathInfo(@RequestParam("path") @NotEmpty(message = "path不能为空") @ApiParam("文件夹路径") String path) throws Exception {
        log.info("HdfsController：mkdir：开始读取文件夹信息,参数:{}", path);
        ResultVo resultVo = hdfsService.readPathInfo(path);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }


    /**
     * 上传文件
     *
     * @param path 路径
     * @return 200
     * @throws Exception
     */
    @ApiOperation("上传文件")
    @PostMapping("/folder/file:upload")
    public ResponseEntity uploadFile(@RequestParam("path") @NotEmpty(message = "path不能为空") @ApiParam("文件夹路径") String path,
                                     @RequestParam("file") @NotNull(message = "file不能为空") @ApiParam("文件") MultipartFile file) throws Exception {
        log.info("HdfsController：createFile：开始上传文件,参数:{}", path);
        ResultVo resultVo = hdfsService.uploadFile(path, file);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 读取文件列表
     *
     * @param path 文件夹路径
     * @return 文件列表
     * @throws Exception
     */
    @ApiOperation("读取文件列表")
    @GetMapping("/folder/files")
    public ResponseEntity listFile(@RequestParam("path") @NotEmpty(message = "path不能为空") @ApiParam("文件夹路径") String path) throws Exception {
        log.info("HdfsController：createFile：开始读取文件列表,参数:{}", path);
        ResultVo resultVo = hdfsService.listFile(path);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 重命名文件
     *
     * @param oldName 旧的文件路径
     * @param newName 新的文件路径
     * @return 200
     * @throws Exception
     */
    @ApiOperation("重命名文件")
    @GetMapping("/folder/file:rename")
    public ResponseEntity renameFile(@RequestParam("oldName") @NotEmpty(message = "oldName不能为空") @ApiParam("旧的文件路径") String oldName,
                                     @RequestParam("newName") @NotEmpty(message = "newName不能为空") @ApiParam("新的文件路径") String newName)
            throws Exception {
        ResultVo resultVo = hdfsService.renameFile(oldName, newName);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 文件或文件夹路径
     * @return
     * @throws Exception
     */
    @ApiOperation("删除文件或文件夹")
    @GetMapping("/folder/file:delete")
    public ResponseEntity deleteFile(@RequestParam("path") @ApiParam("文件或文件夹路径") String path) throws Exception {
        ResultVo resultVo = hdfsService.deleteFile(path);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 下载文件
     *
     * @param path 文件路径
     * @return 文件流
     * @throws Exception
     */
    @ApiOperation("下载文件")
    @GetMapping("/folder/file:download")
    public ResponseEntity downloadFile(@RequestParam("path") @ApiParam("文件路径") String path)
            throws Exception {
        String[] pathSplit = path.split("/");
        String fileName = pathSplit[pathSplit.length - 1];
        byte[] bytes = hdfsService.downloadFile(path);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(bytes);
    }
}
