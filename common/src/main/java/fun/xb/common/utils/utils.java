package fun.xb.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {

    static public String regex(String context,StringBuffer Directory) {
        /*
        * 该函数是转换md文件到html文件的转换器
        * 只提供一下几项md格式
        * 1. 标题 h1 h6
        * 2. 加粗
        * 3. `代码片段
        * 4. ``` 代码段
        * 5. > 段落,先取消，有bug
        * 6. *** 分割线
        * 7. 超链接
        * 8. 有序和无序 -
        * 9. <h1>目录
        * */

//        System.out.println(context);
        Pattern pattern = Pattern.compile("(?<=^[ \t]{0,200})#(?= [ \t]*?.*?\n)", Pattern.MULTILINE);
        Pattern p2 = Pattern.compile("(?<=^[ \t]{0,200}<h1>.{0,200})$", Pattern.MULTILINE);

        //转换  #
        Matcher matcher = pattern.matcher(context);
        context = matcher.replaceAll("<h1>");
        context = p2.matcher(context).replaceAll("</h1>\n");

        //添加目录,只添加<h1>
//        System.out.println(context);
        int d=0;
        Pattern h1=Pattern.compile("<h1>.*?</h1>");
        if((h1.matcher(context).find())) {
            Directory.append("<div class=\"md_dir\" >");
            while (h1.matcher(context).find()) {
                Matcher p_h1 = h1.matcher(context);
                if (p_h1.find()) {
                    String d_context = p_h1.group();
                    Pattern P_h = Pattern.compile("<h1>", Pattern.MULTILINE);
                    Matcher h_ = P_h.matcher(d_context);
                    if (h_.find()) {
                        d_context = h_.replaceFirst("<h1 id=\"" + "md_d" + d + "\" >");
                        d++;

                    }
//                System.out.println(d_context);
                    context = p_h1.replaceFirst(d_context);
                    Pattern h_d = Pattern.compile("(?<=<h1.{0,}>).*?(?=</h1>)", Pattern.MULTILINE);
//                System.out.println(d_context);
                    Matcher h_a = h_d.matcher(d_context);
                    h_a.find();
                    String h_dd = h_a.group();
                    Directory.append("<li ><a href=\"#" + "md_d" + (d - 1) + "\" >" + h_dd + "</a></li>");

                }

//            System.out.println(p_h1.group());
            }
            Directory.append("</div>");
        }
        Pattern p3 = Pattern.compile("(?<=^[ \t]{0,200})##(?= [ \t]*?.*?\n)", Pattern.MULTILINE);
        Pattern p4 = Pattern.compile("(?<=^[ \t]{0,200}<h2>.{0,200})$", Pattern.MULTILINE);
        context = p3.matcher(context).replaceAll("<h2>");
//        System.out.println(p4.matcher(context).replaceAll("</h2>\n"));
        context = p4.matcher(context).replaceAll("</h2>\n");

        Pattern p5 = Pattern.compile("(?<=^[ \t]{0,200})###(?= [ \t]*?.*?\n)", Pattern.MULTILINE);
        Pattern p6 = Pattern.compile("(?<=^[ \t]{0,200}<h3>.{0,200})$", Pattern.MULTILINE);
        context = p5.matcher(context).replaceAll("<h3>");
//        System.out.println(p6.matcher(context).replaceAll("</h3>\n"));
        context = p6.matcher(context).replaceAll("</h3>\n");

        Pattern p7 = Pattern.compile("(?<=^[ \t]{0,200})####(?= [ \t]*?.*?\n)", Pattern.MULTILINE);
        Pattern p8 = Pattern.compile("(?<=^[ \t]{0,200}<h4>.{0,200})$", Pattern.MULTILINE);
        context = p7.matcher(context).replaceAll("<h4>");
//        System.out.println(p8.matcher(context).replaceAll("</h4>\n"));
        context = p8.matcher(context).replaceAll("</h4>\n");

        Pattern p9 = Pattern.compile("(?<=^[ \t]{0,200})#####(?= [ \t]*?.*?\n)", Pattern.MULTILINE);
        Pattern p10 = Pattern.compile("(?<=^[ \t]{0,200}<h5>.{0,200})$", Pattern.MULTILINE);
        context = p9.matcher(context).replaceAll("<h5>");
//        System.out.println(p10.matcher(context).replaceAll("</h5>\n"));
        context = p10.matcher(context).replaceAll("</h5>\n");


        Pattern p11 = Pattern.compile("(?<=^[ \t]{0,200})######(?= [ \t]*?.*?\n)", Pattern.MULTILINE);
        Pattern p12 = Pattern.compile("(?<=^[ \t]{0,200}<h6>.{0,200})$", Pattern.MULTILINE);
        context = p11.matcher(context).replaceAll("<h6>");
//        System.out.println(p12.matcher(context).replaceAll("</h6>\n"));
        context = p12.matcher(context).replaceAll("</h6>\n");
        //匹配url
        Pattern p_url = Pattern.compile("\\[.*?\\]\\(.*?\\)", Pattern.MULTILINE);
        Matcher matcher1 = p_url.matcher(context);






        while (p_url.matcher(context).find()) {
            //得到被替换的文本
            Matcher m = p_url.matcher(context);
            if (m.find()) {
//            System.out.println(m.group());
                //得到url
                Pattern p_after = Pattern.compile("(?<=]\\s{0,10}\\()[\\s.*?https|http].*?(?=\\))");
                Matcher m_after = p_after.matcher(m.group());
                m_after.find();
                String url = m_after.group();
                //得到删除后的url
                Pattern p_after_delete = Pattern.compile("\\(\\s*?[https|http].*?\\)");
                String delete_context = p_after_delete.matcher(m.group()).replaceFirst("");


                Pattern p_before1 = Pattern.compile("(?<=\\s{0,10})\\[(?=\\S*?)");
                Pattern p_before2 = Pattern.compile("(?<=\\S{0,200})]");
                Matcher m_before1 = p_before1.matcher(delete_context);
                String result = m_before1.replaceFirst("<a href=\"" + url + "\" class=\"md_url\" >");
                Matcher m_before2 = p_before2.matcher(result);
                result = m_before2.replaceFirst("</a>");

                m_after.replaceFirst("");
//            System.out.println(result);

                context = p_url.matcher(context).replaceFirst(result);
            }
        }

        //搞定块，只能实现一级
        Pattern p_block = Pattern.compile("(?<=^[ \t]{0,200})>(?=[ \t]*?)", Pattern.MULTILINE);
        context = p_block.matcher(context).replaceAll("<div class=\"md_block\" >");
        Pattern p_block_after = Pattern.compile("(?<=^<div class=\"md_block\" >.{0,3000})$", Pattern.MULTILINE);//3000有点小？
        context = p_block_after.matcher(context).replaceAll("</div>");
//        System.out.println(context);

        //搞定代码段
        Pattern p_code_part = Pattern.compile("(?<![``|`])`(?=.*?`)(?![`|``])", Pattern.MULTILINE);
        context = p_code_part.matcher(context).replaceAll("<p class=\"md_code_part\" >");
//        System.out.println(context);
        Pattern p_coce_part_after = Pattern.compile("(?<=<p class=\"md_code_part\" >.{0,100})`(?!['|''])", Pattern.MULTILINE);
        context = p_coce_part_after.matcher(context).replaceAll("</p>");
//        System.out.println(context);

        //搞定长断码段
        Pattern code = Pattern.compile("```(?=[\\s|\\S]*?```)", Pattern.MULTILINE);
        context = code.matcher(context).replaceAll("<pre><code class=\"md_code language\" >");
//        System.out.println(context);
        Pattern code_after = Pattern.compile("(?<=<pre><code class=\"md_code language\" >[\\s|\\S]{0,2000})```", Pattern.MULTILINE);
        context = code_after.matcher(context).replaceAll("</code></pre>");

        Pattern code_language=Pattern.compile("<code.*?language(?!-).*?\n",Pattern.MULTILINE);
        while (code_language.matcher(context).find()){
            Matcher mc=code_language.matcher(context);//需要被替换的
            String new_mc = null;
            if (mc.find()){
//                System.out.println(mc.group()+"需要被替换的");
                String c=mc.group();//整个需要被替换的字符串
                Pattern code_lan = Pattern.compile("(?<=<code class=\"md_code language\" >[\\s|\\S]{0,20}).*?(?=\n)", Pattern.MULTILINE);
                Matcher code_lan_nama=code_lan.matcher(c);//需要被替换字符串的匹配
                if(code_lan_nama.find()) {
//                    System.out.println(code_lan_nama.group()+"可以");
                    String lan_name=code_lan_nama.group();//语言的名字
                    c=code_lan_nama.replaceFirst("");//替换为空

                    Pattern lang=Pattern.compile("language",Pattern.MULTILINE);
                    c=lang.matcher(c).replaceFirst("language"+"-"+lan_name);
                    new_mc=c;//赋值新的字符串
                }

            }
//            System.out.println(new_mc+"这里");
            context=mc.replaceFirst(new_mc);
        }

//        System.out.println(context);

        //分割线，必须要在加粗的上面才行
        Pattern p_fengexian = Pattern.compile("\\*\\*\\*(?=$)|\\*\\*\\*\\*(?=$)", Pattern.MULTILINE);
        context = p_fengexian.matcher(context).replaceAll("<p class=\"md_fengexian\" />");
//        System.out.println(context);

        //加粗，必须在分割线的下
        Pattern p_jiachu = Pattern.compile("\\*\\*", Pattern.MULTILINE);
        int i = 0;
        while (p_jiachu.matcher(context).find()) {
            if (i % 2 == 0)
                context = p_jiachu.matcher(context).replaceFirst("<p class=\"md_jiachu\" >");
            else context = p_jiachu.matcher(context).replaceFirst("</p>");
            i++;
        }
//        System.out.println(context);
        /**
         * 无序列表
         */

        Pattern p_wuxu_list = Pattern.compile("-[ \t]", Pattern.MULTILINE);
        context = p_wuxu_list.matcher(context).replaceAll("<li>");
        Pattern p_wuxu_liest_last = Pattern.compile("(?<=<li>.{0,})$", Pattern.MULTILINE);//3000有点小？
        context = p_wuxu_liest_last.matcher(context).replaceAll("</li>");

//        System.out.println(context);
        //搞定有序列表
        Pattern p_youxu_list = Pattern.compile("^(?<!<br/>)[ |\t]*?\\d*?. ", Pattern.MULTILINE);
        while (p_youxu_list.matcher(context).find()) {
            Matcher m_list = p_youxu_list.matcher(context);
            String p_list;
//            if (m_list.find())
//            System.out.println(m_list.group());
            if (m_list.find()) {
                p_list = m_list.group();
                context = p_youxu_list.matcher(context).replaceFirst("<br/>" + p_list + "&nbsp;");
            }
        }
        //改变所有换行，pre标签也行
//        Pattern br=Pattern.compile("\n",Pattern.MULTILINE);
//        Matcher br_c=br.matcher(context);
//        context=br_c.replaceAll("<br/>");

        return context;
    }
    static public String gettime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
