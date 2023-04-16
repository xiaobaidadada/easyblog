package fun.xb.web.controller.admin;

import fun.xb.basefunction.entity.type_entity;
import fun.xb.common.vo.Result;
import fun.xb.easySqlorm.service.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 文章类型
 */
@RequestMapping(value = "/type/essay", produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})
//添加produces，根据协议缩小范围
@Controller
@ResponseBody//这个 注解才会被swgger识别
public class TypeController {

    @Autowired
    SqlSession session;

    /**
     * 保存或者修改接口
     */
    @PostMapping("/save")
    public Result upload(Integer id, String type_name) {//参数分别是id和类型名
        type_entity t=new type_entity();
        t.setId(id);
        t.setType_name(type_name);
        if(id!=-1){
           int i= session.updateById(t);
           if(i!=0){
               return Result.sucess();
           }
        }
        else {

           return Result.sucess(session.insert(t).getId()) ;

        }
        return Result.fail();
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @PostMapping("/del")
    public Result delte(Integer id) {
        type_entity t=new type_entity();
        t.setId(id);
        if(session.deleteById(t)!=0){
            return Result.sucess();
        }
        return Result.fail();

    }

    /**
     * 获取全部接口
     *
     * @return
     */
    @GetMapping("getA")
    public Result<List> getAll() {
        List<type_entity> list=session.select("select * from type ", type_entity.class);

        return Result.sucess(list);
    }

    /**
     * 获取单个详情
     */
    @GetMapping("get")
    public Result get(Integer id) {
        List<type_entity> list=session.select("select * from type where id=?", type_entity.class,id);
        return Result.sucess(list.get(0));
    }
}