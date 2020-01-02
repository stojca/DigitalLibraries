package com.company;

import javax.swing.*;

public class SearchByTextResults extends JFrame{
    public static JFrame searchResults = null;
    public JPanel SearchResultsPanel;

    public static void searchTextConstructor()
    {
        searchResults = new JFrame("SearchByTextResults");
        searchResults.setContentPane(new SearchByTextResults().SearchResultsPanel);
        searchResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchResults.setSize(800,800);
        searchResults.setVisible(true);
    }

}
