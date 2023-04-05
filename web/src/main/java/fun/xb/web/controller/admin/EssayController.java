package fun.xb.web.controller.admin;


import fun.xb.basefunction.entity.blog_entity;
import fun.xb.basefunction.entity.type_entity;
import fun.xb.basefunction.service.markdown.re.MdMain;
import fun.xb.common.POJOUtil;
import fun.xb.common.utils.utils;
import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.SqlSession;
import fun.xb.easyorm.util.easyormPage;
import fun.xb.web.vo.EssayVO;
import fun.xb.web.vo.essay_static;
import fun.xb.web.vo.essay_vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Result upload(@RequestBody  EssayVO essay) {
        blog_entity blog = new blog_entity();
        essay.verify();
        POJOUtil.copyProperties(essay, blog,(v,o)->{
        });
        StringBuffer directory = new StringBuffer();
        String md_context = MdMain.toHtml(blog.getContext(),directory);
//        String md_context = utils.regex(blog.getContext(),directory);
        blog.setContext_html(md_context);
        blog.setDirectory(directory.toString());

        if(essay.getId()==-1){

            blog.setClick(0);
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
     * 获取所有文章类型
     * @return
     */
    @GetMapping("/type")
    public Result getType(){
        List<type_entity> list = session.select("select * from type",type_entity.class);
        return Result.sucess(list);

    }


    /**
     * 获取所有文章类型
     * @return
     */
    @PostMapping("/add_type")
    public Result addType(@RequestBody type_entity entity){



        try {
            session.insert(entity);
        }
        catch (Exception e){
            return Result.fail(e.toString());
        }


        return Result.sucess();
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
    public Result<Page> getPage(Integer size,Integer page,Long id ,String title,String blog_type_id) {
        easyormPage<blog_entity> p=new easyormPage();
        p.setPage(page);
        p.setSize(size);
        session.selectPage(String.format("select * from blog where 1=1  %s  %s order by id desc",
             id==null?"":" and id = "+id,
                blog_type_id==null?"":"and type_id = "+blog_type_id,
             title==null?"":" and title like %"+title.replaceAll(" ","")+"%"  ), blog_entity.class,p);
        Page<essay_vo> page1=new Page<>();


        POJOUtil.copyProperties(p,page1,(s,o)->{
            if(s.getList()!=null && s.getList().size()!=0){
                List<blog_entity> list = s.getList();
                List<Integer> ids = list.stream().map(blog_entity::getId).toList();

                String in_sql = "(";
                in_sql+=ids.get(0);
                for(int i =1 ;i<ids.size() ;i++){
                        in_sql+=","+ids.get(i);
                }
                in_sql+=")";

                List<type_entity> types = session.select("select * from type where id in " +in_sql,type_entity.class);

                Map<Integer,String> type_map = new HashMap<>();
                types.forEach(v->{
                    type_map.put(v.getId(),v.getType_name());
                });

                List<essay_vo> vos = new ArrayList<>();
                for(blog_entity blog : list){
                    essay_vo vo = new essay_vo();
                    vo.setId(blog.getId());
                    vo.setType(type_map.getOrDefault(blog.getType_id(),"无"));
                    vo.setTitle(blog.getTitle());
                    vo.setClick(blog.getClick());
                    vos.add(vo);
                }

                o.setList(vos);

            }

        });

        return Result.sucess(page1);
    }

    /**
     * 获取统计接口
     *
     * @return
     */
    @GetMapping("get_static")
    public Result<essay_static> get_static_info() {

       long 文章总数量 = session.selectCount("select * from blog ");
        Map 文章浏览总量_map = session.selectOne("select sum(click) c_t from blog ");
        Map 最高浏览文章_map  = session.selectOne("select title  from blog order by click desc ");
        return Result.sucess(essay_static.build().文章总数量( 文章总数量).文章浏览总量(Long.valueOf((int)文章浏览总量_map.get("c_t")))
                .最高浏览文章((String)最高浏览文章_map.get("title")));
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


