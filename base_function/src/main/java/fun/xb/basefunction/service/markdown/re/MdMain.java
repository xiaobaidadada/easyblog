package fun.xb.basefunction.service.markdown.re;

public class MdMain {

    static public String toHtml(String markdown,StringBuffer directory){

        String re= Title.mdTitleparse(markdown);

        re=ItIalic.mdBoldeparse(re);

        re=Blockquote.mdQuoteParse(re);

        re=Bold.mdBoldeparse(re);

        re=CodeBlock.mdCodeBlockparse(re);

        re=CodeSeg.mdCodeSegeparse(re);

        re=Disorder.mdDisorderparse(re);

        re=Image.mdLinkparse(re);



        re=Link.mdLinkparse(re);

        re=Order.mdOrderparse(re);

        re=SqLine.mdLineparse(re);

        re=Table.main(re);

        Directory.processDirectory(directory,re);

//        re=Br.BrTo(re); 目前无法避免 pre

        return  re;

    }
}
