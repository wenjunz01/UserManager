package com.example.mapper;

import com.example.entity.Clue;
import java.util.List;

/**
 * 操作Clue相关数据接口
 */
public interface ClueMapper {


    /**
     * 新增
     */
    int insert(Clue clue);
    /**
     * 跟进
     */
    int flowup(Clue clue);
    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Clue clue);

    /**
     * 根据ID查询
     */
    Clue selectById(Integer id);

    /**
     * 查询所有
     */
    List<Clue> selectAll(Clue clue);
}
