package net.museum_browser;

import static net.museum_browser.SearchEngine.searchEngineConstructor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GetImage extends JFrame{
  private JButton searchByImageButton;
  private JButton button1;
  private JPanel panel1;
  private JLabel label;
  public static JFrame museumBrowser = null;

  public GetImage() {
    super("Choose image");
    initialize();
  }

  private void initialize() {
    button1.addActionListener(new ActionListener() {

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
          System.out.println("User didn't select a file");
        }
      }
    });
    museumBrowser = new JFrame("GetImage");
    museumBrowser.setContentPane(this.panel1);
    museumBrowser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    museumBrowser.setLocationRelativeTo(null);
    museumBrowser.setSize(600,500);
    museumBrowser.setVisible(true);
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
