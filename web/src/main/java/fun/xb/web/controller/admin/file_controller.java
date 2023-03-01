package fun.xb.web.controller.admin;


import fun.xb.common.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件管理，目前只做图床管理
 */
@RequestMapping(value = "/file",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@RestController
public class file_controller {

    static String shangpin_path = "path";

    @PostMapping("upload_images")
    public void publish_images(HttpServletRequest request, @RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("shangpin_id") String shangpin_id)  {

        if (request.getAttribute("user_openid")!=null){
            try {
                String file_new=hash_file_name(file.getOriginalFilename(), (String) request.getAttribute("user_id"));//第一个参数是获取的文件类型
                System.out.println(shangpin_path+file_new);
                File outfile =new File(shangpin_path+file_new);
                file.transferTo(outfile);//写入文件


                //todo
            }catch ( IOException e){

            }

        }

    }

    public String hash_file_name(String file_name, String orstr) {
        String ymd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return DigestUtils.sha1Hex(file_name + orstr + ymd);
    }


    /**
     * 下载获取单个文件，用于展示等各种功能
     * @param request
     * @param file
     * @param shangpin_id
     */
    @GetMapping("get_image")
    public void get_file(HttpServletRequest request, @RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("shangpin_id") String shangpin_id)  {


                //todo


    }


    /**
     * 获取文件夹下的文件信息
     * @param request
     * @param file
     * @param shangpin_id
     */
    @GetMapping("get_file_info")
    public Result get_file_info(HttpServletRequest request, @RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("shangpin_id") String shangpin_id)  {

            //todo
          return Result.sucess();
        }



}
