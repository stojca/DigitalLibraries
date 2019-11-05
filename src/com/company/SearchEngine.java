package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class  SearchEngine extends JFrame{
    private static Document document = null;
    private JPanel mainView;
    private JTextField searchField;
    private JButton buttonSearch;
    public static JFrame searchEngine = null;
    public Map<Integer, String> collectionOfDocuments;
    public static String searchTerm;

    public static void setDocument(Document document) {
        SearchEngine.document = document;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public static void setSearchEngine(JFrame searchEngine) {
        SearchEngine.searchEngine = searchEngine;
    }

    public SearchEngine() {
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSearchTerm(searchField.getText());
                JFrame searchResults = new JFrame("SearchResults");
                searchResults.setContentPane(new SearchResults().SearchResultsPanel);
                searchResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                searchResults.setSize(800,800);
                searchEngine.setVisible(false);
                searchResults.setVisible(true);
                MainIndexer.startMethod(searchTerm);
        }
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                searchField.setText("");
            }
        });
    }

    public static void main(String[] args) throws IOException{
        searchEngine = new JFrame("SearchEngine");
        searchEngine.setContentPane(new SearchEngine().mainView);
        searchEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchEngine.setSize(800,800);
        searchEngine.setVisible(true);
        setSearchEngine(searchEngine);

        establishConnection();

        createDocuments();

    }

    public static void establishConnection()throws IOException{
        Document document = Jsoup.connect("http://www.getty.edu/art/collection/").get();
        setDocument(document);


    }

    public static void createDocuments()throws IOException
    {
        String title = document.title();
        System.out.println("title is: " + document.toString());
        String path = "documents/html/" + title + ".html";
        File file = new File(path);
        if(file.createNewFile())
            System.out.println("File is created");
        else
            System.out.println("File already exists");

        FileWriter writer = new FileWriter(file);
        writer.write(document.toString());
        writer.close();

        Elements links = document.select("a[href]");
        for(Element link: links)
        {
            System.out.println("\nlink: " + link.attr("href"));
            System.out.println("\ntext: " + link.text());
        }

    }

}
