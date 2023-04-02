package fun.xb.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 字符串工具类
 */
public class string_util {

    static public String hash_name(String name, String param) {
        String ymd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return DigestUtils.sha1Hex(name + param + ymd);
    }
}
