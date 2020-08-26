package top.rogermaster.hdfs.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.rogermaster.common.common.ResultCode;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hdfs.config.HdfsConf;
import top.rogermaster.hdfs.utils.HdfsUtil;

/**
 * @Author: Roger
 * @description: Hdfs业务逻辑层
 * @date: 2020/8/26 11:21 下午
 */
@Service
@Slf4j
public class HdfsService {
    @Autowired
    private HdfsConf hdfsConf;

    public ResultVo mkdir(String path) throws Exception {
        log.info("HdfsService：mkdir：创建文件夹");
        ResultVo resultVo = new ResultVo();
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        boolean isOk = HdfsUtil.mkdir(path);
        if (isOk) {
            log.info("文件夹创建成功");
            resultVo.setCode(ResultCode.SUCCESS.getCode());
            resultVo.setMsg(ResultCode.SUCCESS.getMessage());
            fileSystem.close();
            return resultVo;
        } else {
            log.info("文件夹创建失败");
            resultVo.setCode(ResultCode.FAILED.getCode());
            resultVo.setMsg(ResultCode.FAILED.getMessage());
            fileSystem.close();
            return resultVo;
        }
    }
}
