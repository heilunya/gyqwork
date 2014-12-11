package testlucene;
//
//import java.io.File;  
//
//
//import org.apache.lucene.document.Document;  
//import org.apache.lucene.index.Term;  
//import org.apache.lucene.search.Hits;  
//import org.apache.lucene.search.IndexSearcher;  
//import org.apache.lucene.search.TermQuery;  
//import org.apache.lucene.store.FSDirectory;  
///** 
// * This class is used to demonstrate the  
// * process of searching on an existing  
// * Lucene index 
// * 
// */  
//public class TxtFileSearcher {  
//    public static void main(String[] args) throws Exception{  
//        //Logger logger=Logger.getLogger(TxtFileSearcher.class);  
//        //要查询的词组  
//        String queryStr = "com.log4j.test.TestLog.main";  
//        //索引地址  
//        File indexDir = new File("D:\\luceneIndex");  
//        //取得索引字典  
//        FSDirectory directory = FSDirectory.open(indexDir);  
//        //建立查询  
//        IndexSearcher searcher = new IndexSearcher(directory);  
//        //查询的索引地址是否存在  
//        if(!indexDir.exists()){  
//            System.out.println("The Lucene index is not exist");  
//            return;  
//        }  
//        //建立term 查询docuemnt中contents中的内容（内容要转为大字）  
//        Term term = new Term("contents",queryStr.toLowerCase());  
//        //进行查询  
//        TermQuery luceneQuery = new TermQuery(term);  
//        //生成结果  
//        Hits hits = searcher.search(luceneQuery,10);  
//        for(int i = 0; i < hits.length(); i++){  
//            //取得结果中的dowuemnt  
//            Document document = hits.doc(i);  
//            //取得返回的path属性  
//            System.out.println("File: " + document.get("path"));  
//        }  
//    }  
//}  
import java.io.File;   
import org.apache.lucene.document.Document;   
import org.apache.lucene.index.Term;   
import org.apache.lucene.search.Hits;   
import org.apache.lucene.search.IndexSearcher;   
import org.apache.lucene.search.TermQuery;   
import org.apache.lucene.store.FSDirectory;   
/**  
* This class is used to demonstrate the  
* process of searching on an existing  
* Lucene index  
*  
*/   
public class TxtFileSearcher {   
    public static void main(String[] args) throws Exception{   
       String queryStr = "hello";   
       //This is the directory that hosts the Lucene index   
       File indexDir = new File("/home/luceneIndex");   
       FSDirectory directory = FSDirectory.getDirectory(indexDir,false);   
       IndexSearcher searcher = new IndexSearcher(directory);   
       if(!indexDir.exists()){   
            System.out.println("The Lucene index is not exist");   
            return;   
       }   
       Term term = new Term("contents",queryStr.toLowerCase());   
       TermQuery luceneQuery = new TermQuery(term);   
       Hits hits = searcher.search(luceneQuery);   
       for(int i = 0; i < hits.length(); i++){   
            Document document = hits.doc(i);   
            System.out.println("File: " + document.get("path"));   
       }   
      searcher.close();  
    }   
}   
