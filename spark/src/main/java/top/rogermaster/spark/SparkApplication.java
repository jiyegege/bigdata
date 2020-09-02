package top.rogermaster.spark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Roger
 * @description: 启动类
 * @date: 2020/9/2 10:45 下午
 */

@EnableSwagger2
@SpringBootApplication
public class SparkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SparkApplication.class, args);
    }
}
