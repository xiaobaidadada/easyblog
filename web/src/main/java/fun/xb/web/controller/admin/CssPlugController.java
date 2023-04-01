package fun.xb.web.controller.admin;


import fun.xb.basefunction.constant.blog_constant;
import fun.xb.basefunction.constant.plug_constant;
import fun.xb.basefunction.entity.css_plug_entity;
import fun.xb.common.POJOUtil;
import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.SqlSession;
import fun.xb.easyorm.util.easyormPage;
import fun.xb.web.vo.plug_static;
import fun.xb.web.vo.plug_vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * css插件模块
 */
@RequestMapping(value = "/plug/css",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@RestController
public class CssPlugController {

    @Autowired
    SqlSession session;

    /**
     * 上传插件
     * @return
     */
    @PostMapping("/save")
    public Result logup(@RequestBody plug_vo vo) {
        css_plug_entity plug = new css_plug_entity();

        plug.setTime(System.currentTimeMillis());

        POJOUtil.copyProperties(vo, plug,(v, o)->{
        });
        if(vo.getId()==-1){
            vo.setId(null);
            plug.setSort(0);
            plug.setOn_off(blog_constant.plug_off);
            session.insert(plug);
            return Result.sucess(plug.getId());
        }
        else {
            if(vo.getOn_off()== blog_constant.plug_on){
                Long p1 = session.selectCount("select count(*) from css_plug where type =? and on_off = ?",vo.getType(), blog_constant.plug_on);
                if(p1>1){
                    return Result.fail("只能有一个插件被设置成开启");
                }
                else {
                    plug.setSort(1);
                }
            }

            if(session.updateById(plug)!=0){
                return Result.sucess();
            }
            else {
                return Result.fail();
            }
        }
    }



    @PostMapping("/del")
    public Result delte(Integer id) {
        css_plug_entity plug = new css_plug_entity();
        plug.setId(id);
        if(id!=null){
            if(session.deleteById(plug)!=0){
                return Result.sucess();
            }
            else {
                return Result.fail();
            }
        }
        return Result.sucess();
    }


    /**
     * 获取特定插件内容
     * @return
     */
    @GetMapping("get")
    public Result get(String id){
        List<css_plug_entity> list=session.select("select * from css_plug where id=?", css_plug_entity.class,id);
        return Result.sucess(list.get(0));
    }

    /**
     * 获取分页接口
     * @return
     */
    @GetMapping("getP")
    public Result getP(Integer size,Integer page,Long id ,String name,Integer type){
        easyormPage<css_plug_entity> p=new easyormPage();
        p.setPage(page);
        p.setSize(size);

        session.selectPage(String.format("select * from css_plug where 1=1  %s %s %s order by sort desc",
                id==null?"":"and id = "+id,
                type==null?"":"and type = "+type,
                name==null?"":" and name like %"+name.replaceAll(" ","")+"%"  ), css_plug_entity.class,p);
        Page page1=new Page<>();
        POJOUtil.copyProperties(p,page1);
        return Result.sucess(page1);
    }

    /**
     * 获取统计接口
     *
     * @return
     */
    @GetMapping("get_static")
    public Result<plug_static> get_static_info(Integer type) {

        Long total = session.selectCount("select * from css_plug where type = ?",type);

        css_plug_entity entity = session.selectOne("select * from css_plug where type = ? and on_off = ? ",css_plug_entity.class,type, plug_constant.on);

        return Result.sucess(plug_static.build().plug_total(total).using_name(entity==null?"无数据": entity.getName()));
    }

}
