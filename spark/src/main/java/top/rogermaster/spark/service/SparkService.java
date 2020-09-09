package top.rogermaster.spark.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.rogermaster.common.common.ResultCode;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.spark.entity.Count;
import top.rogermaster.spark.entity.Word;
import top.rogermaster.spark.utils.SparkUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Roger
 * @description: Spark业务逻辑层
 * @date: 2020/9/3 1:35 下午
 */
@Service
@Slf4j
public class SparkService {
    @Value("${spark.master.uri}")
    private String masterUri;

    public ResultVo count() {
        SparkConf sparkConf = new SparkConf().setAppName("word count").setMaster(masterUri);
        SparkSession sparkSession = SparkUtil.sparkSession(sparkConf);
        log.info("SparkController：count：统计单词数");
        String input = "hello world hello hello hello";
        String[] _words = input.split(" ");
        List<Word> words = Arrays.stream(_words).map(Word::new).collect(Collectors.toList());
        //通过对象创建DataFram，由内部数据集创建
        Dataset<Row> dataFrame = sparkSession.createDataFrame(words, Word.class);
        //展示数据帧
        dataFrame.show();
        //dataFrame通过word分组
        RelationalGroupedDataset groupedDataset = dataFrame.groupBy(new Column("word"));
        //分组后的数据统计展示
        groupedDataset.count().show();
        //获取行数据
        List<Row> rows = groupedDataset.count().collectAsList();
        List<Count> counts = rows.stream().map(row -> new Count(row.getString(0), row.getLong(1))).collect(Collectors.toList());
        sparkSession.close();
        return new ResultVo(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), counts);
    }
}
