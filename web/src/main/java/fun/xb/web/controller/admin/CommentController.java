package fun.xb.web.controller.admin;


import fun.xb.basefunction.entity.blog;
import fun.xb.basefunction.entity.comment;
import fun.xb.common.POJOUtil;
import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.Session;
import fun.xb.easyorm.util.SelectPage;
import fun.xb.web.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 后台评论系统
 */
@Controller
@ResponseBody
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    Session session;
    /**
     * 获取文章相关评论 分页的形式 可以选择展示什么样的评论 type 0全部 1已经删除 2未删除
     * @param id
     * @return
     */
    @GetMapping("/getCP")
    @ResponseBody
    public Result<Page<CommentVO>> getP(@RequestParam("essay_id")int id, Page page, int status) {
        SelectPage<comment> p=new SelectPage();
        p.setNum(page.getNum());
        p.setSize(page.getSize());
        session.selectPage("select * from comment where seeay_id = ?", comment.class,p,id);
        Page page1=new Page<>();
        POJOUtil.copyProperties(p,page1);
        return Result.sucess(page1);
    }

    /**
     * 修改 或者 新增评论
     * @param commentVO
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Result comment(CommentVO commentVO) {

        comment comment = new comment();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        comment.setTime(dateNowStr);

        POJOUtil.copyProperties(commentVO, comment,(v,o)->{
            o.setBlog_id(v.getEssay_id());
            o.setContext(v.getComment());
            o.setId(null);
        });
        comment.setId(null);
        if(commentVO.getId()==-1){
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
        comment comment = new comment();
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
        List<comment> list=session.select("select * from comment where id=?", comment.class,id);
        return Result.sucess(list.get(0));
    }


}
