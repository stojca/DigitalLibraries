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
    public static JFrame imageResults = null;

    public static Map<Integer, String> image_paths = new HashMap<>();
    Integer index_label_1 = 0;


    public void fillImages()
    {
        image_paths.put(1, "documents/images/image_1.jpg");
        image_paths.put(2, "documents/images/image_2.jpg");
        image_paths.put(3, "documents/images/image_3.jpg");
        image_paths.put(4, "documents/images/image_4.jpg");
        image_paths.put(5, "documents/images/image_5.jpg");
        image_paths.put(6, "documents/images/image_6.jpg");


    }

    public ImageResults(String path)
    {
        super("SetImage");
        imageInit(path);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageResults.dispose();
                new MuseumBrowser();
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon image_1  = ResizeImageLabel(image_paths.get(++index_label_1));
                System.out.println("next 1" + index_label_1);
                ImageIcon image_2  = ResizeImageLabel(image_paths.get(++index_label_1));
                System.out.println("next 2" + index_label_1);

                label_1.setIcon(image_1);
                label_2.setIcon(image_2);

                //TODO 6 is number of image size
                if(index_label_1 == 6)
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
                ImageIcon image_1  = ResizeImageLabel(image_paths.get(index_label_1));
                label_2.setIcon(image_1);

                index_label_1 --;
                System.out.println("prev 2" + index_label_1);
                ImageIcon image_2  = ResizeImageLabel(image_paths.get(index_label_1));
                label_1.setIcon(image_2);
                if(index_label_1 == 6)
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
                try
                {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("http://www.hotelgiovanni.cz/");
                            desktop.browse(uri);
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
                try
                {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("http://www.hotelgiovanni.cz/");
                            desktop.browse(uri);
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

        ImageIcon image  = ResizeImage(image_paths.get(1));
        mainLabel.setIcon(image);

        ImageIcon image_1  = ResizeImageLabel(image_paths.get(1));
        label_1.setIcon(image_1);


        ImageIcon image_2  = ResizeImageLabel(image_paths.get(2));
        label_2.setIcon(image_2);
        index_label_1 +=2;
        prev.setVisible(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }

    public ImageIcon ResizeImage(String ImagePath)
    {
        ImageIcon MyImage = new ImageIcon("documents/images/image_9.jpg");
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
