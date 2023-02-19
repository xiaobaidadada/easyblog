//package fun.xb.web.controller.show;
//
//import com.alibaba.fastjson.JSONObject;
//import com.xb.basefunction.service.Blog;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@RequestMapping(value = "/blog")
//public class html {
//    @Autowired
//    Blog blog;
//    @Autowired
//    com.xb.basefunction.repository.mysqlimp.mapper.blogMapper blogMapper;
//
//    /**
//     * 获取文章详情
//     * @param request
//     * @param id
//     * @param model
//     * @return
//     */
//    @GetMapping("/essay/{id}")
//    public String get(HttpServletRequest request, @PathVariable("id")int id, Model model){
////        System.out.println("kk");
//           model.addAttribute("title","欧克");
////        System.out.println(request.getContextPath());
//        com.xb.basefunction.entity.blog b=blog.getIdBlog(id);
//        model.addAttribute("blog",b);
////        System.out.println(b.toJSONString());
//        return "blog";
//    }
//
//    /**
//     * 获取所有文章类型
//     * @return
//     */
//    @PostMapping("/type")
//    @ResponseBody
//    public String getType(){
//        return blog.getAllBlogTypeJson();
//    }
//
//    /**
//     * 查询六个特定类型的文章
//     * @param request
//     * @param body
//     * @return
//     */
//    @PostMapping("/tblog")
//    @ResponseBody
//    public String getsomeblog(HttpServletRequest request, @RequestBody String body){
////        System.out.println(body);
//        JSONObject object=JSONObject.parseObject(body);
//        int index= (int) object.get("essay_index"),
//                type= (int) object.get("essay_type");
//
//        return blog.getSomeBlog(index,type);
//    }
//
//    /**
//     * 获取一种文章类型的名字
//     * @param request
//     * @param body
//     * @return
//     */
//    @PostMapping("/typename")
//    @ResponseBody
//    public String gettypename(HttpServletRequest request, @RequestBody String body){
//        JSONObject object=JSONObject.parseObject(body);
//        int id= Integer.parseInt((String) object.get("id"));
//
//        return blog.getBlogTypeName(id);
//    }
//
//    /**
//     * 首页统计访问量
//     * @param request
//     * @return
//     */
//    @PostMapping("/indexload")
//    @ResponseBody
//    @Transactional//不加也可以更新
//    public String indexload(HttpServletRequest request){//发送的body为空不能有, @RequestBody String body
//
//        blogMapper.updateIndexClick();
//
//        return "ok";
//    }
//
//    /**
//     * 搜索博客
//     * @param request
//     * @param body
//     * @return
//     */
//    @PostMapping("/queryblog")
//    @ResponseBody
//    public String queryblog(HttpServletRequest request, @RequestBody String body){
//
//        JSONObject object=JSONObject.parseObject(body);
//        String  input= (String) object.get("input");
//
//        return blog.queryBlog(input);
//    }
//
//
//}
