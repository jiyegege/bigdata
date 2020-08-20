package top.rogermaster.hive.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.rogermaster.common.common.PageContent;
import top.rogermaster.common.common.ResultCode;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hive.entity.data.Emp;
import top.rogermaster.hive.mapper.EmpMapper;

import java.util.List;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * @Author: Roger
 * @description: 雇员Service
 * @date: 2020/8/20 10:45 下午
 */
@Service
@Slf4j
public class EmpService extends ServiceImpl<EmpMapper, Emp> {
    @Autowired
    private EmpMapper empMapper;

    public ResultVo<Object> getAllEmployeres(Integer offset, Integer limit) {
        log.info("EmpService：getAllEmployeres：开始分页查询雇员信息");
        Page<Object> page = startPage(offset, limit);
        List<Emp> empList = empMapper.selectList(new QueryWrapper<>());
        PageContent<Emp> pageContent = new PageContent<>(page.getTotal(), empList);
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), pageContent);
    }
}
