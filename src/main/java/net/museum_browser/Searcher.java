package net.museum_browser;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


public class Searcher {
    IndexSearcher indexSearcher;
    QueryParser queryParser;
    Query query;
    public Searcher(String indexDirectoryPath) throws IOException
    {

        /*IndexReader indexDirectory = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryPath)));

        indexSearcher = new IndexSearcher(indexDirectory);
        queryParser = new QueryParser(Constants.CONTENTS, new WhitespaceAnalyzer());*/

        IndexReader indexDirectory = DirectoryReader.open(Indexer.writer);
        indexSearcher = new IndexSearcher(indexDirectory);

        queryParser = new QueryParser(Constants.CONTENTS, new StandardAnalyzer());

    }
    public TopDocs search(String searchQuery) throws IOException, ParseException
    {
        query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, Constants.MAX_SEARCH);
    }
    public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException
    {
        return indexSearcher.doc(scoreDoc.doc);
    }
    public void close() throws IOException
    {
        //indexSearcher.close();
    }
}
