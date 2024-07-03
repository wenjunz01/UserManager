package com.example.controller;

import com.example.common.Result;
import com.example.entity.FollowUp;
import com.example.entity.PublicCustomer;
import com.example.service.FollowUpService;
import com.example.service.PublicCustomerService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/followup")
public class FollowUpController {

    @Resource
    private FollowUpService followUpService;

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        followUpService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        followUpService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        FollowUp followUp = followUpService.selectById(id);
        return Result.success(followUp);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(FollowUp followUp) {
        List<FollowUp> list = followUpService.selectAll(followUp);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(FollowUp followUp,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<FollowUp> page = followUpService.selectPage(followUp, pageNum, pageSize);
        return Result.success(page);
    }


}
