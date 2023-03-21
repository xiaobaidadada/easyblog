package fun.xb.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 评论 不能在一个评论内循环，一个评论要么是评论别人的，要么是评论一个人的
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//属性为null的不返回，也可以注释到属性上
public class CommentVO
{
    /*
    文章id
     */
    private Integer essay_id;
    /**
     * 评论内容
     */
    private String context;
    /**
     * 父评论 如果有
     */
    private Integer father_id;
    /**
     * 评论类型， 1是评论博客  2是评论别人评论
     */
    private Integer type;
    /**
     * 评论的Id
     */
    private Integer id;
    /**
     * 评论删除状态
     */
    private Integer status;

}
