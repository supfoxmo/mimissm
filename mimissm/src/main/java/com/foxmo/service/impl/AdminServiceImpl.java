package com.foxmo.service.impl;

import com.foxmo.mapper.AdminMapper;
import com.foxmo.pojo.Admin;
import com.foxmo.pojo.AdminExample;
import com.foxmo.service.AdminService;
import com.foxmo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(String name, String pwd) {
        //根据闯入的用户信息到数据库中查询相应的用户对象
        //如果有条件，则 一定要创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();
        /**如何添加条件
         * select * from admin where a_name = 'admin'
         */
        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        //判断数据库中是否有该用户
        if (list.size() > 0 ){
            //因为用户名不可以重复，故按用户名查询最多只能查询到一个
            Admin admin = list.get(0);
            //进行密码比对，注意数据库中的密码是加密过的
            String mipwd = MD5Util.getMD5(pwd);
            if (mipwd.equals(admin.getaPass())){
                //如果密码相同，则登录成功，返回对应的用户的对象
                return admin;
            }
        }
        //登录失败
        return null;
    }
}
