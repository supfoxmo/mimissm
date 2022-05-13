package com.foxmo.controller;

import com.foxmo.pojo.Admin;
import com.foxmo.pojo.ProductInfo;
import com.foxmo.pojo.vo.ProductInfoVo;
import com.foxmo.service.ProductInfoService;
import com.foxmo.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    //每一页显示纪录数
    public static final int PAGE_SIZE = 5;
    //图片存储文件名
    private String saveFileName = "";

    @Autowired
    ProductInfoService productInfoService;

    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    /**
     * 显示第一页的5条纪录
     * @param request
     * @return
     */
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info = null;
        Object prodVo = request.getSession().getAttribute("prodVo");
        if (prodVo != null){
            info = productInfoService.splitPageVo((ProductInfoVo) prodVo,PAGE_SIZE);
            //清除存储在Session中的prodVo
            request.getSession().removeAttribute("prodVo");
        }else{
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.setAttribute("info",info);

        return "product";
    }

    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //提取生成图片存储文件名
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //获取项目中图片存的真实路径
        String path = request.getServletContext().getRealPath("/image_big");
        //转存：
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //图片回显
        JSONObject json = new JSONObject();
        json.put("imgurl",saveFileName);
        return json.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpDate(new Date());
        info.setpImage(saveFileName);

        int num = -1;
        try{
            num = productInfoService.save(info);
        }catch(Exception e ){
            e.printStackTrace();
        }
        if (num > 0){
            request.setAttribute("msg","新增成功");
        }else{
            request.setAttribute("msg","新增失败");
        }
        //清空saveFileName
        saveFileName = "";

        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid,ProductInfoVo vo, Model model,HttpSession session){
        ProductInfo productInfo = productInfoService.getById(pid);
        model.addAttribute("prod",productInfo);
        session.setAttribute("prodVo",vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //因为ajax的异步图片上传，如果上传过，
        //则saveFileName里有上传的图片的名称
        //如果没有使用异步ajax上传过图片，则saveFileName="",
        //实体类info使用隐藏表单域提供上来的pImage原始图片的名称
        if(!saveFileName.equals("")){
            //过ProductInfo对象的pImage属性赋值
            info.setpImage(saveFileName);
        }
        //更新处理
        int num = -1;
        try{
            num = productInfoService.update(info);
        }catch(Exception e ){
            e.printStackTrace();
        }
        if (num > 0){
            //更新成功
            request.setAttribute("msg","更新成功");
        }else{
            //更新失败
            request.setAttribute("msg","更新失败");
        }
        //清空saveFileName，以便下次使用
        saveFileName = "";
        //跳转到重新分页的展示，重新读取数据库中的数据
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        int num = -1;
        try{
            num = productInfoService.delete(pid);
        }catch(Exception e){
            e.printStackTrace();
        }
        if (num > 0){
            //删除成功
            request.setAttribute("msg","删除成功");
            //存放分页状态
            request.getSession().setAttribute("prodVo",vo);
        }else{
            //删除失败
            request.setAttribute("msg","删除失败");
        }
        //删除结束后转发到分页显示
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "test/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo pageInfo = null;
        Object prodVo = request.getSession().getAttribute("prodVo");
        if (prodVo != null){
            pageInfo = productInfoService.splitPageVo((ProductInfoVo) prodVo,PAGE_SIZE);
            //清除session域中的proVo数据，防止影响后续操作
            request.getSession().removeAttribute("proVo");
        }else{
            //获取第一页的数据
            pageInfo = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("info",pageInfo);
        return request.getAttribute("msg");
    }

    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request){
        //将pids字符串分割成商品id的字符数组
        String[] ps = pids.split(",");
        int num = -1;

        try{
            num = productInfoService.deleteBatch(ps);
            if (num > 0){
                request.setAttribute("msg","批量删除成功");
            }else{
                request.setAttribute("msg","批量删除失败");
            }
        }catch(Exception e){
            request.setAttribute("msg","商品不可删除");
        }
        //删除结束后转发到分页显示
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping("/selectCondition")
    public void selectCondtion(ProductInfoVo vo, HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }

}
