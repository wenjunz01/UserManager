package com.example.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.entity.Department;
import com.example.mapper.DepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门表业务处理
 **/
@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 新增
     */
    public void add(Department department) {
        Integer pid = department.getPid();
        if (ObjectUtil.isNotNull(pid)) {
            Department parent = departmentMapper.selectById(pid);
            if (ObjectUtil.isNotNull(parent.getRootId())) {
                department.setRootId(parent.getRootId());  // 父节点是二级节点
            } else {
                department.setRootId(parent.getId());  // 父节点是根节点
            }
        }
        departmentMapper.insert(department);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        Department department = selectById(id);
        if (ObjectUtil.isNull(department.getPid())) {
            // 如果是根节点，根据根节点删除所有子节点
            departmentMapper.deleteByRootId(department.getId());
        }
        departmentMapper.deleteById(id);
        departmentMapper.deleteByPid(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Department department) {
        departmentMapper.updateById(department);
    }

    /**
     * 根据ID查询
     */
    public Department selectById(Integer id) {
        return departmentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Department> selectTree(Department department) {
        List<Department> departmentList = departmentMapper.selectAll(department);
        // 设置三级部门
        // 第一级
        List<Department> first = departmentList.stream().filter(d -> ObjectUtil.isNull(d.getPid())).collect(Collectors.toList());
        for (Department f : first) {
            List<Department> second = departmentList.stream().filter(d -> f.getId().equals(d.getPid())).collect(Collectors.toList());
            f.setChildren(second);
            for (Department s : second) {
                List<Department> third = departmentList.stream().filter(d -> s.getId().equals(d.getPid())).collect(Collectors.toList());
                s.setChildren(third);
            }
        }
        return first;
    }

    public List<Department> selectAll(Department department) {
        return departmentMapper.selectAll(department);
    }

    /**
     * 分页查询
     */
    public PageInfo<Department> selectPage(Department department, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Department> list = departmentMapper.selectAll(department);
        return PageInfo.of(list);
    }

}