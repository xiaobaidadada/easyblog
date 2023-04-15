package fun.xb.full_text.util;

import fun.xb.full_text.service.annotation.text_column;
import fun.xb.full_text.service.annotation.text_id;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DtoUtil {

    /**
     * 获取Id值
     * @param dto
     * @return
     * @throws IllegalAccessException
     */
    public static Object getId(Object dto)  {
        Object id=null;

        List<Field> list = Arrays.asList(FieldUtils.getAllFields(dto.getClass()));
        for (Field f:list) {
            boolean br=false;


                if (f.isAnnotationPresent(text_id.class) ){//插入跳过id
                    f.setAccessible(true);
                    try {
                        id=f.get(dto);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    br=true;
                    break;
                }

            //是不是已经获取了，不再判断名字是不是等于id
            if(br){
                break;
            }else {
                f.setAccessible(true);
                if(f.getName().equals("id")){

                    try {
                        id=f.get(dto);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        }
        return id;
    }

    /**
     * 获取对象的被注解的全部属性 map 包含id
     * @param
     * @return
     */
    public static  data_record_map getProperties(Object dto){
        Field[]  files = FieldUtils.getAllFields(dto.getClass());
        data_record_map all_annotaed_fields = new data_record_map();
        for(Field field : files){
            //访问私有
            field.setAccessible(true);
            if(field.isAnnotationPresent(text_column.class)){
                try {
                    //不能转化会报错
                    all_annotaed_fields.put(field.getName(), (String) field.get(dto));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else if (field.isAnnotationPresent(text_id.class)) {
                try {
                    all_annotaed_fields.setKey(field.get(dto));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return all_annotaed_fields;
    }

    /**
     * 给对象oo 特定的属性设置特定的值value
     */
    public static  void setPartOfProperty(Object oo,String name,Object value){


        Class target=oo.getClass();
        Field[] targetFields = target.getDeclaredFields();//目标属性集合
        for (Field fieldTarget : targetFields) {

            if(fieldTarget.getName().equals(name)){
                fieldTarget.setAccessible(true);
                try {
                    if(value!=null)
                        fieldTarget.set(oo,TypeConverter.selfType_to_otherType(fieldTarget.getType().getTypeName(),value));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }


    /**
     * 为对象的id字段进行赋值
     * @param dto
     * @return
     * @throws IllegalAccessException
     */
    public static void setId(Object dto, IndexableField value) throws IllegalAccessException {
        Object id=null;
        List<Field> list = Arrays.asList(FieldUtils.getAllFields(dto.getClass()));

        for (Field f:list) {

            if (f.isAnnotationPresent(text_id.class)) {
                    f.setAccessible(true);
                    if( f.getType().getTypeName().equals("java.lang.String"))
                    f.set(dto,value.stringValue());
                    else f.set(dto,value.numericValue());
                    break;
            }

        }

    }



}
