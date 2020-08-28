package top.rogermaster.hdfs.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.rogermaster.common.common.ResultCode;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.common.exception.CustomClientException;
import top.rogermaster.common.exception.CustomServiceException;
import top.rogermaster.hdfs.config.HdfsConf;
import top.rogermaster.hdfs.utils.HdfsUtil;

import java.util.List;
import java.util.Map;

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

    /**
     * 创建文件夹
     *
     * @param path 路径
     * @return 创建信息
     * @throws Exception
     */
    public ResultVo mkdir(String path) throws Exception {
        log.info("HdfsService：mkdir：创建文件夹");
        ResultVo resultVo = new ResultVo();
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        boolean isOk = HdfsUtil.mkdir(path);
        if (isOk) {
            log.info("文件夹创建成功");
            resultVo.setCode(ResultCode.SUCCESS.getCode());
            resultVo.setMsg(ResultCode.SUCCESS.getMessage());
        } else {
            log.info("文件夹创建失败");
            resultVo.setCode(ResultCode.FAILED.getCode());
            resultVo.setMsg(ResultCode.FAILED.getMessage());
        }
        fileSystem.close();
        return resultVo;
    }

    /**
     * 获取文件夹信息
     *
     * @param path 路径
     * @return 文件夹信息
     * @throws Exception
     */
    public ResultVo readPathInfo(String path) throws Exception {
        log.info("HdfsService：readPathInfo：获取文件夹信息");
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        if (fileSystem.exists(new Path(path))) {
            List<Map<String, Object>> list = HdfsUtil.readPathInfo(path);
            fileSystem.close();
            return new ResultVo(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list);
        } else {
            fileSystem.close();
            log.info("hdfs上不存在路径:{}", path);
            throw new CustomClientException(String.format("hdfs上不存在路径:%s", path));
        }
    }

    /**
     * 上传文件
     *
     * @param path 路径
     * @return 200
     * @throws Exception
     */
    public ResultVo uploadFile(String path, MultipartFile file) throws Exception {
        log.info("HdfsService：uploadFile：上传文件");
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        HdfsUtil.createFile(path, file);
        fileSystem.close();
        return new ResultVo(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 读取文件列表
     *
     * @param path 文件夹路径
     * @return 文件列表
     * @throws Exception
     */
    public ResultVo listFile(String path) throws Exception {
        log.info("HdfsService：listFile：读取文件列表");
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        if (fileSystem.exists(new Path(path))) {
            List<Map<String, String>> returnList = HdfsUtil.listFile(path);
            fileSystem.close();
            return new ResultVo(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), returnList);
        } else {
            fileSystem.close();
            log.info("hdfs上不存在路径:{}", path);
            throw new CustomClientException(String.format("hdfs上不存在路径:%s", path));
        }
    }

    /**
     * 重命名文件
     *
     * @param oldName 旧的文件路径
     * @param newName 新的文件路径
     * @return 200
     * @throws Exception
     */
    public ResultVo renameFile(String oldName, String newName) throws Exception {
        log.info("HdfsService：renameFile：重命名文件");
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        boolean isOk = HdfsUtil.renameFile(oldName, newName);
        if (isOk) {
            log.info("文件重命名成功");
            fileSystem.close();
            return new ResultVo(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
        } else {
            log.info("文件重命名失败");
            fileSystem.close();
            throw new CustomServiceException("文件重命名失败");
        }
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 文件或文件夹路径
     * @return
     * @throws Exception
     */
    public ResultVo deleteFile(String path) throws Exception {
        log.info("HdfsService：deleteFile：删除文件或文件夹");
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        boolean isOk = HdfsUtil.deleteFile(path);
        if (isOk) {
            log.info("文件或文件夹删除成功");
            fileSystem.close();
            return new ResultVo(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
        } else {
            log.info("文件或文件夹删除失败");
            fileSystem.close();
            throw new CustomServiceException("文件或文件夹删除失败");
        }
    }

    /**
     * 下载文件
     *
     * @param path 文件路径
     * @return 文件流
     * @throws Exception
     */
    public byte[] downloadFile(String path) throws Exception {
        log.info("HdfsService：downloadFile：下载文件");
        FileSystem fileSystem = HdfsUtil.getFileSystem(hdfsConf.getHost(), hdfsConf.getUser(), hdfsConf.getPort());
        byte[] bytes = HdfsUtil.openFileToBytes(path);
        fileSystem.close();
        return bytes;
    }
}
