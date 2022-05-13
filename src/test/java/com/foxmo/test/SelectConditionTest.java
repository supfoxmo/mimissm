package com.foxmo.test;

import com.foxmo.mapper.ProductInfoMapper;
import com.foxmo.pojo.ProductInfo;
import com.foxmo.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml","classpath:spring-service.xml"})
public class SelectConditionTest {
    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void test1(){
        ProductInfoVo productInfoVo = new ProductInfoVo();
        productInfoVo.setPname("电视");
        productInfoVo.setLprice(3000);
        productInfoVo.setHprice(3999);

        List<ProductInfo> list = mapper.selectCondition(productInfoVo);
        for (ProductInfo productInfo : list) {
            System.out.println(productInfo);
        }
    }
}
