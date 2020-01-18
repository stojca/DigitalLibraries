package net.museum_browser;

import static net.museum_browser.SearchEngine.searchEngineConstructor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MuseumBrowser {
    private JButton searchByImageButton;
    private JButton searchByTextButton;
    private JPanel mainView;
    public static JFrame museumBrowser = null;

    public static void setMuseumBrowser(JFrame museumBrowser) {
        MuseumBrowser.museumBrowser = museumBrowser;
    }

    public MuseumBrowser(boolean create) throws Exception{
        initialize();
        if(create)
            MainStart.createImages();
    }

    private void initialize() {

        museumBrowser = new JFrame("MuseumBrowser");
        museumBrowser.setContentPane(this.mainView);
        museumBrowser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        museumBrowser.setLocationRelativeTo(null);
        museumBrowser.setSize(700,550);
        museumBrowser.setVisible(true);
        setMuseumBrowser(museumBrowser);

        searchByImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                museumBrowser.dispose();
                //imageResults.setVisible(false);
                setMuseumBrowser(museumBrowser);
                new GetImage();
            }
        });
        searchByTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchEngineConstructor();

                museumBrowser.dispose();
                setMuseumBrowser(museumBrowser);

            }
        });
    }
}
