package fun.xb.easySqlorm.service.annotation;

import java.lang.annotation.*;

/**
 * 该注解在dto类上，标识该dto的表名
 */
@Documented//文档处理
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})//可以注解在注解和接口、类上
public @interface table {

    public String value() ;

}
