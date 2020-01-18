package net.museum_browser;

import javax.swing.*;

public class SearchByTextResults extends JFrame{
    public static JFrame searchResults = null;
    public JPanel SearchResultsPanel;
    public JTextField term = new JTextField();
    private JLabel label1;

    public SearchByTextResults(String searched_term)
    {
        super("Searching for term");
        initialize(searched_term);
    }

    public void initialize(String searched_term)
    {
        label1.setText(searched_term);
        searchResults = new JFrame("SearchByTextResults");
        term.setText(searched_term);
        searchResults.setContentPane(this.SearchResultsPanel);
        searchResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchResults.setSize(800,800);
        searchResults.setVisible(true);
    }


}
