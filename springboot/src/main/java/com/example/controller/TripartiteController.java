package com.example.controller;

import com.example.common.Result;
import com.example.entity.Tripartite;
import com.example.service.TripartiteService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 第三方表前端操作接口
 **/
@RestController
@RequestMapping("/tripartite")
public class TripartiteController {

    @Resource
    private TripartiteService tripartiteService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Tripartite tripartite) {
        tripartiteService.add(tripartite);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        tripartiteService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        tripartiteService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Tripartite tripartite) {
        tripartiteService.updateById(tripartite);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Tripartite tripartite = tripartiteService.selectById(id);
        return Result.success(tripartite);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Tripartite tripartite) {
        List<Tripartite> list = tripartiteService.selectAll(tripartite);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Tripartite tripartite,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Tripartite> page = tripartiteService.selectPage(tripartite, pageNum, pageSize);
        return Result.success(page);
    }

}