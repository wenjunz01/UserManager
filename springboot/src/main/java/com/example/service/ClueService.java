package com.example.service;

import com.example.entity.Account;
import com.example.entity.Clue;
import com.example.entity.PublicCustomer;
import com.example.mapper.ClueMapper;
import com.example.mapper.PublicCustomerMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClueService {
    @Resource
    private ClueMapper clueMapper;

    /**
     * 新增
     */
    public void add(Clue clue) {
        clueMapper.insert(clue);
    }
    /**
     * 跟进
     */
    public void flowup(Clue clue) {
        clueMapper.flowup(clue);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        clueMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            clueMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Clue clue) {
        clueMapper.updateById(clue);
    }

    /**
     * 根据ID查询
     */
    public Clue selectById(Integer id) {
        return clueMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Clue> selectAll(Clue clue) {
        return clueMapper.selectAll(clue);
    }

    /**
     * 分页查询
     */
    public PageInfo<Clue> selectPage(Clue clue, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Clue> list = clueMapper.selectAll(clue);
        return PageInfo.of(list);
    }
}
