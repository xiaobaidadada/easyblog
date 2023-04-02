package fun.xb.web.controller.show;


import fun.xb.common.vo.Page;
import fun.xb.common.vo.Result;
import fun.xb.web.vo.CommentVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 前端评论系统
 */
@Controller
@ResponseBody
@RequestMapping("/comment_for")
public class blog_comment_controller {

    /**
     * 获取文章相关评论 分页的形式
     * @param id
     * @return
     */
    @GetMapping("/getC")
    @ResponseBody
    public Result<Page<CommentVO>> getP(@RequestParam("essay_id")int id, Page page) {

        return null;
    }

    /**
     * 评论别人
     * @param commentVO
     * @return
     */
    @PostMapping("/comment")
    @ResponseBody
    public Result comment(CommentVO commentVO) {

        return null;
    }


}
