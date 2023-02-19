package fun.xb.web.controller.admin;

import fun.xb.common.vo.Result;
import fun.xb.web.vo.AdminVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录权限模块，不提供注册功能；默认账号密码admin
 */
@RequestMapping(value = "/admin",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@Controller
public class LogController {



    /**
     * 登录接口
     * @param adminVO
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(AdminVO adminVO) {
        //todo
        return Result.sucess();
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
     *登录功能
     */
    @PostMapping("/logout")
    public Result logout(){
        // todo
        return Result.fail();
    }
}
