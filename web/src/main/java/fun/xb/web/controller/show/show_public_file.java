package fun.xb.web.controller.show;

import fun.xb.basefunction.constant.sys_constant;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * 可以展示公共权限的目录文件
 */
@RequestMapping(value = "/file_public",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@RestController
public class show_public_file {

     String file_path = sys_constant.file_home_path;

    @RequestMapping("/show")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("file_name") String fileName){
        File file = new File(file_path + fileName);
        if(!file.exists()){
            return "下载文件不存在";
        }
//        response.reset();
//        response.setContentType("application/octet-stream");
//        response.setCharacterEncoding("utf-8");
//        response.setContentLength((int) file.length());
        //不支持中文只支持ASCII
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            //输出流
            OutputStream os  = response.getOutputStream();

            //读取文件
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {

            return "下载失败";
        }
        return "下载成功";
    }
}
