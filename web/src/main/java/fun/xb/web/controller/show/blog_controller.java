package fun.xb.web.controller.show;

import com.alibaba.fastjson.JSONObject;
import fun.xb.basefunction.constant.blog_constant;
import fun.xb.basefunction.constant.plug_enum_type;
import fun.xb.basefunction.entity.blog_entity;
import fun.xb.basefunction.entity.css_plug_entity;
import fun.xb.basefunction.entity.js_plug_entity;
import fun.xb.easyorm.service.SqlSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

        //todo 以后换成缓存静态队列，不在这里直接更新了
        b.setClick(b.getClick()+1);
        session.updateById(b);

        List<js_plug_entity> js_list=session.select("select * from js_plug where on_off =? and type = ? ", js_plug_entity.class, blog_constant.plug_on, plug_enum_type.blog.getType());
        List<css_plug_entity> css_list=session.select("select * from css_plug where on_off=? and type = ? ", css_plug_entity.class,blog_constant.plug_on,plug_enum_type.blog.getType());

        if(js_list.size()!=0){
            js_plug_entity js = js_list.get(0);

            model.addAttribute("js",js.getContext());
        }
        if(css_list.size()!=0){
            css_plug_entity css = css_list.get(0);

            model.addAttribute("css",css.getContext());
        }



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
