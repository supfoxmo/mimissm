package com.foxmo.service;

import com.foxmo.pojo.Admin;

public interface AdminService {
    /**
     * 判断用户是否登录成功
     * @param name
     * @param pwd
     * @return
     */
    public Admin login(String name,String pwd);
}
