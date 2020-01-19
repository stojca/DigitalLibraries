package net.museum_browser;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainIndexer {
    //String indexDir = "C:\\Users\\Aleksandar\\Desktop\\DigitalLibraries\\documents\\indices";
    String indexDir = "documents/indices";
    Indexer indexer;
    Searcher searcher;
    public static List<String> terms = new ArrayList<String>();

    public static void startMethod(String searchTerm){
        //System.out.println("searchTerm is: "+ searchTerm);
        MainIndexer mainIndexer;

        try {

            mainIndexer = new MainIndexer();
            mainIndexer.indexFiles();
            mainIndexer.search(searchTerm);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


    }

    private void indexFiles() throws IOException
    {
        //cleanIndexFolder();
        indexer = new Indexer(indexDir);

        //indexer.createIndex("documents/html/searchHtmls", new TextFileFilter());

        long endTime = System.currentTimeMillis();

    }

    private void search(String searchQuery) throws IOException, ParseException {
        System.out.println(searchQuery);
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();
        System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime));

        for(ScoreDoc scoreDoc : hits.scoreDocs)
        {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: " + doc.get(Constants.FILE_PATH));
            String temp = doc.get(Constants.FILE_PATH).split("searchHtmls")[1];
            System.out.println("file is " + temp.substring(1, temp.length()));
            terms.add(temp.substring(1, temp.length()));

        }
        indexer.close();
        searcher.close();

    }


    private void cleanIndexFolder() {
        File directory = new File(indexDir);

        File[] files = directory.listFiles();

        for (File file : files)
        {
            if (!file.delete())
                System.out.println("Failed to delete "+file);
        }
    }
}
