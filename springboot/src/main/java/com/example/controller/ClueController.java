package com.example.controller;

import com.example.common.Result;
import com.example.entity.Clue;
import com.example.service.ClueService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 线索表前端操作接口
 **/
@RestController
@RequestMapping("/clue")
public class ClueController {

    @Resource
    private ClueService clueService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Clue clue) {
        clueService.add(clue);
        return Result.success();
    }
    /**
     * 跟进
     */
    @PostMapping("/flowup")
    public Result flowup(@RequestBody Clue clue) {
        clueService.flowup(clue);
        return Result.success();
    }

    /**
     * 转换
     */
    @PostMapping("/convert")
    public Result convert(@RequestBody Clue clue) {
        clueService.flowup(clue);
        return Result.success();
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        clueService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        clueService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Clue clue) {
        clueService.updateById(clue);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Clue clue = clueService.selectById(id);
        return Result.success(clue);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Clue clue) {
        List<Clue> list = clueService.selectAll(clue);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Clue clue,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Clue> page = clueService.selectPage(clue, pageNum, pageSize);
        return Result.success(page);
    }

}
