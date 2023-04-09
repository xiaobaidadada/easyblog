package fun.xb.full_text.service.datasource;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class EasyFullTextSource {

    static Directory dir;

    static {
        try {
            dir = FSDirectory.open(Paths.get("D:\\databases_data\\lucene"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
