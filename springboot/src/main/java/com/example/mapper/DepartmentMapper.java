package com.example.mapper;

import com.example.entity.Department;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 操作department相关数据接口
*/
public interface DepartmentMapper {

    /**
      * 新增
    */
    int insert(Department department);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Department department);

    /**
      * 根据ID查询
    */
    Department selectById(Integer id);

    /**
      * 查询所有
    */
    List<Department> selectAll(Department department);

    @Delete("delete from department where root_id = #{rootId}")
    void deleteByRootId(Integer rootId);

    @Delete("delete from department where pid = #{pid}")
    void deleteByPid(Integer pid);
}