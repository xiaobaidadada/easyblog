package fun.xb.web.controller.admin;

import fun.xb.basefunction.entity.dict_entity;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.SqlSession;
import fun.xb.web.vo.ConfigVO;
import fun.xb.web.vo.web_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置项目的一些设置，采用默认的sqlte数据库
 */

@RestController
@RequestMapping(value = "/config",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
public class ConfigController {


    @Autowired
    SqlSession session;

    /**
     * 获取当前当前数据保存方式
     * @return
     */

    @GetMapping("/getType")
    public Result getType(){

        //todo
        return Result.sucess();
    }

    /**
     * 获取当前数据保存的具体位置，不同的数据保存方式格式不同，目前是磁盘和mysql
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo(){
        //todo
        return Result.sucess();
    }

    /**
     * 设置用户信息
     */
    @PostMapping("/set_user")
    public Result setting(@RequestBody ConfigVO configVO){

        dict_entity entity = new dict_entity();
        entity.setValue(configVO.getUsername());
        session.updateByWhereSql(entity," name = 'username' ");


        entity.setValue(configVO.getPassword());
        session.updateByWhereSql(entity," name = 'password' ");

        return Result.sucess();
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
     * 设置网站信息
     */
    @PostMapping("/set_web")
    public Result set_web(@RequestBody web_info web_info){

        dict_entity entity = new dict_entity();
        entity.setValue(web_info.getWebsite_name());
        session.updateByWhereSql(entity," name = 'website_name' ");


        entity.setValue(web_info.getWebsite_about_added());
        session.updateByWhereSql(entity," name = 'website_about_added' ");

        entity.setValue(web_info.getWebsite_about());
        session.updateByWhereSql(entity," name = 'website_about' ");

        return Result.sucess();
    }

}
