package fun.xb.web.controller.show;

import fun.xb.basefunction.constant.blog_constant;
import fun.xb.basefunction.entity.css_plug_entity;
import fun.xb.basefunction.entity.js_plug_entity;
import fun.xb.easyorm.service.SqlSession;
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

        List<js_plug_entity> js_list=session.select("select * from js_plug where on_off =?", js_plug_entity.class, blog_constant.plug_on);
        List<css_plug_entity> css_list=session.select("select * from js_plug where on_off=?", css_plug_entity.class,blog_constant.plug_on);

        if(js_list.size()!=0){
            js_plug_entity js = js_list.get(0);

            model.addAttribute("js",js.getContext());
        }
        if(css_list.size()!=0){
            css_plug_entity css = css_list.get(0);

            model.addAttribute("css",css.getContext());
        }



        return "index";
    }

}
