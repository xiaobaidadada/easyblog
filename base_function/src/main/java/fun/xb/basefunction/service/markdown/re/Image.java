package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片
 */
public class Image {
    private static Pattern Image_PATTERN = Pattern.compile("!\\[(.*?)]\\((.*?)\\)",Pattern.MULTILINE);//使用多行匹配才能使用 $和^符号




    public static String mdLinkparse(String markdown) {
        String html = markdown;

        Matcher matcher = Image_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group();
            String context=matcher.group(1);
            String url=matcher.group(2);
            String img="<img class=\"md_img\"/ src=\""+url+"\"  alt=\""+context+"\"/>";
            matcher.appendReplacement(sb, img);//这样的做法可以更为精细的处理每一个匹配
        }

        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }
}
