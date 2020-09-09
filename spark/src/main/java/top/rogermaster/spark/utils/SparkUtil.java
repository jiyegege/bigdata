package top.rogermaster.spark.utils;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

/**
 * @Author: Roger
 * @description: spark工具类
 * @date: 2020/9/9 4:42 下午
 */
public class SparkUtil {

    public static SparkConf sparkConf(String appName, String masterUri) {
        SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setMaster(masterUri);

        return sparkConf;
    }

    public static JavaSparkContext javaSparkContext(SparkConf sparkConf) {
        return new JavaSparkContext(sparkConf);
    }

    public static SparkSession sparkSession(SparkConf sparkConf) {
        return SparkSession
                .builder()
                .sparkContext(javaSparkContext(sparkConf).sc())
                .appName("Integrating Spring-boot with Apache Spark")
                .getOrCreate();
    }
}
