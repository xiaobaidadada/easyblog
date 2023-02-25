package fun.xb.web.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文件管理，目前只做图床管理
 */
@RequestMapping(value = "/file",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@Controller
public class file_controller {


}
