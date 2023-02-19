//package fun.xb.basefunction.service;
//
//import com.xb.basefunction.entity.blog;
//import com.xb.basefunction.repository.mysqlimp.mapper.blogMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Isolation;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//public class MdModel {
//        @Autowired
//    blogMapper blogMapper;
//
//        //功能也可以提供默认加1
//        @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
//        public String insertBlog(blog blog){//
//            // 这个地方是查询操作 在并发的请款下
//            if (blogMapper.insertBlog(blog)!=0)
//            return "success";
//            else return "faile";
//        }
//
//    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
//    public String deleteblog(int  id){//
//        // 这个地方是查询操作 在并发的请款下
//        if (blogMapper.deleteblog(id)!=0)
//            return "success";
//        else return "faile";
//    }
//
//}
