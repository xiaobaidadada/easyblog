package fun.xb.web.controller.show;

import com.alibaba.fastjson.JSONObject;
import fun.xb.basefunction.constant.cache;
import fun.xb.basefunction.entity.*;
import fun.xb.common.vo.Result;
import fun.xb.easySqlorm.service.SqlSession;
import fun.xb.web.vo.web_info;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int index= (int) object.get("essay_index")-1,
                type= (int) object.get("essay_type");

        List<blog_entity> list = null;
        if(type == 0){
            //查全部
          list =  session.select("select * from blog order by id desc limit ?,?",blog_entity.class,index,6);
        }
        else{
            list =  session.select("select * from blog where type_id =? order by id desc limit ?,?",blog_entity.class,type,index,6);
        }


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

        cache.web_index_click_num.addAndGet(1);

        return "ok";
    }

        /**
     * 获取一种文章类型的名字
     * @param request
     * @param body
     * @return
     */
    @PostMapping("/typename")
    @ResponseBody
    public Result gettypename(HttpServletRequest request, @RequestBody String body){
        JSONObject object=JSONObject.parseObject(body);
        int id= Integer.parseInt((String) object.get("id"));
        // 暂时不需要从这里加载列表数据了
//        List<blog_entity> list = session.select("select * from blog where type_id =? limit 0,6",blog_entity.class,id);
        type_entity type = session.selectOne("select * from type where id = ?",type_entity.class,id);
        Result result = Result.sucess();
        Map map = new HashMap();
        map.put("type",type.getType_name());
        result.setMap(map);
        return result;
    }


    /**
     * 获取网站信息
     */
    @GetMapping("/get_web")
    public Result<web_info> getWebInfo(){

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
        return Result.sucess(web_info);
    }


    /**
     * 查询博客
     */
    @GetMapping("/queryblog")
    public Result<List<blog_entity>> queryblog(@RequestParam("q") String query){

        query = "%"+query+"%";

        List<blog_entity> list =  session.select("select * from blog  where title like ? or context like ? ",blog_entity.class,query,query);
        list.forEach(v->{
            v.setContext(null);
            v.setContext_html(null);
            v.setDirectory(null);
        });


        return Result.sucess(list);
    }


}
