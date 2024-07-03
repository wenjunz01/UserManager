package com.example.service;

import com.example.entity.Account;
import com.example.entity.Contract;
import com.example.mapper.ContractMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * 合同表业务处理
 **/
@Service
public class ContractService {

    @Resource
    private ContractMapper contractMapper;

    /**
     * 新增
     */
    public void add(Contract contract) {
        contractMapper.insert(contract);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        contractMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            contractMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Contract contract) {
        contractMapper.updateById(contract);
    }

    /**
     * 根据ID查询
     */
    public Contract selectById(Integer id) {
        return contractMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Contract> selectAll(Contract contract) {
        return contractMapper.selectAll(contract);
    }

    /**
     * 分页查询
     */
    public PageInfo<Contract> selectPage(Contract contract, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Contract> list = contractMapper.selectAll(contract);
        return PageInfo.of(list);
    }
}
