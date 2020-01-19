package net.museum_browser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
    private JButton back;
    public ArrayList<JButton> buttonList = new ArrayList<>();
    public String temp_term = null;

    public int index = 0;
    public int number_of_buttons = 0;
    public int number_of_links = 0;
    public int number_of_pages = 0;

    public SearchByTextResults(String searched_term)
    {
        super("Searching for term");
        initialize(searched_term);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initButtons_();
                setButtons_prev(1);

                if(number_of_buttons >= 10)
                    prevButton.setVisible(true);
                else
                    prevButton.setVisible(false);

                if(image_paths.get(++index) == null)
                    nextButton.setVisible(false);
                else
                    nextButton.setVisible(true);

//                if(number_of_buttons >= 10)
//                    prevButton.setVisible(true);
//                else
//                    prevButton.setVisible(false);
//
//                if(image_paths.get(++index) == null)
//                    nextButton.setVisible(false);
//                else
//                    nextButton.setVisible(true);
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setButtons(false);
//                if(number_of_buttons >= 10)
//                    prevButton.setVisible(true);
//                else
//                    prevButton.setVisible(false);

                if(image_paths.get(++index) == null)
                    nextButton.setVisible(false);
                else
                    nextButton.setVisible(true);
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchResults.dispose();
                try
                {
                    image_paths.clear();
                    SearchEngine.searchEngineConstructor();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static Map<Integer, String> image_paths = new HashMap<>();

    public void fillText()
    {
        number_of_links = MainIndexer.terms.size();
        Integer i = 1;
        System.out.println("size" + MainIndexer.terms.size());
        for(String it: MainIndexer.terms)
        {
            image_paths.put(i, it);
            i++;
        }


        for(Map.Entry<Integer, String> entry: image_paths.entrySet())
            System.out.println("image " + entry.getValue());
        MainIndexer.terms.clear();
    }

    public void initialize(String searched_term)
    {
        temp_term = searched_term;
        label1.setText(searched_term);
        searchResults = new JFrame("SearchByTextResults");
        term.setText(searched_term);
        searchResults.setContentPane(this.SearchResultsPanel);
        searchResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchResults.setSize(800,800);
        searchResults.setVisible(true);

        fillText();

        setButtons(true);

        prevButton.setVisible(false);
        System.out.println("index " + number_of_buttons);
        if(number_of_buttons < 9)
            nextButton.setVisible(false);

    }

    public void initButtons_()
    {
        buttonList.add(btn1);

        buttonList.add(btn2);

        buttonList.add(btn3);

        buttonList.add(btn4);

        buttonList.add(btn5);

        buttonList.add(btn6);

        buttonList.add(btn7);

        buttonList.add(btn8);

        buttonList.add(btn9);

        buttonList.add(btn10);
    }


    public void setButtons(boolean flag)
    {
        if(flag)
            initButtons_();
        for(JButton button: buttonList)
        {
            if(image_paths.get(++index) != null && PathToOpen(index) != null)
            {
                button = initButtons(button,  PathToOpen(index));
                number_of_buttons++;
            }
            else
            {
                button.setVisible(false);
            }

        }


    }

    public int checkExists(int index)
    {
        int temp_index = index;
        boolean flag;
        while (image_paths.get(++temp_index) != null && PathToOpen(temp_index)!= null)
        {
            return temp_index;
        }
        return 0;
    }


    public void setButtons_prev(int index_)
    {

            System.out.println("sa index " + index_);
        System.out.println("to m,i " + image_paths.get(index_));

        if(checkExists(index_) != 0)
            initButtons(btn1, PathToOpen(checkExists(index_)));
        else
        {
            return;
        }

        if(checkExists(++index_) != 0)
            initButtons(btn2, PathToOpen(checkExists(index_)));
        else
        {   --index;
            return;
        }

        if(checkExists(++index_) != 0)
            initButtons(btn3, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }

        if(checkExists(++index_) != 0)
            initButtons(btn4, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }

        if(checkExists(++index_) != 0)
            initButtons(btn5, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }

        if(checkExists(++index_) != 0)
            initButtons(btn6, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }
        if(checkExists(++index_) != 0)
            initButtons(btn7, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }
        if(checkExists(++index_) != 0)
            initButtons(btn8, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }
        if(checkExists(++index_) != 0)
            initButtons(btn9, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }
        if(checkExists(++index_) != 0)
            initButtons(btn10, PathToOpen(checkExists(index_)));
        else
        {
            --index;
            return;
        }

    }




    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
    }

    public String PathToOpen(int index)
    {
        String path_to_doc = image_paths.get(index);

        String path_to_open = null;
        System.out.println("path " + path_to_doc);
        for(Map.Entry<String,  String> entry: MainStart.image_links.entrySet())
        {
            if(entry.getValue().equals(path_to_doc))
            {

                path_to_open = entry.getKey().substring(0, entry.getKey().length() - 1);
            }

        }
        if(path_to_open != null)
            return path_to_open;
        else
            return null;
    }


    public JButton initButtons(JButton button, String path){

            String appended = "<HTML><U>"+path+"</U>.</HTML>";
            System.out.println("Apendovani je " + appended);
            button.setText(appended);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorderPainted(false);
            button.setOpaque(false);


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
