//package fun.xb.web.controller.admin;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.xb.basefunction.entity.blog;
//import com.xb.basefunction.service.Blog;
//import com.xb.basefunction.service.MdModel;
//import com.xb.basefunction.service.markdown.re.MdMain;
//import com.xb.common.utils.utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Controller
//@CrossOrigin
//@RequestMapping(value = "/md",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
//@ResponseBody
//public class md_admin {
//    @Autowired
//    MdModel md;
//    @Autowired
//    Blog blog;
//
//    @PostMapping("/upload")//登陆函数
//    public String upload(HttpServletRequest request, @RequestBody String body) throws ServletException, IOException {
//
//        JSONObject object=JSONObject.parseObject(body);
//        com.xb.basefunction.entity.blog blog=new blog();
//        blog.setType(String.valueOf(object.get("type")) );
//        StringBuffer Directory = new StringBuffer();
//        blog.setContext(MdMain.toHtml((String) object.get("context"),Directory));
//        blog.setDirectory(Directory.toString());
//        blog.setTitle((String) object.get("title"));
//        blog.setTime(utils.gettime());
//
//        if (md.insertBlog(blog).equals("success"))
//        return "ok";
//        else return "no";
//    }
//
//    @PostMapping("/type")
//    @ResponseBody
//    public String getType(){
//        return blog.getAllBlogTypeJson();
//    }
//
//    @PostMapping("/tblog")
//    @ResponseBody
//    public String getsomeblog(HttpServletRequest request, @RequestBody String body){
//        System.out.println(body);
//        JSONObject object=JSONObject.parseObject(body);
//        int index= (int) object.get("essay_index"),type= (int) object.get("essay_type");
//
//        return blog.getSomeBlog(index,type);
//    }
//
//    @PostMapping("/deleteblog")
//    @ResponseBody
//    public String deleteblog(HttpServletRequest request, @RequestBody String body){
//        System.out.println(body);
//        JSONObject object=JSONObject.parseObject(body);
//        int id= (int) object.get("id");
//
//        return md.deleteblog(id);
//    }
//
//}
