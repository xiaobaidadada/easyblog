package fun.xb.web.controller.show;

import fun.xb.basefunction.constant.blog_constant;
import fun.xb.basefunction.constant.plug_enum_type;
import fun.xb.basefunction.entity.css_plug_entity;
import fun.xb.basefunction.entity.dict_entity;
import fun.xb.basefunction.entity.js_plug_entity;
import fun.xb.easySqlorm.service.SqlSession;
import fun.xb.web.vo.web_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class index {

    @Autowired
    SqlSession session;

    @RequestMapping("/")
    public String getString(Model  model){

        //加载插件
        List<js_plug_entity> js_list=session.select("select * from js_plug where on_off =? and type = ? ", js_plug_entity.class, blog_constant.plug_on, plug_enum_type.index.getType());
        List<css_plug_entity> css_list=session.select("select * from css_plug where on_off=? and type = ? ", css_plug_entity.class,blog_constant.plug_on,plug_enum_type.index.getType());

        if(js_list.size()!=0){
            js_plug_entity js = js_list.get(0);

            model.addAttribute("js",js.getContext());
        }
        if(css_list.size()!=0){
            css_plug_entity css = css_list.get(0);

            model.addAttribute("css",css.getContext());
        }

    //加载网站信息
        web_info web_info = new web_info();
        List<dict_entity> dict_list = session.select("select * from dict where type = 2",dict_entity.class);
        dict_list.forEach(v->{
            if(v.getName().equals("website_name")){
                web_info.setWebsite_name(v.getValue());
            } else if(v.getName().equals("website_about_added")){
                web_info.setWebsite_about_added(v.getValue());
            } else if(v.getName().equals("website_about")){
                web_info.setWebsite_about(v.getValue());
            }
        });
        model.addAttribute("info",web_info);


        return "index";
    }

}
