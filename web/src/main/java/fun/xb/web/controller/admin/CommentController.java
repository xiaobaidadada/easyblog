package fun.xb.web.controller.admin;


import fun.xb.basefunction.entity.comment_entity;
import fun.xb.common.POJOUtil;
import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easySqlorm.service.SqlSession;
import fun.xb.easySqlorm.util.easyormPage;
import fun.xb.web.vo.CommentVO;
import fun.xb.web.vo.comment_static;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台评论系统
 */
@Controller
@ResponseBody
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    SqlSession session;
    /**
     * 获取文章相关评论 分页的形式 可以选择展示什么样的评论 type 0全部 1已经删除 2未删除
     * @param id
     * @return
     */
    @GetMapping("/getCP")
    @ResponseBody
    public Result<Page<CommentVO>> getP(Integer id, Integer status,String title,String context, int page,int size) {
        easyormPage<comment_entity> p=new easyormPage();
        p.setPage(page);
        p.setSize(size);
        session.selectPage(String.format("select * from comment where 1=1 %s %s %s order by id desc ",
                id==null?"":"and id = "+id,
                title==null?"":" and title like %"+title.replaceAll(" ","")+"%",
                context==null?"":" and context like %"+title.replaceAll(" ","")+"%"), comment_entity.class,p);
        Page page1=new Page<>();
        POJOUtil.copyProperties(p,page1);
        return Result.sucess(page1);
    }

    /**
     * 获取统计接口
     *
     * @return
     */
    @GetMapping("get_static")
    public Result<comment_static> get_static_info() {

        long total = session.selectCount("select * from comment  ");
//        long title = session.selectCount("select * from comment  ");
        String title = "haha";
        return Result.sucess(comment_static.build().title(title).total(total));
    }

    /**
     * 修改 或者 没有新增新增评论（只是后台）
     * @param commentVO
     * @return
     */
    @PostMapping("/del")
    @ResponseBody
    public Result comment(@RequestBody CommentVO commentVO) {

        comment_entity comment = new comment_entity();
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNowStr = sdf.format(d);
        comment.setTime(System.currentTimeMillis());

        POJOUtil.copyProperties(commentVO, comment,(v,o)->{
//            o.setBlog_id(v.getEssay_id());
        });
        if(commentVO.getId()==-1){
            comment.setId(null);
            session.insert(comment);
            return Result.sucess(comment.getId());
        }
        else {
            if(session.updateById(comment)!=0){
                return Result.sucess();
            }
            else {
                return Result.fail();
            }
        }
    }

    /**
     * 删除评论 强制删除，会删除与之相关的数据
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id")Integer id) {
        comment_entity comment = new comment_entity();
        comment.setId(id);
        if(id!=null){
            if(session.deleteById(comment)!=0){
                return Result.sucess();
            }
            else {
                return Result.fail();
            }
        }
        return Result.sucess();
    }
    /**
     * 获取评论的详情 判断是否要强制删除
     */
    @GetMapping("/get")
    @ResponseBody
    public Result getC(@RequestParam("id")int id) {
        List<comment_entity> list=session.select("select * from comment where id=?", comment_entity.class,id);
        return Result.sucess(list.get(0));
    }


}
