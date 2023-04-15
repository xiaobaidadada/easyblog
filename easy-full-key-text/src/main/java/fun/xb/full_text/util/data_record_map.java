package fun.xb.full_text.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * key-text所支持的基本对象 ，只保存被分词的字段，整体可以看作提取一个 entity中 的主键，和主键字段；
 * 只允许Long 和 string 其他的能转化就转化，不能转化就失败
 */
public class data_record_map implements Map<String,String>{
    
    // 键，一般用于主键
    Long key_long;

    //两种主键
    String key_string;
    
    // 分词字段， 只有字符串才可以分词 ，这里只把需要分词的字段进行保存，别的字段不做分词
    Map<String,String> values;

    public data_record_map(){
        key_long = null;
        key_string = null;
        values = new HashMap<>();
    }

    //两种主键
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

    public boolean setKey(Object key){
        if(key instanceof String){
            this.key_string = (String) key;
        }
        if(key instanceof Long){
            this.key_long = (Long) key;
        }
        return true;
    }

    public <T> T getKey(){
        if(this.key_long!=null){
            return (T) this.key_long;
        }
        if ((this.key_string!=null)){
            return (T) this.key_string;
        }

        return null;
    }

    public <T> T getKeyByType(int type){
        if(type==get_long_key_type)
            return (T) this.key_long;
        else if (type==get_string_key_type) {
            return (T) this.key_string;
        } else {
            return null;
        }
    }

    public int getkeyType(){
        if(this.key_long!=null){
            return this.get_long_key_type;
        }
        if ((this.key_string!=null)){
            return  this.get_string_key_type;
        }
        return 0;
    }


    public static int get_long_key_type = 1;

    public static int get_string_key_type = 2;

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return values.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.containsKey(value);
    }

    @Override
    public String get(Object key) {
        return values.get(key);
    }

    @Override
    public String put(String key, String value) {
        return values.put(key,value);
    }

    @Override
    public String remove(Object key) {
        return values.remove(key);
    }

    @Override
    public void putAll(Map m) {
            values.putAll(m);
    }

    @Override
    public void clear() {
            values.clear();
    }

    @Override
    public Set keySet() {
        return values.keySet();
    }

    @Override
    public Collection values() {
        return values.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return values.entrySet();
    }
}