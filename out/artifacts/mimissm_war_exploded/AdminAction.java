package com.foxmo.controller;

import com.foxmo.pojo.Admin;
import com.foxmo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name,String pwd,HttpServletRequest request){
        Admin admin = adminService.login(name, pwd);
        if (admin != null){
            //登录成功,进入主页
            request.setAttribute("admin",admin);
            return "admin/main.jsp";
        }else{
            //登录失败，返回登录页面
            request.setAttribute("errmsg","用户名或密码不正确");
            return "admin/login.jsp";
        }
    }
}
