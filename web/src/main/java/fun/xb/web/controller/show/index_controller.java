package fun.xb.web.controller.show;

import com.alibaba.fastjson.JSONObject;
import fun.xb.basefunction.constant.blog_constant;
import fun.xb.basefunction.entity.blog_entity;
import fun.xb.basefunction.entity.css_plug_entity;
import fun.xb.basefunction.entity.js_plug_entity;
import fun.xb.basefunction.entity.type_entity;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.SqlSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;


import java.util.List;

@RestController
@RequestMapping(value = "/index")
public class index_controller {

    @Autowired
    SqlSession session;


    /**
     * 获取所有文章类型
     * @return
     */
    @PostMapping("/type")
    @ResponseBody
    public Result getType(){
        List<type_entity> list = session.select("select * from type",type_entity.class);
        return Result.sucess(list);
    }

    /**
     * 查询六个特定类型的文章
     * @param request
     * @param body
     * @return
     */
    @PostMapping("/tblog")
    @ResponseBody
    public Result getsomeblog(HttpServletRequest request, @RequestBody String body){

        JSONObject object=JSONObject.parseObject(body);
        int index= (int) object.get("essay_index"),
                type= (int) object.get("essay_type");

        List<blog_entity> list = session.select("select * from blog where type_id =? limit ?,?",blog_entity.class,type,index,6);

        return Result.sucess(list);
    }


    /**
     * 首页统计访问量
     * @param request
     * @return
     */
    @PostMapping("/indexload")
    @ResponseBody
    @Transactional//不加也可以更新
    public String indexload(HttpServletRequest request){//发送的body为空不能有, @RequestBody String body


        System.out.println("统计");
        return "ok";
    }

}
