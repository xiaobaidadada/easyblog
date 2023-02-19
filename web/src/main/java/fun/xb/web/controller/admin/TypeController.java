package fun.xb.web.controller.admin;

import fun.xb.basefunction.entity.type;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.Session;
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
    Session session;

    /**
     * 保存或者修改接口
     */
    @PostMapping("/save")
    public Result upload(Long id, String type_name) {//参数分别是id和类型名
        type t=new type();
        t.setId(id);
        t.setType_name(type_name);
        if(id!=-1){
            session.updateById(t);
        }
        else {
            t.setId(null);
            session.insert(t);
            System.out.println(t.getId());
        }

        return Result.sucess();
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @PostMapping("/del")
    public Result delte(Long id) {
        //todo
        return Result.sucess();
    }

    /**
     * 获取全部接口
     *
     * @return
     */
    @GetMapping("getA")
    public Result<List> getAll() {
        //todo
        return Result.sucess();
    }

    /**
     * 获取单个详情
     */
    @GetMapping("get")
    public Result get(String id) {
        //todo
        return Result.sucess();
    }
}