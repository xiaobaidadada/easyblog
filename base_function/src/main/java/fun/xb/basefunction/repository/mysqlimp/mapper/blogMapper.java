//package fun.xb.basefunction.repository.mysqlimp.mapper;
//
//
//import com.xb.basefunction.entity.blog;
//import com.xb.basefunction.entity.blog_type;
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface blogMapper {
//    @Insert("insert into blog (id ,title ,context,time,about,directory,type) values(#{id} ,#{title},#{context} ,#{time},#{about},#{directory},#{type}) ")
//    public int insertBlog(blog blog);
//
//    @Select("select * from blog_type")
//    public List<blog_type> getAllBlogType();//设置成arrlist是不行的
//
//    @Select("select type_name from blog_type where id=#{id}")
//    public String getAllBlogTypeName(@Param("id") int id);//
//
//    //都是查询博客的
//    @Select("select * from blog  where title like CONCAT('%',#{input},'%') or context like CONCAT('%',#{input},'%')  order by time desc ")
//    public List<blog> queryBlog(@Param("input") String input);
//
//    @Select("select * from blog where type=#{type} order by time desc limit #{index},6 ")
//    public List<blog> getsomeBlog(@Param("type") int type,@Param("index") int index);
//
//    @Select("select * from blog order by time desc limit #{index},6 ")
//    public List<blog> getsomeBlog0(@Param("index") int index);
//
//    @Select("select * from blog where id=#{id} ")
//    public blog getIdBlog(@Param("id") int id);
//
//    //修改博客
//    @Delete("DELETE FROM blog where id =#{id}  ")
//    public int deleteblog(@Param("id")int id);
//
//    //博客查询次数
//    @Select("select count from blog_click where id=#{id} ")
//    public Integer getIdBlogClickCount(@Param("id") int id);
//
//    @Insert("insert into blog_click (id,count) values(#{id} , 1)")
//    public int insertNewBlogClick(@Param("id") int id);
//
//    @Update("UPDATE blog_click set count=count+1 where id=#{id}")
//    public int updateIdBlogClick(@Param("id") int id) ;
//
//    //整个网站打开
//    @Update("UPDATE count set index_click=index_click+1")
//    public int updateIndexClick();
//
//}
