package net.museum_browser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BrowseImage extends JFrame {
  JButton button ;
  JLabel label;

  public BrowseImage(){
    super("Choose image");
    button = new JButton("Browse");
    button.setBounds(300,300,100,40);
    label = new JLabel();
    label.setBounds(10,10,670,250);
    add(button);
    add(label);

    button.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {

        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        //filter the files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        //if the user click on save in Jfilechooser
        if(result == JFileChooser.APPROVE_OPTION){
          File selectedFile = file.getSelectedFile();
          String path = selectedFile.getAbsolutePath();
          label.setIcon(ResizeImage(path));

          try
          {
            StartImageOperations.image_operations(path);
          }
          catch (Exception exp)
          {
            exp.printStackTrace();
          }

        }
        //if the user click on save in Jfilechooser


        else if(result == JFileChooser.CANCEL_OPTION){
          System.out.println("No File Select");
        }
      }
    });

    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize(800,800);
    setVisible(true);
  }

  // Methode to resize imageIcon with the same size of a Jlabel
  public ImageIcon ResizeImage(String ImagePath)
  {
    ImageIcon MyImage = new ImageIcon(ImagePath);
    Image img = MyImage.getImage();
    Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(newImg);
    return image;
  }


}
