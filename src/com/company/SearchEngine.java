package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SearchEngine extends JFrame{
    private JPanel panel1;
    private JTextField textField;
    private JButton buttonSearch;
    public static JFrame searchEngine = null;

    public SearchEngine() {
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame searchResults = new JFrame("SearchResults");
                searchResults.setContentPane(new SearchResults().SearchResultsPanel);
                searchResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                searchResults.setSize(800,800);
                searchEngine.setVisible(false);
                searchResults.setVisible(true);

        }
        });

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textField.setText("");
            }
        });
    }

    public static void main(String[] args) throws IOException{
        searchEngine = new JFrame("SearchEngine");
        searchEngine.setContentPane(new SearchEngine().panel1);
        searchEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchEngine.setSize(800,800);
        searchEngine.setVisible(true);

        establishConnection();
    }

    public static void establishConnection()throws IOException{
        Document document = Jsoup.connect("http://www.getty.edu/art/collection/").get();
        String title = document.title();
        System.out.println("title is: " + document.toString());
        File file = new File("documents/html/index.html");
        if(file.createNewFile())
            System.out.println("File is created");
        else
            System.out.println("File already exists");

        FileWriter writer = new FileWriter(file);
        writer.write(document.toString());
        writer.close();

     }
}
