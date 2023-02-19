package fun.xb.basefunction.service.markdown.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeBlock {



    static   String tail="</pre>";
    private static final Pattern CODE_PATTERN = Pattern.compile("```(.*?)\n(.*?)```", Pattern.MULTILINE | Pattern.DOTALL);

    public static String mdCodeBlockparse(String markdown) {
        String html = markdown;

        Matcher matcher = CODE_PATTERN.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {// 如果符合条件，处理它的子内部而不是外部
            String quote = matcher.group(2);
            String language="language-";
            language+=matcher.group(1);
            String start="<pre class=\"md_code  "+language+"\" />\n";
            matcher.appendReplacement(sb, start + quote + tail);//这样的做法可以更为精细的处理每一个匹配
        }
        matcher.appendTail(sb);
        html = sb.toString();

        return html;
    }
}
