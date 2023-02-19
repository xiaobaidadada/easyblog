package fun.xb.web.controller.admin;


import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.easyorm.service.Session;
import fun.xb.web.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

        return Result.sucess();
    }

    /**
     * 修改评论
     * @param commentVO
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Result comment(CommentVO commentVO) {

        return Result.sucess();
    }

    /**
     * 删除评论 强制删除，会删除与之相关的数据
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestParam("id")int id) {

        return Result.sucess();
    }
    /**
     * 获取评论的详情 判断是否要强制删除
     */
    @GetMapping("/get")
    @ResponseBody
    public Result<CommentVO> getC(@RequestParam("id")int id) {

        return Result.sucess();
    }


}
