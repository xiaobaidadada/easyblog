package fun.xb.basefunction.service.ddns;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.xb.basefunction.constant.dict_type_constant;
import fun.xb.basefunction.entity.dict_entity;
import fun.xb.common.Requests;
import fun.xb.easySqlorm.service.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class tengxunyun implements CommandLineRunner {

    @Autowired
    SqlSession session;

    public static void main(String[] args) {
        String url = "https://dnsapi.cn/Record.List";
        String token = "409035,b633d1e55e8a309feab6ffba55d1098d";
        String type = "AAAA";
        String domain = "luoshoushou.fun";
        String sub_domain= "@";
        String body = String.format("login_token=%s&format=json&domain=%s&record_type=%s&sub_domain=%s",
                token,domain,type,sub_domain);

      Map m =   Requests.post(url,body,null);
        JSONObject jsonObject = JSONObject.parseObject((String) m.get("body"));
        JSONArray records = jsonObject.getJSONArray("records");
        JSONObject record = records.getJSONObject(0);

        String id = record.getString("id");
        String value = record.getString("value");

      System.out.println(1);
    }

    public static JSONArray getAllRecoreds(String token,String type,String domain){

        String url = "https://dnsapi.cn/Record.List";

        String body = String.format("login_token=%s&format=json&domain=%s&record_type=%s",
                token,domain,type);

        Map m =   Requests.post(url,body,null);
        JSONObject jsonObject = JSONObject.parseObject((String) m.get("body"));
        JSONArray records = jsonObject.getJSONArray("records");
//        JSONObject record = records.getJSONObject(0);
//        String id = record.getString("id");
//        String value = record.getString("value");
        return records;
    }

    /**
     * 靠这三个参数获取记录的 id
     * @param domain
     * @param sub_main
     * @param type
     * @return
     */
    static public Map getIdAndValue(String domain,String sub_main,String type,JSONArray jsonArray ){

        return null;
    }

    //根据配置保存所有的记录数据 域名和id做一一对应关系
    static Map<String,Integer> domainAndId = new HashMap<>();

    //初始化对象函数
    @Override
    public void run(String... args) throws Exception {

        //获取登录信息
        List<dict_entity> user = session.select("select * from dict where type = ?",dict_entity.class, dict_type_constant.tengxunyun_user);
        if(user != null && user.size() == 2){
            String id , token ;
            for(dict_entity dict : user){
                if(dict.getName().equals("id")) id = dict.value;
                else if (dict.getName().equals("token")) {
                    token = dict.value;
                }
            }

        }

    }

}
