package net.museum_browser;


import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Indexer {


    public static  IndexWriter writer;
    public Indexer(String indexDirectoryPath) throws IOException
    {

        /*FSDirectory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));

        //create the indexer
        IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
        writer = new IndexWriter(indexDirectory, conf);*/



        FSDirectory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexWriterConfig conf = new IndexWriterConfig(new SimpleAnalyzer());
        //create the indexer
        writer = new IndexWriter(indexDirectory, conf);

    }
    public void close() throws CorruptIndexException, IOException
    {
        writer.close();
    }
    private Document getDocument(File file) throws IOException
    {

        InputStream stream = new FileInputStream(file);
        Document document = new Document();

        Field pathField = new StringField("path", file.toString(), Field.Store.YES);
        document.add(pathField);

        document.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));


        Field contentField = new Field(Constants.CONTENTS, new FileReader(file), new FieldType());
        Field fileNameField = new Field(Constants.FILE_NAME, file.getName(), TextField.TYPE_STORED);
        Field filePathField = new Field(Constants.FILE_PATH, file.getCanonicalPath(), TextField.TYPE_STORED);


        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);
        return document;
    }


    private void indexFile(File file) throws IOException
    {
        System.out.println("Indexing "+file.getCanonicalPath());
        Document document = getDocument(file);
        writer.addDocument(document);


    }
    public int createIndex(String dataDirPath, FileFilter filter) throws IOException
    {
        //get all files in the data directory
        File dir = new File(dataDirPath);
        boolean exists = dir.exists();

        if(exists)
        {
            File[] files = new File(dataDirPath).listFiles();
            for (File file : files)
            {
                if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file) )
                {
                    indexFile(file);
                }
            }
            return writer.numDocs();
        }
        else
        {
            return -1;
        }
    }
}
