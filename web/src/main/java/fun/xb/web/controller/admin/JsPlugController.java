package fun.xb.web.controller.admin;



import fun.xb.common.vo.Result;
import fun.xb.web.vo.AdminVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
js插件模块
 */
@RequestMapping(value = "/plug/js",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@Controller
public class JsPlugController {

    /**
     * 上传插件
     * @param adminVO
     * @return
     */
    @PostMapping("/upload")
    public Result logup(AdminVO adminVO) {
        //todo
        return Result.sucess();
    }

    /**
     * 设置起作用得插件
     * @param id
     * @return
     */
    @PostMapping("/optPlug")
    public Result optPLug(String id) {
        //todo
        return Result.sucess();
    }

    /**
     * 获取当前所有插件
     * @return
     */
    @GetMapping("getA")
    public Result getAll(){
        //todo
        return Result.sucess();
    }
    /**
     * 获取特定插件内容
     * @return
     */
    @GetMapping("get")
    public Result get(String id){
        //todo
        return Result.sucess();
    }
    /**
     * 获取当前起作用得插件
     * @return
     */
    @GetMapping("getP")
    public Result getP(String id){
        //todo
        return Result.sucess();
    }
}
