package com.foxmo.listener;

import com.foxmo.pojo.ProductType;
import com.foxmo.service.ProductTypeService;
import com.foxmo.service.impl.ProductTypeServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手动从Spring容器中取出ProductTypeServiceimpl的对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ProductTypeService productTypeService = applicationContext.getBean("ProductTypeServiceImpl", ProductTypeService.class);
        List<ProductType> typeList = productTypeService.getAll();

        //放入全局应用作用域中，供新增页面，修改页面，前台的查询功能提供全部商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
