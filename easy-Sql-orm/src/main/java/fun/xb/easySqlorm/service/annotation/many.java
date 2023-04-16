package fun.xb.easySqlorm.service.annotation;


import java.lang.annotation.*;

/**
 * 一对多映射
 */
@Documented//文档处理
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})//可以注解在参数上，注解上
public @interface many {


}
