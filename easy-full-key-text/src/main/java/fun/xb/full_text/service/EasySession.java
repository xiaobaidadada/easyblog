package fun.xb.full_text.service;


import fun.xb.full_text.service.datasource.EasyFullTextSource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import fun.xb.full_text.util.DtoUtil;
import fun.xb.full_text.util.data_record_map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

public class EasySession<T> implements FullTextSession<T> {


    //每一次创建对象，这个就会重新生成一次 todo 和easyorm问题一样，需要修改，现在没有spring会生成多个连接
    private EasyFullTextSource source;

    IndexWriter indexWriter;

    public EasySession(EasyFullTextSource source) {
        this.source = source;
        this.indexWriter = source.getWriter();

    }

    @Override
    public void insert(Object dto) {


        IndexWriter writer = this.indexWriter;
        Document document = getNotNullAllDocumentProperties(dto);


        try {
            writer.addDocument(document);
            writer.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 不支持直接更新，而是先删除后添加
     *
     * @param dto
     * @param query_dto
     */
    @Override
    public long update(T dto, T query_dto) {
        //会直接因为多线程卡死？
//        IndexWriter writer = source.getWriter();
        //删除
        long num = delete(query_dto);
        if (num != 0)
            //添加
            insert(dto);

        return num;
    }

    @Override
    public long delete(T query_dto) {

        BooleanQuery booleanQuery = getBooleanQueryForDto(query_dto);
        IndexWriter writer = this.indexWriter;
        long num = 0;
        try {
            num = writer.deleteDocuments(booleanQuery);
            writer.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return num;
    }

    @Override
    public List<T> query(T dto) {
        List<T> resultList = new ArrayList<>();

            IndexSearcher indexSearcher = source.getSearcher();
//            Analyzer analyzer = source.getAnalyzer();
//            String[] key = new String[]{"1234","nonquery","sql",};
//            String [] fields =  new String[]{"id","的","v"};//长度要相等
//                Query parser = MultiFieldQueryParser.parse(key,fields,analyzer);
            try {
                BooleanQuery booleanQuery = getBooleanQueryForDto(dto);
                //最高设置一下
                TopDocs docs = indexSearcher.search(booleanQuery, 10000);
                StoredFields storedFields = indexSearcher.storedFields();
                for (ScoreDoc scoreDoc : docs.scoreDocs) { //取出每条查询结果

                    Document doc3 = storedFields.document(scoreDoc.doc); //scoreDoc.doc相当于docID,根据这个docID来获取文档
                    System.out.println(doc3.get("a"));
                    System.out.println(doc3.get("b"));
                    System.out.println(doc3.get("id"));
                    T o = (T) dto.getClass().getDeclaredConstructor().newInstance();
                    data_record_map map = DtoUtil.getProperties(o);
                    Set<String> keys = map.keySet();
                    for (String name : keys) {
                        DtoUtil.setPartOfProperty(o, name, doc3.get(name));
                    }
//                    String keyName = map.getKey();
                    if (doc3.get("id") != null) {
                        DtoUtil.setId(o, doc3.getField("id"));
                    }
                    resultList.add(o);

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }



        return resultList;
    }


    private BooleanQuery getBooleanQueryForDto(Object dto) {

        Analyzer analyzer = source.getAnalyzer();
        data_record_map all_annotaed_fields = DtoUtil.getProperties(dto);
        //设置查询条件
        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        Set<Map.Entry<String, String>> entrySet = all_annotaed_fields.entrySet();
        if (all_annotaed_fields.getKey() == null) {
            for (Map.Entry entry : entrySet) {
                if (entry.getValue() != null) {
                    QueryParser parser1 = new QueryParser((String) entry.getKey(), analyzer); //语句查询解析器，或者用 Query下的各种TermQuery来查
                    Query query = null;
                    try {
                        query = parser1.parse((String) entry.getValue());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    builder.add(query, BooleanClause.Occur.MUST);
                }

            }
        } else {
            int type = all_annotaed_fields.getkeyType();
            if(type==data_record_map.get_long_key_type){
                Query tq = LongField.newExactQuery("id", all_annotaed_fields.getKey());
                builder.add(tq, BooleanClause.Occur.MUST);
            } else if (type==data_record_map.get_string_key_type) {
                String id = all_annotaed_fields.getKey();
                Query tq = new TermQuery(new Term("id",id));
                builder.add(tq, BooleanClause.Occur.MUST);
            }

        }


        return builder.build();
    }

    private Document getNotNullAllDocumentProperties(Object dto) {
        data_record_map all_annotaed_fields = DtoUtil.getProperties(dto);

        Document document = new Document();

        //设置key
        int type = all_annotaed_fields.getkeyType();
        if (type == data_record_map.get_long_key_type) {
            Long value = all_annotaed_fields.getKeyByType(type);
            document.add(new LongField("id", value));
            document.add(new StoredField("id", value));
        } else if (type == data_record_map.get_string_key_type) {
            String key = all_annotaed_fields.getKey();
            //设置保存 YES String 不会分词，会被索引
            document.add(new StringField("id", key, org.apache.lucene.document.Field.Store.YES));
        }

        //设置values
        Set<Map.Entry<String, String>> entrySet = all_annotaed_fields.entrySet();
        for (Map.Entry entry : entrySet) {
            //text 会分词
            if (entry.getValue() != null)
                document.add(new TextField((String) entry.getKey(), (String) entry.getValue(), org.apache.lucene.document.Field.Store.YES));
        }
        return document;
    }

}
