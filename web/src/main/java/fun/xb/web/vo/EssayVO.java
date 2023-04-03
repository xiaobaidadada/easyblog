package fun.xb.web.vo;

import lombok.Data;


/**
 * 文章vo
 */
@Data
public class EssayVO {

    /**
     * 当id为-1的时候是上传
     */
    private   Integer  id;

     private String title;

     //类型的id
     private Integer type_id;

     private String context;

     public boolean verify(){
         if(this.id==null || this.title==null)return false;

         return false;
     }

}
