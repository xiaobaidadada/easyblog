package fun.xb.web.controller.show;

import com.alibaba.fastjson.JSONObject;
import fun.xb.basefunction.entity.blog_entity;
import fun.xb.easyorm.service.SqlSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping(value = "/blog")
public class blog_controller {
    @Autowired
    SqlSession session;

    /**
     * 获取文章详情
     * @param request
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/essay/{id}")
    public String get(HttpServletRequest request, @PathVariable("id")int id, Model model){
        model.addAttribute("title","欧克");
        blog_entity b=session.selectOne("select * from blog where id = ?",blog_entity.class,id);
        //临时处理
        b.setContext(b.getContext_html());
        model.addAttribute("blog",b);
        return "blog";
    }






//
//
//
//    /**
//     * 搜索博客
//     * @param request
//     * @param body
//     * @return
//     */
//    @PostMapping("/queryblog")
//    @ResponseBody
//    public String queryblog(HttpServletRequest request, @RequestBody String body){
//
//        JSONObject object=JSONObject.parseObject(body);
//        String  input= (String) object.get("input");
//
//        return blog.queryBlog(input);
//    }


}
