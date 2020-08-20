package top.rogermaster.hive.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hive.entity.data.Dept;
import top.rogermaster.hive.mapper.DeptMapper;

/**
 * @Author: Roger
 * @description: 部门Service
 * @date: 2020/8/20 10:48 下午
 */
@Service
@Slf4j
public class DeptService extends ServiceImpl<DeptMapper, Dept> {
    public ResultVo<Object> deptService(Integer offset, Integer limit) {
        return null;
    }
}
