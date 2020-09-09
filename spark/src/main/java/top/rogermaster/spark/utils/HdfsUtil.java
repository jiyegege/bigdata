package top.rogermaster.spark.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.rogermaster.common.exception.CustomClientException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Roger
 * @description: Hdfs工具类
 * @date: 2020/8/25 11:09 下午
 */
@Component
@Slf4j
public class HdfsUtil {
    private static final int BUFFER_SIZE = 1024 * 1024 * 64;
    private static FileSystem fileSystem;

    /**
     * 获取HDFS配置信息
     *
     * @return
     */
    private static Configuration getConfiguration(String hosts, String port, String clusterName) {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://" + clusterName);
        configuration.set("dfs.nameservices", "ns1");
        configuration.set("dfs.ha.namenodes.ns1", "nn1,nn2");
        String[] hostArray = hosts.split(",");
        if (hostArray.length != 0 && hostArray.length >= 2) {
            configuration.set(String.format("dfs.namenode.rpc-address.%s.nn1", clusterName), hostArray[0] + ":" + port);
            configuration.set(String.format("dfs.namenode.rpc-address.%s.nn2", clusterName), hostArray[1] + ":" + port);
        } else {
            configuration.set(String.format("dfs.namenode.rpc-address.%s.nn1", clusterName), hostArray[0] + ":" + port);
            configuration.set(String.format("dfs.namenode.rpc-address.%s.nn2", clusterName), hostArray[0] + ":" + port);
        }
        //conf.setBoolean(name, value);
        configuration.set("dfs.client.failover.proxy.provider.ns1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        return configuration;
    }

    /**
     * 获取HDFS文件系统对象
     *
     * @return
     * @throws Exception
     */
    public static FileSystem getFileSystem(String hosts, String user, String port, String clusterName) throws Exception {
        // 客户端去操作hdfs时是有一个用户身份的，默认情况下hdfs客户端api会从jvm中获取一个参数作为自己的用户身份
        // DHADOOP_USER_NAME=hadoop
        // 也可以在构造客户端fs对象时，通过参数传递进去
        String clusterURL = "hdfs://" + clusterName;
        FileSystem fileSystem = FileSystem.get(new URI(clusterURL), getConfiguration(hosts, port, clusterName), user);
        if (fileSystem != null) {
            HdfsUtil.fileSystem = fileSystem;
            return fileSystem;
        } else {
            log.info("Hadoop节点状态未激活");
            throw new CustomClientException("Hadoop节点状态未激活");
        }
    }

    /**
     * 在HDFS创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean mkdir(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (existFile(path)) {
            return true;
        }
        // 目标路径
        Path srcPath = new Path(path);
        boolean isOk = fileSystem.mkdirs(srcPath);
        fileSystem.close();
        return isOk;
    }

    /**
     * 判断HDFS文件是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean existFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        Path srcPath = new Path(path);
        boolean isExists = fileSystem.exists(srcPath);
        return isExists;
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> readPathInfo(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path newPath = new Path(path);
        FileStatus[] statusList = fileSystem.listStatus(newPath);
        List<Map<String, Object>> list = new ArrayList<>();
        if (null != statusList && statusList.length > 0) {
            for (FileStatus fileStatus : statusList) {
                Map<String, Object> map = new HashMap<>();
                map.put("filePath", fileStatus.getPath());
                map.put("fileStatus", fileStatus.toString());
                list.add(map);
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * HDFS创建文件
     *
     * @param path
     * @param file
     * @throws Exception
     */
    public static void createFile(String path, MultipartFile file) throws Exception {
        if (StringUtils.isEmpty(path) || null == file.getBytes()) {
            return;
        }
        String fileName = file.getOriginalFilename();

        // 上传时默认当前目录，后面自动拼接文件的目录
        Path newPath = new Path(path + "/" + fileName);
        // 打开一个输出流
        try (FSDataOutputStream outputStream = fileSystem.create(newPath)) {
            outputStream.write(file.getBytes());
        }
    }

    /**
     * 读取HDFS文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String readFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path srcPath = new Path(path);

        try (FSDataInputStream inputStream = fileSystem.open(srcPath)) {
            // 防止中文乱码
            StringBuffer sb;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String lineTxt = "";
                sb = new StringBuffer();
                while ((lineTxt = reader.readLine()) != null) {
                    sb.append(lineTxt);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 读取HDFS文件列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> listFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }

        // 目标路径
        Path srcPath = new Path(path);
        // 递归找到所有文件
        RemoteIterator<LocatedFileStatus> filesList = fileSystem.listFiles(srcPath, true);
        List<Map<String, String>> returnList = new ArrayList<>();
        while (filesList.hasNext()) {
            LocatedFileStatus next = filesList.next();
            String fileName = next.getPath().getName();
            Path filePath = next.getPath();
            Map<String, String> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("filePath", filePath.toString());
            returnList.add(map);
        }
        return returnList;
    }

    /**
     * HDFS重命名文件
     *
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    public static boolean renameFile(String oldName, String newName) throws Exception {
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName)) {
            return false;
        }
        // 原文件目标路径
        Path oldPath = new Path(oldName);
        // 重命名目标路径
        Path newPath = new Path(newName);
        boolean isOk = fileSystem.rename(oldPath, newPath);
        return isOk;
    }

    /**
     * 删除HDFS文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean deleteFile(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (!existFile(path)) {
            return false;
        }
        Path srcPath = new Path(path);
        boolean isOk = fileSystem.deleteOnExit(srcPath);
        return isOk;
    }

    /**
     * 上传HDFS文件
     *
     * @param path
     * @param uploadPath
     * @throws Exception
     */
    public static void uploadFile(String path, String uploadPath) throws Exception {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath)) {
            return;
        }
        // 上传路径
        Path clientPath = new Path(path);
        // 目标路径
        Path serverPath = new Path(uploadPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fileSystem.copyFromLocalFile(false, clientPath, serverPath);
    }

