package com.company;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainIndexer {
    String indexDir = "C:\\Users\\Aleksandar\\Desktop\\DigitalLibraries\\documents\\indices";
    Indexer indexer;
    Searcher searcher;
    private static List<String> terms = new ArrayList<String>();

    public static void startMethod(String searchTerm) {
        System.out.println("searchTerm is: "+ searchTerm);
        MainIndexer mainIndexer;

        try {

            mainIndexer = new MainIndexer();
            mainIndexer.indexFiles();
            //mainIndexer.search(searchTerm);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

    }

    private void indexFiles() throws IOException, ParseException
    {
        //cleanIndexFolder();
        indexer = new Indexer(indexDir);

        //indexing all files in for loop TODO
        indexer.createIndex("C:\\Users\\Aleksandar\\Desktop\\DigitalLibraries\\documents\\html", new TextFileFilter());

        long endTime = System.currentTimeMillis();
        indexer.close();
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

            //String splittedTerm = splitPath(doc.get(LuceneConstants.FILE_PATH));
            terms.add(doc.get(Constants.FILE_PATH));
        }

        //findVersions();
        //checkVersionInTerm();
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
