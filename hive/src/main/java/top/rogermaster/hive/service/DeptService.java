package top.rogermaster.hive.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.rogermaster.common.common.ResultCode;
import top.rogermaster.common.common.ResultVo;
import top.rogermaster.hive.entity.data.Dept;
import top.rogermaster.hive.entity.param.AddedDept;
import top.rogermaster.hive.mapper.DeptMapper;

import java.util.List;

/**
 * @Author: Roger
 * @description: 部门Service
 * @date: 2020/8/20 10:48 下午
 */
@Service
@Slf4j
public class DeptService extends ServiceImpl<DeptMapper, Dept> {
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 获取所有的部门信息
     *
     * @return 所有的部门信息
     */
    public ResultVo<Object> deptService() {
        log.info("DeptService：deptService：开始查询所有部门信息");
        List<Dept> deptList = list();
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), deptList);
    }

    public ResultVo<Object> getDeptWithId(Integer deptno) {
        log.info("DeptService：getDeptWithId：开始根据部门编号获取部门信息");
        Dept dept = deptMapper.selectOne(new QueryWrapper<Dept>().eq("deptno", deptno));
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), dept);
    }

    public ResultVo<Object> addDept(AddedDept addedDept) {
        Dept dept = new Dept();
        dept.setDname(addedDept.getDname());
        dept.setLoc(addedDept.getLoc());
        log.info("DeptService：addDept：开始插入部门信息");
        deptMapper.insert(dept);
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }
}
