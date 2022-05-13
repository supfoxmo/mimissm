package com.foxmo.service.impl;

import com.foxmo.mapper.ProductInfoMapper;
import com.foxmo.pojo.Admin;
import com.foxmo.pojo.ProductInfo;
import com.foxmo.pojo.ProductInfoExample;
import com.foxmo.pojo.vo.ProductInfoVo;
import com.foxmo.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoMapper productInfoMapper;
    private ProductInfo info;

    @Override
    public List<ProductInfo> getAll() {
        List<ProductInfo> infoList = productInfoMapper.selectByExample(new ProductInfoExample());
        return infoList;
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper工具类完成分页设置
        PageHelper.startPage(pageNum, pageSize);

        //进行PageInfo的数据封装
        //进行有条件的数据查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按主键降序排序
        example.setOrderByClause("p_id desc");
        //设置完排序后，取集合，切记：一定在取集合之前，设置PageHelper.startPage
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查询到的集合封装进PageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(pid);
        return productInfo;
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo,int pageSize) {
        //取出集合前，先要设置Pagehelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<ProductInfo>(list);
    }
}
