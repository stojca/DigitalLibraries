package net.museum_browser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static net.museum_browser.SearchEngine.searchEngineConstructor;


public class MuseumBrowser {
    private JButton searchByImageButton;
    private JButton searchByTextButton;
    private JPanel mainView;
    public static JFrame museumBrowser = null;

    public static void setMuseumBrowser(JFrame museumBrowser) {
        MuseumBrowser.museumBrowser = museumBrowser;
    }

    public MuseumBrowser() {
        searchByImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                museumBrowser.setVisible(false);
                setMuseumBrowser(museumBrowser);
                new BrowseImage();
            }
        });
        searchByTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchEngineConstructor();

                museumBrowser.setVisible(false);
                setMuseumBrowser(museumBrowser);

            }
        });
    }

    public static void main(String[] args){

        museumBrowser = new JFrame("MuseumBrowser");
        museumBrowser.setContentPane(new MuseumBrowser().mainView);
        museumBrowser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        museumBrowser.setLocationRelativeTo(null);
        museumBrowser.setSize(800,800);
        museumBrowser.setVisible(true);
        setMuseumBrowser(museumBrowser);

    }
}
