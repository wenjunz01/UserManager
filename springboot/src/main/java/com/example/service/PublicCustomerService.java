package com.example.service;

import com.example.entity.Account;
import com.example.entity.PublicCustomer;
import com.example.mapper.PublicCustomerMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class PublicCustomerService {

    @Resource
    private PublicCustomerMapper publicCustomerMapper;

    /**
     * 新增
     */
    public void add(PublicCustomer publiccustomer) {
        Account currentUser = TokenUtils.getCurrentUser();
        publiccustomer.setResponsible(currentUser.getName());
        publicCustomerMapper.insert(publiccustomer);
    }
    /**
     * 跟进
     */
    public void flowup(PublicCustomer publiccustomer) {
        publicCustomerMapper.flowup(publiccustomer);
    }
    /**
     * 删除
     */
    public void deleteById(Integer id) {
        publicCustomerMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            publicCustomerMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(PublicCustomer publiccustomer) {
        publicCustomerMapper.updateById(publiccustomer);
    }

    /**
     * 根据ID查询
     */
    public PublicCustomer selectById(Integer id) {
        return publicCustomerMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<PublicCustomer> selectAll(PublicCustomer publiccustomer) {
        return publicCustomerMapper.selectAll(publiccustomer);
    }

    /**
     * 分页查询
     */
    public PageInfo<PublicCustomer> selectPage(PublicCustomer publiccustomer, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PublicCustomer> list = publicCustomerMapper.selectAll(publiccustomer);
        return PageInfo.of(list);
    }
}
