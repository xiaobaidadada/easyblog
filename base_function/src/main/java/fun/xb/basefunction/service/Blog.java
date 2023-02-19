//package fun.xb.basefunction.service;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.xb.basefunction.entity.blog;
//import com.xb.basefunction.entity.blog_type;
//import com.xb.basefunction.repository.mysqlimp.mapper.blogMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Component
//public class Blog {
//@Autowired
//    blogMapper  blog;
//
//  public   String getAllBlogTypeJson(){
//      JSONObject object=new JSONObject();
//      JSONArray array=new JSONArray();
//
//      List<blog_type> type=blog.getAllBlogType();
//      for (blog_type o : type) {
////          System.out.println(o);
//          array.add(JSONObject.toJSON(o));
//      }
//      object.put("type","blogtype");
//      object.put("blogtype",array);
//      return object.toJSONString();
//
//  }
//
//  public String getSomeBlog(int index,int type){
//      JSONObject object=new JSONObject();
//      JSONArray array=new JSONArray();
//      List<com.xb.basefunction.entity.blog> b=null;
//      if (type!=0)//如果不为类型为全部
//      b=blog.getsomeBlog(type,index);
//      else b=blog.getsomeBlog0(index);
//      for (blog o : b) {
////          System.out.println(o);
//          array.add(JSONObject.toJSON(o));
//      }
//      object.put("type","blog");
//      object.put("blog",array);
//      return object.toJSONString();
//
//  }
//
//  public String getBlogTypeName(int id){
//      JSONObject object=new JSONObject();
//      object.put("type","typename");
//      object.put("typename",blog.getAllBlogTypeName(id));
//      return object.toJSONString();
//  }
//
//
//  @Transactional
//    public blog getIdBlog(int id){
//
//        if(blog.getIdBlogClickCount(id)!=null){blog.updateIdBlogClick(id);}
//        else blog.insertNewBlogClick(id);
//
////        JSONObject object=new JSONObject();
//         blog   b=blog.getIdBlog(id);
////        object.put("blog",b);
//        return b;
//
//    }
//
//
//
//    public String queryBlog(String  input){
//        JSONObject object=new JSONObject();
//        JSONArray array=new JSONArray();
//        List<com.xb.basefunction.entity.blog> b=null;
//        b=blog.queryBlog(input);
//
//        for (blog o : b) {
////          System.out.println(o);
//            array.add(JSONObject.toJSON(o));
//        }
//        object.put("type","blog");
//        object.put("blog",array);
//        return object.toJSONString();
//
//    }
//
//}
