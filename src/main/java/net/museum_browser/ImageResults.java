package net.museum_browser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageResults extends JFrame{
    private  JPanel mainPanel;
    private JLabel mainLabel;
    private JLabel label_1;
    private JLabel label_2;
    private JButton button1;
    private JButton prev;
    private JButton next;
    public int prevVisible = 0;
    public int number_of_images = 0;
    public static JFrame imageResults = null;

    public static Map<Integer, String> image_paths = new HashMap<>();
    public static Map<Integer, String> image_urls = new HashMap<>();
    Integer index_label_1 = 0;


    public void fillImages()
    {

        number_of_images = SearcherImage.image_paths.size();
        if(SearcherImage.image_paths.size() % 2 != 0)
            number_of_images --;

        System.out.println("searcher " + SearcherImage.image_paths.size());
        Integer  index = 1;
        for(String it: SearcherImage.image_paths)
        {
            image_paths.put(index, it);
            index ++;
        }

        SearcherImage.image_paths.clear();
    }


    public ImageResults(String path)
    {
        super("SetImage");
        imageInit(path);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageResults.dispose();
                try
                {
                    image_paths.clear();
                    new GetImage();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image_1  = ResizeImageLabel("documents/imagesIDs/" + image_paths.get(++index_label_1));
                System.out.println("next 1" + index_label_1);
                ImageIcon image_2  = ResizeImageLabel("documents/imagesIDs/" + image_paths.get(++index_label_1));
                System.out.println("next 2" + index_label_1);

                label_1.setIcon(image_1);
                label_2.setIcon(image_2);

                //TODO 6 is number of image size
                if(index_label_1 == number_of_images)
                    next.setVisible(false);
                else
                {
                    next.setVisible(true);
                }

                if(index_label_1 > 2)
                {
                    prev.setVisible(true);
                }

            }
        });
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index_label_1 -=2;
                System.out.println("prev 1" + index_label_1);
                ImageIcon image_1  = ResizeImageLabel("documents/imagesIDs/" + image_paths.get(index_label_1));
                label_2.setIcon(image_1);

                index_label_1 --;
                System.out.println("prev 2" + index_label_1);
                ImageIcon image_2  = ResizeImageLabel("documents/imagesIDs/" + image_paths.get(index_label_1));
                label_1.setIcon(image_2);
                if(index_label_1 == number_of_images)
                    next.setVisible(false);
                else
                {
                    next.setVisible(true);
                }

                if(index_label_1 < 2)
                {
                    prev.setVisible(false);
                }
                index_label_1 ++;
                System.out.println("prev 3" + index_label_1);
            }
        });

        label_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String path_to_open = null;
                for(Map.Entry<String,  String> entry: MainStart.image_urls.entrySet())
                {
                    Integer index = index_label_1 - 1;
                    System.out.println("1--> " + entry.getValue());

                    System.out.println(image_paths.get(index));

                    if(entry.getValue().contains(image_paths.get(index)))
                    {
                        path_to_open = entry.getKey();
                    }
                }
                try
                {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            if(path_to_open != null)
                            {
                                System.out.println("-->" + path_to_open);
                                URI uri = new URI(path_to_open);
                                desktop.browse(uri);
                            }
                            else
                            {
                                System.out.println("path could not be found");
                            }

                        } catch (IOException ex) {
                            // do nothing
                        } catch (URISyntaxException ex) {
                            //do nothing
                        }
                    } else {
                        //do nothing

                    }
                }
                catch (Exception exc)
                {
                   exc.printStackTrace();
                }


            }
        });
        label_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String path_to_open = null;
                //get path to display
                for(Map.Entry<String,  String> entry: MainStart.image_urls.entrySet())
                {
                    Integer index = index_label_1;
                    System.out.println("1--> " + entry.getValue());
                    System.out.println("2--> " + image_paths.get(index));

                    if(entry.getValue().contains(image_paths.get(index)))
                    {
                        path_to_open = entry.getKey();
                    }
                }

                try
                {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            if(path_to_open != null)
                            {
                                System.out.println("-->" + path_to_open);
                                URI uri = new URI(path_to_open);
                                desktop.browse(uri);
                            }
                            else
                            {
                                System.out.println("path could not be found");
                            }

                        } catch (IOException ex) {
                            // do nothing
                        } catch (URISyntaxException ex) {
                            //do nothing
                        }
                    } else {
                        //do nothing

                    }
                }
                catch (Exception exc)
                {
                    exc.printStackTrace();
                }
            }
        });
    }



    public void imageInit(String path)
    {

        imageResults = new JFrame("ImageResults");
        imageResults.setContentPane(this.mainPanel);
        imageResults.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imageResults.setSize(1200,800);
        imageResults.setVisible(true);

        fillImages();

        ImageIcon image  = ResizeImage(path);
        mainLabel.setIcon(image);

        ImageIcon image_1  = ResizeImageLabel("documents/imagesIDs/" + image_paths.get(1));
        label_1.setIcon(image_1);


        ImageIcon image_2  = ResizeImageLabel("documents/imagesIDs/" +image_paths.get(2));
        label_2.setIcon(image_2);
        index_label_1 +=2;
        prev.setVisible(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }

    public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(600, 300, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }


    public ImageIcon ResizeImageLabel(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }


}
