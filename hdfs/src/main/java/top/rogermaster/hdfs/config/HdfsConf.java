package top.rogermaster.hdfs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Roger
 * @description: hdfs配置
 * @date: 2020/8/25 11:24 下午
 */
@Configuration
@Data
public class HdfsConf {
    @Value("${hdfs.host}")
    private String host;

    @Value("${hdfs.port}")
    private String port;

    @Value("${hdfs.user}")
    private String user;
}
