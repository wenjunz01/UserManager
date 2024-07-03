package com.example.mapper;

import com.example.entity.PublicCustomer;
import java.util.List;

/**
 * 操作PublicCustomer相关数据接口
*/
public interface PublicCustomerMapper {

    /**
      * 新增
    */
    int insert(PublicCustomer publiccustomer);
    /**
     * 跟进
     */
    int flowup(PublicCustomer publiccustomer);
    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(PublicCustomer publiccustomer);

    /**
      * 根据ID查询
    */
    PublicCustomer selectById(Integer id);

    /**
      * 查询所有
    */
    List<PublicCustomer> selectAll(PublicCustomer publiccustomer);

}