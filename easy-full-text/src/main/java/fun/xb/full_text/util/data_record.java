package fun.xb.full_text.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * key-text所支持的基本对象 ，只保存被分词的字段，整体可以看作提取一个 entity中 的主键，和主键字段；
 */
public class data_record{
    
    // 键，一般用于主键
    Long key_long;

    //两种主键
    String key_string;
    
    // 分词字段， 只有字符串才可以分词 ，这里只把需要分词的字段进行保存，别的字段不做分词
    List<Map<String,String>> values;

    data_record(){
        key_long = null;
        key_string = null;
        values = new ArrayList<>();
    }

    public boolean setKey(Long key){
        if(this.key_string!=null)
            return false;
        this.key_long = key;
        return true;
    }

    public boolean setKey(String key){
        if(this.key_long!=null)
            return false;
        this.key_string =key;
        return true;
    }

    public void add(String properties_name ,String data){
        Map<String,String> map = new HashMap<>();
        map.put(properties_name,data);
        values.add(map);
    }


    public <T> T getKey(int type){
        if(type==get_long_key_type)
            return (T) this.key_long;
        else if (type==get_string_key_type) {
            return (T) this.key_string;
        } else {
            return null;
        }
    }

    public static int get_long_key_type = 1;

    public static int get_string_key_type = 2;

}