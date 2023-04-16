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
        Analyzer chianses_anlyzer = new IKAnalyzer(); //中文分词器
        //************ SATRT 插入开始
        IndexWriterConfig config = new IndexWriterConfig(chianses_anlyzer);
        // 索引维护器 跟连接会话一个意思
        //一个索引目录下只能有一个
        IndexWriter writer = new IndexWriter(dir, config);
        Document doc = new Document();
        doc.add(new StringField("name","白保祥", Field.Store.YES));
        doc.add(new TextField("sex","抉择下, 本论文出现了, 我们就", Field.Store.YES));
        Document doc1 = new Document();
        doc1.add(new StringField("name","王瑶", Field.Store.YES));
        doc1.add(new StringField("sex","个日日夜夜思考这个问题. 生活中, 若个人博客论文出现了, 我们就", Field.Store.YES));
        writer.addDocument(doc);
        writer.commit();
        //************** END
        //*************  SATRT查询开始
//        writer.close();
        QueryParser parser = new QueryParser("sex", chianses_anlyzer); //查询解析器
        //线程安全，可以多个同时读
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
        //************* END


    }

}
