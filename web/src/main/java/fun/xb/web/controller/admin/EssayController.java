package fun.xb.web.controller.admin;


import fun.xb.basefunction.entity.blog;
import fun.xb.basefunction.entity.type;
import fun.xb.basefunction.service.essay.easy_service;
import fun.xb.common.POJOUtil;
import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.Session;
import fun.xb.web.vo.EssayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 管理文章的控制器
 */
@RequestMapping(value = "/essay", produces = {"text/html;charset=UTF-8;", "application/json;charset=UTF-8;"})
//添加produces，根据协议缩小范围
//@Controller
//@ResponseBody
@RestController//前面俩的替代
public class EssayController {
//    @Autowired
//    easy_service service;
    @Autowired
    Session session;

    /**
     * 保存或者修改接口
     *
     * @param essay
     * @return
     */
    @PostMapping("/save")
    public Result upload(EssayVO essay) {
        blog blog = new blog();
        essay.verify();
        POJOUtil.copyProperties(essay, blog);
        blog.setId(null);
        if(essay.getId()==-1){
            if(session.insert(blog).getId()!=-1){
                Result.sucess();
            }
            else {
                return Result.fail();
            }
        }
        else {
            if(session.updateById(blog)!=0){
                return Result.sucess();
            }
            else {
                return Result.fail();
            }
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
    public Result delte(Long id) {
        blog blog = new blog();
        blog.setId(id);
        if(id!=null){
            if(session.deleteById(blog)!=1){
                return Result.sucess();
            }
            else {
                return Result.fail();
            }
        }
        return Result.sucess();
    }

    /**
     * 获取分页接口
     *
     * @return
     */
    @GetMapping("getP")
    public Result<Page> getPage() {
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

    /**
     * 通过文件的方式上传数据
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping(value = "upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @Transactional
    public Result<Void> uploadEssay(@RequestPart("file") MultipartFile multipartFile, @RequestParam("type") int type, @RequestParam("title") String title) {
        System.out.println(title);
        //todo
        return null;
    }

}


