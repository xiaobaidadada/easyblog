package fun.xb.basefunction.constant;

/**
 * 登录权限的模块，仅用于单设备，单人，单次 登录
 */
public class authority_constant {

    //token用来验证身份
    public static String token = "";

    //上次登录时间 毫秒级
    public static long current_time_stamp = System.currentTimeMillis();

    //会话有效期 默认一周 七天
    public static long period_day = 7*(24*60*60*1000);

}
