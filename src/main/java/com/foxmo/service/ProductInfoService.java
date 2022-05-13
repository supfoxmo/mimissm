package com.foxmo.service;

import com.foxmo.pojo.Admin;
import com.foxmo.pojo.ProductInfo;
import com.foxmo.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {
    /**
     * 查询所有数据
     * @return
     */
    public List<ProductInfo> getAll();

    /**
     * 事项分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo splitPage(int pageNum,int pageSize);

    /**
     * 增添商品
     * @param info
     * @return
     */
    public int save(ProductInfo info);

    /**
     * 查找指定id的纪录
     * @param pid
     * @return
     */
    public ProductInfo getById(int pid);

    public int update(ProductInfo info);
    //单独删除
    public int delete(int pid);
    //批量删除
    public int deleteBatch(String[] ids);

    //多条件查询功能
    public List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件分页查询
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo,int pageSize);

}
