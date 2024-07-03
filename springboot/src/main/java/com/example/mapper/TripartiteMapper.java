package com.example.mapper;

import com.example.entity.Tripartite;
import java.util.List;

/**
 * 操作Tripartite相关数据接口
*/
public interface TripartiteMapper {

    /**
      * 新增
    */
    int insert(Tripartite tripartite);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Tripartite tripartite);

    /**
      * 根据ID查询
    */
    Tripartite selectById(Integer id);

    /**
      * 查询所有
    */
    List<Tripartite> selectAll(Tripartite tripartite);

}