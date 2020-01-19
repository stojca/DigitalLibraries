package net.museum_browser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class SearchByTextResults extends JFrame{
    public static JFrame searchResults = null;
    public JPanel SearchResultsPanel;
    public JTextField term = new JTextField();
    private JLabel label1;
    private JButton prevButton;
    private JButton nextButton;
    private JButton btn10;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JButton btn5;
    private JButton btn6;
    private JButton btn7;
    private JButton btn8;
    private JButton btn9;

    private int index = 1;

    public SearchByTextResults(String searched_term)
    {
        super("Searching for term");
        initialize(searched_term);
    }

    public static Map<Integer, String> image_paths = new HashMap<>();

    public void fillText()
    {
        Integer i = 1;
        System.out.println("size" + MainIndexer.terms.size());
        for(String it: MainIndexer.terms)
        {
            image_paths.put(i, it);
            i++;
        }


        for(int it = 1; it < image_paths.size(); it++)
            System.out.println("image " + image_paths.get(it));
        MainIndexer.terms.clear();
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

        fillText();

        btn1 = initButtons(btn1, index);
        btn2 = initButtons(btn2, ++index);
        btn3 = initButtons(btn3, ++index);
        btn4 = initButtons(btn4, ++index);
        btn5 = initButtons(btn5, ++index);
        btn6 = initButtons(btn6, ++index);
        btn7 = initButtons(btn7, ++index);
        btn8 = initButtons(btn8, ++index);
        btn9 = initButtons(btn9, ++index);
        btn10 = initButtons(btn10,++index);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                btn1 = initButtons(btn1, ++index);
                btn2 = initButtons(btn2, ++index);
                btn3 = initButtons(btn3, ++index);
                btn4 = initButtons(btn4, ++index);
                btn5 = initButtons(btn5, ++index);
                btn6 = initButtons(btn6, ++index);
                btn7 = initButtons(btn7, ++index);
                btn8 = initButtons(btn8, ++index);
                btn9 = initButtons(btn9, ++index);
                btn10 = initButtons(btn10,++index);

                if(image_paths.size() <= index )
                    nextButton.setVisible(false);
                else
                {
                    nextButton.setVisible(true);
                }

                if( 10 >index)
                {
                    prevButton.setVisible(false);
                }

            }
        });
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn10 = initButtons(btn10,--index);
                btn9 = initButtons(btn9, --index);
                btn8 = initButtons(btn8, --index);
                btn7 = initButtons(btn7, --index);
                btn6 = initButtons(btn6, --index);
                btn5 = initButtons(btn5, --index);
                btn4 = initButtons(btn4, --index);
                btn3 = initButtons(btn3, --index);
                btn2 = initButtons(btn2, --index);

                btn1 = initButtons(btn1, --index);

                if(image_paths.size() <= index )
                    nextButton.setVisible(false);
                else
                {
                    nextButton.setVisible(true);
                }

                if((10 <= index) )
                {
                    prevButton.setVisible(false);
                }

            }
        });

    }

    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
    }
    public JButton initButtons(JButton button, int index){
        String path_to_doc = image_paths.get(index);


        String path_to_open = null;
        System.out.println("path " + path_to_doc);
        for(Map.Entry<String,  String> entry: MainStart.image_links.entrySet())
        {
            if(entry.getValue().equals("museum_object_1261.html"))
                System.out.println("masp ");

            if(entry.getValue().equals(path_to_doc))
            {

                path_to_open = entry.getKey().substring(0, entry.getKey().length() - 1);
            }
            else
            {
                //System.out.println("doesn't exists");
            }
        }


        String appended = "<HTML><U>"+path_to_open+"</U>.</HTML>";
        System.out.println("Apendovani je " + appended);
        button.setText(appended);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setOpaque(false);
        String path = path_to_open;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    URI uri = new URI(path);
                    open(uri);

                } catch (URISyntaxException ex) {
                    //do nothing
                }
            }
            });
        return button;
    }
}
