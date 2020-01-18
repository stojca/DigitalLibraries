package net.museum_browser;

import javax.swing.*;

public class SearchByTextResults extends JFrame{
    public static JFrame searchResults = null;
    public JPanel SearchResultsPanel;
    public JTextField term = new JTextField();

    public SearchByTextResults(String searched_term)
    {
        super("Searching for term");
        initialize(searched_term);
    }

    public void initialize(String searched_term)
    {
        searchResults = new JFrame("SearchByTextResults");
        searchResults.setContentPane(this.SearchResultsPanel);
        term.setText(searched_term);
        searchResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchResults.setSize(800,800);
        searchResults.setVisible(true);
    }


}
