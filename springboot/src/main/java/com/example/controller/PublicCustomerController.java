package com.example.controller;


import com.example.common.Result;
import com.example.entity.PublicCustomer;
import com.example.service.PublicCustomerService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公海用户表前端操作接口
 **/
@RestController
@RequestMapping("/publiccustomer")
public class PublicCustomerController {

    @Resource
    private PublicCustomerService publiccustomerService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody PublicCustomer publiccustomer) {
        publiccustomerService.add(publiccustomer);
        return Result.success();
    }
    /**
     * 跟进
     */
    @PostMapping("/flowup")
    public Result flowup(@RequestBody PublicCustomer publiccustomer) {
        publiccustomerService.flowup(publiccustomer);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        publiccustomerService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        publiccustomerService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody PublicCustomer publiccustomer) {
        publiccustomerService.updateById(publiccustomer);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        PublicCustomer publiccustomer = publiccustomerService.selectById(id);
        return Result.success(publiccustomer);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(PublicCustomer publiccustomer) {
        List<PublicCustomer> list = publiccustomerService.selectAll(publiccustomer);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(PublicCustomer publiccustomer,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<PublicCustomer> page = publiccustomerService.selectPage(publiccustomer, pageNum, pageSize);
        return Result.success(page);
    }


}