    /**
     * 下载HDFS文件
     *
     * @param path
     * @param downloadPath
     * @throws Exception
     */
    public static void downloadFile(String path, String downloadPath) throws Exception {

        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(downloadPath)) {
            return;
        }
        // 上传路径
        Path clientPath = new Path(path);
        // 目标路径
        Path serverPath = new Path(downloadPath);

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fileSystem.copyToLocalFile(false, clientPath, serverPath);
    }

    /**
     * HDFS文件复制
     *
     * @param sourcePath
     * @param targetPath
     * @throws Exception
     */
    public static void copyFile(String sourcePath, String targetPath) throws Exception {
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath)) {
            return;
        }
        // 原始文件路径
        Path oldPath = new Path(sourcePath);
        // 目标路径
        Path newPath = new Path(targetPath);
        try (FSDataInputStream inputStream = fileSystem.open(oldPath);
             FSDataOutputStream outputStream = fileSystem.create(newPath);) {
            IOUtils.copyBytes(inputStream, outputStream, BUFFER_SIZE, false);
        }
    }

    /**
     * 打开HDFS上的文件并返回byte数组
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static byte[] openFileToBytes(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = fileSystem.open(srcPath);
        return IOUtils.readFullyToByteArray(inputStream);
    }

    /**
     * 打开HDFS上的文件并返回java对象
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static <T extends Object> T openFileToObject(String path, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        String jsonStr = readFile(path);
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, clazz);
    }

    /**
     * 获取某个文件在HDFS的集群位置
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static BlockLocation[] getFileBlockLocations(String path) throws Exception {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        if (!existFile(path)) {
            return null;
        }
        // 目标路径
        Path srcPath = new Path(path);
        FileStatus fileStatus = fileSystem.getFileStatus(srcPath);
        return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
    }

    /**
     * 关闭filesystem
     *
     * @throws IOException
     */
    public static void close() throws IOException {
        fileSystem.close();
    }
}
