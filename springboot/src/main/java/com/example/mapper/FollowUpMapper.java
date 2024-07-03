package com.example.mapper;

import com.example.entity.FollowUp;
import java.util.List;

/**
 * FollowUpMapper相关数据接口
 */
public interface FollowUpMapper {

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 根据ID查询
     */
    FollowUp selectById(Integer id);

    /**
     * 查询所有
     */
    List<FollowUp> selectAll(FollowUp followUp);
}
