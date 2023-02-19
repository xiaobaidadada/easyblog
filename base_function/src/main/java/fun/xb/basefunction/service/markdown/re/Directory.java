package fun.xb.basefunction.service.markdown.re;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
处理目录
 */
public class Directory {

   static public void processDirectory(StringBuffer directory ,String re){
        int d=0;
        Pattern h1=Pattern.compile("<h1>.*?</h1>");
        if((h1.matcher(re).find())) {
            directory.append("<div class=\"md_dir\" >");
            while (h1.matcher(re).find()) {
                Matcher p_h1 = h1.matcher(re);
                if (p_h1.find()) {
                    String d_context = p_h1.group();
                    Pattern P_h = Pattern.compile("<h1>", Pattern.MULTILINE);
                    Matcher h_ = P_h.matcher(d_context);
                    if (h_.find()) {
                        d_context = h_.replaceFirst("<h1 id=\"" + "md_d" + d + "\" >");
                        d++;

                    }
                    re = p_h1.replaceFirst(d_context);
                    Pattern h_d = Pattern.compile("(?<=<h1.{0,}>).*?(?=</h1>)", Pattern.MULTILINE);
                    Matcher h_a = h_d.matcher(d_context);
                    h_a.find();
                    String h_dd = h_a.group();
                    directory.append("<li ><a href=\"#" + "md_d" + (d - 1) + "\" >" + h_dd + "</a></li>");

                }

            }
            directory.append("</div>");
        }

    }
}
