package fun.xb.web.controller.admin;


import fun.xb.basefunction.constant.sys_constant;
import fun.xb.common.vo.Result;
import fun.xb.web.vo.file_vo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理，目前只做图床管理
 */
@RequestMapping(value = "/file",produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})//添加produces，根据协议缩小范围
@RestController
public class file_controller {

     String file_path = sys_constant.file_home_path;

    @PostMapping("/upload_images")
    public Result publish_images(HttpServletRequest request, @RequestParam(value = "files",required = false) MultipartFile [] files)  {


            try {
                System.out.println(files.length);
                for(MultipartFile file: files){
//                    String file_new= hash_name(file.getOriginalFilename(), "(String) request.getAttribute(\"user_id\")");//第一个参数是获取的文件类型
//                    System.out.println(images_file +file_new);
//                    System.out.println(file.getOriginalFilename());
//                    String[] file_name_list = file.getOriginalFilename().split("\\.");
                    File outfile =new File(file_path +file.getOriginalFilename());
                    file.transferTo(outfile);//写入文件
                }


            }catch ( IOException e){
                return Result.fail();
            }

            return Result.sucess();
    }




    /**
     * 下载获取单个文件，用于展示等各种功能
     * @param request
     * @param file
     * @param shangpin_id
     */
    @GetMapping("/get_image")
    public void get_file(HttpServletRequest request, @RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("shangpin_id") String shangpin_id)  {


                //todo


    }


    /**
     * 获取文件夹下的文件信息
     * 不做后端分页的了，一个文件夹的内容，让前端自己做分页展示
     * @param request
     */
    @GetMapping("get_file_info")
    public Result<List<file_vo>> get_file_info(HttpServletRequest request, @RequestParam(value = "folder_name",required = false) String folder_name)  {

//        String host = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort();
        String host = "";
        File dir = new File(file_path+(folder_name==null?"":folder_name));

        //返回展示列表
        List<file_vo> vo_list = new ArrayList<>();

        if(dir.isDirectory() || !dir.exists()){
            //是存在的目录

            //获取所有的目录
            File[] files = dir.listFiles();

            if(files!=null)
            for(File f : files){
//                System.out.println(f.getAbsolutePath());
//                System.out.println(f.getName());
                vo_list.add(file_vo.build().file_type(
                        f.isFile()?1:2
                ).name(f.getName()).url(
                        f.isFile()?host+"file_public/show?file_name="+f.getName():host+"file/get_file_info?folder_name="+f.getName()
                ));
            }

        }

          return Result.sucess(vo_list);
        }



}
