package fun.xb.easyFullTextorm.service.annotation;


import java.lang.annotation.*;

/**
 * 不同于orm 这些必须被设置
 */
@Documented//文档处理
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})//可以注解在参数上，注解上
public @interface text_id {

//    public String value() default "id";


}
