package top.rogermaster.hive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("top.rogermaster.hive.mapper")
public class HiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiveApplication.class, args);
    }

}
