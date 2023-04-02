package fun.xb.web.vo;

import lombok.Data;

/**
 * 登录注册
 */
@Data
public class AdminVO {

     String username;

     String oldUsername;

     String password;

     String oldPassword;

     String code;

    /*
    用于修改账号密码等判断
     */
     int type;


}
