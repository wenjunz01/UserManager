package com.example.service;

import com.example.entity.FollowUp;
import com.example.mapper.FollowUpMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FollowUpService {

    @Resource
    private FollowUpMapper followUpMapper;

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        followUpMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            followUpMapper.deleteById(id);
        }
    }
    /**
     * 根据ID查询
     */
    public FollowUp selectById(Integer id) {
        return followUpMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<FollowUp> selectAll(FollowUp followUp) {
        return followUpMapper.selectAll(followUp);
    }
    /**
     * 分页查询
     */
    public PageInfo<FollowUp> selectPage(FollowUp followUp, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FollowUp> list = followUpMapper.selectAll(followUp);
        return PageInfo.of(list);
    }
}
