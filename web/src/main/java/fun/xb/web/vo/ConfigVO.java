package fun.xb.web.vo;

import lombok.Data;

/**
 * 数据保存方式
 */
@Data
public class ConfigVO {

    /**
     * 保存磁盘文本中
     */

    //文章目录的位置
    private String EssayDirPath;

    //文章内容的位置
    private String EssayConPath;

    /**
     * 保存在mysql（8.0）
     */
    //数据库连接url
    private String url;

    //数据库
    private String dataBase;

    //账号
    private String username;

    //密码
    private String password;

    /**
     * 设置类型
     */
    private int type;

}
