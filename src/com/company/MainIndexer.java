package com.company;

import org.apache.lucene.queryParser.ParseException;

import java.io.File;
import java.io.IOException;

public class MainIndexer {
    String indexDir = "C:\\Users\\Aleksandar\\Desktop\\DigitalLibraries\\documents\\indices";
    Indexer indexer;

    public static void startMethod() {

        MainIndexer mainIndexer;

        try {

            mainIndexer = new MainIndexer();
            mainIndexer.indexFiles();
            //mainIndexer.search(term);
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
        cleanIndexFolder();
        indexer = new Indexer(indexDir);
        indexer.createIndex("C:\\Users\\Aleksandar\\Desktop\\DigitalLibraries\\documents\\html", new TextFileFilter());
    }

    private void search(String searchQuery) throws IOException, ParseException {

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
