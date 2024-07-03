package com.example.mapper;

import com.example.entity.Contract;
import java.util.List;

public interface ContractMapper {
    /**
     * 新增
     */
    int insert(Contract contract);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Contract contract);

    /**
     * 根据ID查询
     */
    Contract selectById(Integer id);

    /**
     * 查询所有
     */
    List<Contract> selectAll(Contract contract);
}
