package com.foxmo.service.impl;

import com.foxmo.mapper.ProductTypeMapper;
import com.foxmo.pojo.ProductType;
import com.foxmo.pojo.ProductTypeExample;
import com.foxmo.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {
        List<ProductType> typeList = productTypeMapper.selectByExample(new ProductTypeExample());
        return typeList;
    }
}
