package fun.xb.easyFullTextorm.service.datasource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

public class EasyFullTextSource {

    private Directory dir;

    private IndexWriterConfig indexWriterConfig;

//    /**
//     * 读索引连接池子，线程安全，创建这个对象不需要连接，并发速度应该
//     */
//   private Queue<IndexReader> IndexReaderSetPool = new LinkedBlockingQueue();
//



    //配置
    private sourConfig config;

    //禁止使用
    private EasyFullTextSource() {

    }

    public Analyzer getAnalyzer(){
        //获取特定的分词器 ik
        return new IKAnalyzer();
    }

    public EasyFullTextSource(sourConfig config) {
        this.config = config;

        Analyzer chianses_anlyzer = getAnalyzer(); //中文分词器
        this.indexWriterConfig = new IndexWriterConfig(chianses_anlyzer);
        try {
            this.dir =  FSDirectory.open(Paths.get(config.home_path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 获取唯一的写对象，没有连接池，一直保持不关闭，不用归还，支持线程堵塞，所以写操作没有读操作快
     *
     * @return
     * @throws IOException
     */
    public synchronized IndexWriter getWriter()  {
        //考虑加锁
        try {
            return new IndexWriter(dir, indexWriterConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //从池子中拿出一个读对象，只是创建对象，不需要建立连接，速度应该不慢
    public synchronized IndexReader getOneReader() throws IOException {

//        IndexReader indexReader = IndexReaderSetPool.poll();
//        if(indexReader!=null)
//        return indexReader;
//        else
        return DirectoryReader.open(dir);
    }



    public synchronized IndexSearcher getSearcher(){

        try {
            return  new IndexSearcher(getOneReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * 归还池子中的连接数量
//     * @param indexReader
//     */
//    public void giveBackReader(IndexReader indexReader){
//
//        //todo 没有归还的对象会不会自动销毁？
//        if(IndexReaderSetPool.size()<=config.inderReaderPoolInitSize)
//        IndexReaderSetPool.add(indexReader);
//
//    }


}
