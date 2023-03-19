package fun.xb.web.controller.admin;



import fun.xb.basefunction.constant.blog_constant;
import fun.xb.basefunction.entity.js_plug_entity;
import fun.xb.common.POJOUtil;
import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.SqlSession;
import fun.xb.easyorm.util.easyormPage;
import fun.xb.web.vo.plug_vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
js插件模块
 */
@RequestMapping(value = "/plug/js",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围

@RestController
public class JsPlugController {

    @Autowired
    SqlSession session;

    /**
     * 上传插件
     * @return
     */
    @PostMapping("/save")
    public Result logup(plug_vo vo) {
        js_plug_entity plug = new js_plug_entity();
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNowStr = sdf.format(d);
        plug.setTime(System.currentTimeMillis());

        POJOUtil.copyProperties(vo, plug,(v, o)->{
            o.setId(null);
        });
        if(vo.getId()==-1){
            plug.setSort(0);
            plug.setOn_off(blog_constant.plug_off);
            session.insert(plug);
            return Result.sucess(plug.getId());
        }
        else {
            if(vo.getOn_off()== blog_constant.plug_on){
                //判断是否唯一

                Long p1 = session.selectCount("select count(*) from js_plug where type =? on_off = ?",vo.getType(), blog_constant.plug_on);
                if(p1>1){
                    return Result.fail("只能有一个插件被设置成开启");
                }

                //判断是否唯一
                else {
                    session.select("update js_plug set on_off =0 where on_off = 1 ");
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
        js_plug_entity plug = new js_plug_entity();
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
        List<js_plug_entity> list=session.select("select * from js_plug where id=?", js_plug_entity.class,id);
        return Result.sucess(list.get(0));
    }

    /**
     * 获取分页接口
     * @return
     */
    @GetMapping("getP")
    public Result getP(Integer size,Integer num){
        easyormPage<js_plug_entity> p=new easyormPage();
        p.setPage(num);
        p.setSize(size);
        session.selectPage("select * from js_plug order by sort desc", js_plug_entity.class,p);
        Page page1=new Page<>();
        POJOUtil.copyProperties(p,page1);
        return Result.sucess(page1);
    }
}
