package com.example.service;

import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.entity.Sale;
import com.example.exception.CustomException;
import com.example.mapper.SaleMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品销售业务处理
 **/
@Service
public class SaleService {

    @Resource
    private SaleMapper saleMapper;

    @Resource
    private GoodsService goodsService;

    /**
     * 新增
     */
    @Transactional
    public void add(Sale sale) {
        Account currentUser = TokenUtils.getCurrentUser();
        sale.setUser(currentUser.getName());
        Goods goods = goodsService.selectById(sale.getGoodsId());
        sale.setUnit(goods.getUnit() + "/" + goods.getPack());
        sale.setTotal(sale.getPrice() * sale.getNum());
        saleMapper.insert(sale);

        // 产品数量变化
        int num = goods.getNum() - sale.getNum();
        if (num < 0) {
            throw new CustomException(ResultCodeEnum.GOODS_LACK);
        }
        goods.setNum(num);
        goodsService.updateById(goods);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        saleMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            saleMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Sale sale) {
        Account currentUser = TokenUtils.getCurrentUser();
        sale.setUser(currentUser.getName());
        sale.setTotal(sale.getPrice() * sale.getNum());
        saleMapper.updateById(sale);
    }

    /**
     * 根据ID查询
     */
    public Sale selectById(Integer id) {
        return saleMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Sale> selectAll(Sale sale) {
        return saleMapper.selectAll(sale);
    }

    /**
     * 分页查询
     */
    public PageInfo<Sale> selectPage(Sale sale, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Sale> list = saleMapper.selectAll(sale);
        return PageInfo.of(list);
    }

}