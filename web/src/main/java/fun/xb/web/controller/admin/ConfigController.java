package fun.xb.web.controller.admin;

import fun.xb.common.vo.Result;
import fun.xb.web.vo.ConfigVO;
import org.springframework.web.bind.annotation.*;

/**
 * 配置项目的一些设置，采用默认的sqlte数据库
 */

@RestController
@RequestMapping(value = "/config",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
public class ConfigController {


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
     * 设置具体的位置数据
     */
    @PostMapping("/setting")
    public Result setting(ConfigVO configVO){
        //todo
        return Result.sucess();
    }
}
