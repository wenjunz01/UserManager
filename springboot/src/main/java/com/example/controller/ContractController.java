package com.example.controller;

import com.example.common.Result;
import com.example.entity.Contract;
import com.example.entity.Goods;
import com.example.service.ContractService;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合同表前端操作接口
 **/
@RestController
@RequestMapping("/contract")
public class ContractController {

    @Resource
    private ContractService contractService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Contract contract) {
        contractService.add(contract);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        contractService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        contractService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Contract contract) {
        contractService.updateById(contract);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Contract contract = contractService.selectById(id);
        return Result.success(contract);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Contract contract) {
        List<Contract> list = contractService.selectAll(contract);
        return Result.success(list);
    }

    /**
     * 查询所有
     */
//    @GetMapping("/selectuccess")
//    public Result selectAll(Contract contract) {
//        List<Contract> list = contractService.selectAll(contract);
//        return Result.success(list);
//    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Contract contract,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<Contract> page = contractService.selectPage(contract, pageNum, pageSize);
        return Result.success(page);
    }
}
