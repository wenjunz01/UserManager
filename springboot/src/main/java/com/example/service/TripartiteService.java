package com.example.service;

import com.example.entity.Tripartite;
import com.example.mapper.TripartiteMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 第三方表业务处理
 **/
@Service
public class TripartiteService {

    @Resource
    private TripartiteMapper tripartiteMapper;

    /**
     * 新增
     */
    public void add(Tripartite tripartite) {
        tripartiteMapper.insert(tripartite);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        tripartiteMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            tripartiteMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Tripartite tripartite) {
        tripartiteMapper.updateById(tripartite);
    }

    /**
     * 根据ID查询
     */
    public Tripartite selectById(Integer id) {
        return tripartiteMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Tripartite> selectAll(Tripartite tripartite) {
        return tripartiteMapper.selectAll(tripartite);
    }

    /**
     * 分页查询
     */
    public PageInfo<Tripartite> selectPage(Tripartite tripartite, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tripartite> list = tripartiteMapper.selectAll(tripartite);
        return PageInfo.of(list);
    }

}