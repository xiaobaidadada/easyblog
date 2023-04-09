package fun.xb.full_text.base;

import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

public class insert {

    public static void main(String[] args) throws IOException, ParseException {

        //前提条件
        Directory dir = FSDirectory.open(Paths.get("D:\\databases_data\\lucene")); //存放索引的 文件系统目录
        Analyzer  chianses_anlyzer = new IKAnalyzer(); //中文分词器
//SmartChineseAnalyzer

        IndexWriterConfig config = new IndexWriterConfig(chianses_anlyzer);
        // 索引维护器 跟连接会话一个意思
        IndexWriter writer = new IndexWriter(dir,config);


        Document doc = new Document();
        doc.add(new StringField("name","白保祥", Field.Store.YES));
        doc.add(new TextField("sex","抉择下, 本人思来想去, 寝食难安. 通过个人博客论文，我感到这虽然是偶然的，但同时也是长期以来对自己放松要求的必然结果。 我们都知道, 只要有意义, 那么就必须慎重考虑. 问题的关键究竟为何? 既然如此, 对我个人而言，个人博客论文不仅仅是一个重大的事件，还可能会改变我的人生。 个人博客论文似乎是一种巧合，但如果我们从一个更大的角度看待问题，这似乎是一种不可避免的事实。 莫扎特说过一句富有哲理的话 : 谁和我一样用功，谁就会和我一样成功。这句话把我们带到了一个新的维度去思考这个问题。 现在, 解决个人博客论文的问题, 是非常非常重要的. 所以, 在这个以和谐为主题的社会里，人与人，就应该和谐相处，和谐发展。 虽然有负面的不好的成长经历，却让我明白了很多做人处事的道理，有些道理，可能在个人博客论文以后，才能真正的大彻大悟。 本人也是经过了深思熟虑,在每个日日夜夜思考这个问题. 生活中, 若个人博客论文出现了, 我们就", Field.Store.YES));

        Document doc1 = new Document();

        doc1.add(new StringField("name","王瑶", Field.Store.YES));
        doc1.add(new StringField("sex","抉择下, 本人思来想去, 寝食难安. 通过个人博客论文，我感到这虽然是偶然的，但同时也是长期以来对自己放松要求的必然结果。 我们都知道, 只要有意义, 那么就必须慎重考虑. 问题的关键究竟为何? 既然如此, 对我个人而言，个人博客论文不仅仅是一个重大的事件，还可能会改变我的人生。 个人博客论文似乎是一种巧合，但如果我们从一个更大的角度看待问题，这似乎是一种不可避免的事实。 莫扎特说过一句富有哲理的话 : 谁和我一样用功，谁就会和我一样成功。这句话把我们带到了一个新的维度去思考这个问题。 现在, 解决个人博客论文的问题, 是非常非常重要的. 所以, 在这个以和谐为主题的社会里，人与人，就应该和谐相处，和谐发展。 虽然有负面的不好的成长经历，却让我明白了很多做人处事的道理，有些道理，可能在个人博客论文以后，才能真正的大彻大悟。 本人也是经过了深思熟虑,在每个日日夜夜思考这个问题. 生活中, 若个人博客论文出现了, 我们就", Field.Store.YES));


        writer.addDocument(doc);
        writer.commit();
//        writer.close();


        QueryParser parser = new QueryParser("sex", chianses_anlyzer); //查询解析器

        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = parser.parse("本人思来想去");
        TopDocs docs = searcher.search(query, 100);
        StoredFields storedFields = searcher.storedFields();
        for(ScoreDoc scoreDoc : docs.scoreDocs) { //取出每条查询结果

            Document doc3 = storedFields.document(scoreDoc.doc); //scoreDoc.doc相当于docID,根据这个docID来获取文档
            System.out.println(doc3.get("name"));
            System.out.println(doc3.get("sex"));

        }


    }

}
