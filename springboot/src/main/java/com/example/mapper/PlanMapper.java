package com.example.mapper;

import com.example.entity.Plan;

import java.util.List;

public interface PlanMapper {

    /**
     * 新增
     */
    int insert(Plan plan);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Plan plan);

    /**
     * 根据ID查询
     */
    Plan selectById(Integer id);

    /**
     * 查询所有
     */
    List<Plan> selectAll(Plan plan);
}
