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

    public void fillImages()
    {
        for(int i=1; i<51; i++)
        {
            String value = "documents/images/image_"+i+".jpg";
            image_paths.put(i, value);
        }


    }

    public void initialize(String searched_term)
    {
        fillImages();
        label1.setText(searched_term);
        searchResults = new JFrame("SearchByTextResults");
        term.setText(searched_term);
        searchResults.setContentPane(this.SearchResultsPanel);
        searchResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchResults.setSize(800,800);
        searchResults.setVisible(true);

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
        String appended = "<HTML><U>"+path_to_doc+"</U>.</HTML>";
        System.out.println("Apendovani je " + appended);
        button.setText(appended);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URI uri = new URI("http://java.sun.com");
                    open(uri);

                } catch (URISyntaxException ex) {
                    //do nothing
                }
            }
            });
        return button;
    }
}
