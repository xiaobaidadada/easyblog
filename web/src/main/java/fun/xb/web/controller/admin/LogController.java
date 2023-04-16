package fun.xb.web.controller.admin;

import fun.xb.basefunction.constant.authority_constant;
import fun.xb.basefunction.constant.dict_type_constant;
import fun.xb.basefunction.entity.dict_entity;
import fun.xb.common.utils.string_util;
import fun.xb.common.vo.Result;
import fun.xb.easySqlorm.service.SqlSession;
import fun.xb.web.vo.AdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录权限模块，不提供注册功能；默认账号密码admin
 */
@RequestMapping(value = "/admin",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@RestController
public class LogController {

        @Autowired
        SqlSession session;

    /**
     * 登录接口
     * @param adminVO
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody  AdminVO adminVO) {

        //获取数据库内容
        String username = null;
        String password = null;
        List<dict_entity> dicts= session.select("select * from dict where type= ?",dict_entity.class, dict_type_constant.user);
        for(dict_entity entity : dicts){
            if(entity.getName().equals("username"))
                username = entity.getValue();
            if(entity.getName().equals("password"))
                password = entity.getValue();
        }

        //验证登录
        if(adminVO.getUsername().equals(username) && adminVO.getPassword().equals(password)){
            //返回Token 设置登录时间戳
            authority_constant.current_time_stamp = System.currentTimeMillis();
            String token = string_util.hash_name(username+password,"e2d2jduh2eudh822hdh2hd2");
            authority_constant.token=token;
            return Result.sucess(token);
        }

        return Result.fail();
    }

    /**
     * 修改账号密码接口
     * @param adminVO
     * @return
     */
    @PostMapping("/modify")
    public Result<String> modify(AdminVO adminVO) {
        //todo
        return Result.sucess();
    }

    /**
     *退出登录
     */
    @PostMapping("/logout")
    public Result logout(){
        authority_constant.token = "";
        return Result.sucess();
    }
}
