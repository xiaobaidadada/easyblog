package fun.xb.web.controller.interceptor;



import com.alibaba.fastjson.JSONObject;
import fun.xb.basefunction.constant.authority_constant;
import fun.xb.basefunction.constant.sys_constant;
import fun.xb.common.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;





public class authority implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨域设置
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,Token");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        String token = request.getHeader("Token");
        if(token != null && token.equals(authority_constant.token)){
            if(System.currentTimeMillis()-authority_constant.current_time_stamp < authority_constant.period_day){
                //正常访问
                return true;
            }

        }
//        System.out.println(11);

        response.getWriter().println(JSONObject.toJSONString(Result.fail(sys_constant.error_login)));
        return false;
    }



}

