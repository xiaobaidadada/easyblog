package fun.xb.web.controller.admin;


import fun.xb.basefunction.entity.blog_entity;
import fun.xb.common.POJOUtil;
import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.SqlSession;
import fun.xb.easyorm.util.SelectPage;
import fun.xb.web.vo.EssayVO;
import org.springframework.beans.factory.annotation.Autowired;
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
SqlSession session;

    /**
     * 保存或者修改接口
     *
     * @param essay
     * @return
     */
    @PostMapping("/save")
    public Result upload(EssayVO essay) {
        blog_entity blog = new blog_entity();
        essay.verify();
        POJOUtil.copyProperties(essay, blog,(v,o)->{
            o.setType_id(v.getType());
            o.setId(null);
        });
        blog.setId(null);
        if(essay.getId()==-1){
            session.insert(blog);
            return Result.sucess(blog.getId());
        }
        else {
            if(session.updateById(blog)!=0){
                return Result.sucess();
            }
            else {
                return Result.fail();
            }
        }
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @PostMapping("/del")
    public Result delte(Integer id) {
        blog_entity blog = new blog_entity();
        blog.setId(id);
        if(id!=null){
            if(session.deleteById(blog)!=0){
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
    public Result<Page> getPage(Integer size,Integer num) {
        SelectPage<blog_entity> p=new SelectPage();
        p.setNum(num);
        p.setSize(size);
        session.selectPage("select * from blog", blog_entity.class,p);
        Page page1=new Page<>();
        POJOUtil.copyProperties(p,page1);
        return Result.sucess(page1);
    }

    /**
     * 获取单个详情
     */
    @GetMapping("get")
    public Result get(Integer id) {
        List<blog_entity> list=session.select("select * from blog where id=?", blog_entity.class,id);
        return Result.sucess(list.get(0));
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


