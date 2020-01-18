package net.museum_browser;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SearchEngine extends JFrame{
    private static Document search_page_document = null;
    public JPanel mainView;
    private JTextField searchField;
    private JButton buttonSearch;
    private JButton backButton;
    public static JFrame searchEngine = null;
    public Map<Integer, String> collectionOfDocuments;
    public static Map<String, String> image_urls = new HashMap<>();
    public static Map<String, String> file_urls = new HashMap<>();
    public static String searchTerm;
    public static List<String> importantTerm = new ArrayList<>();
    public static  String url = "http://www.getty.edu/art/collection/";
    public static int number_of_pages = 555;
    public static String searchUrl;
    public static List<Document> listOfDocuements = new ArrayList<>();
    public static Integer number_of_objects = 0;
    public static Map<Integer, String> museum_objects = new HashMap<Integer, String>();


    public static void setSearchUrl(String searchUrl_)
    {
        searchUrl = searchUrl_;
    }


    public static void setSearch_page_document(Document search_page_document) {
        SearchEngine.search_page_document = search_page_document;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public static void setSearchEngine(JFrame searchEngine) {
        SearchEngine.searchEngine = searchEngine;
    }

    public static void searchEngineConstructor()
    {
        searchEngine = new JFrame("SearchEngine");
        searchEngine.setContentPane(new SearchEngine().mainView);
        searchEngine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchEngine.setLocationRelativeTo(null);
        searchEngine.setSize(600,500);
        searchEngine.setVisible(true);
        setSearchEngine(searchEngine);
    }

    public SearchEngine() {

        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSearchTerm(searchField.getText());
                new SearchByTextResults(searchTerm);
                searchEngine.setVisible(false);
                MainIndexer.startMethod(searchTerm);
            }
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                searchField.setText("");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new MuseumBrowser();
                searchEngine.setVisible(false);
            }
        });
    }

    public static void main(String[] args) throws IOException, Exception{


        //searchEngineConstructor();
        //get all search pages
        //for(int i = 0; i <= number_of_pages; i++)
        //establishConnection(i);

        //web crawling
        //addImportantTerms();??

        //crete images
        //createImages();

        //
        //mapUrlWithObject();

    }

    public static void establishConnection(int number_of_page)throws IOException{
        String searchUrl = url + "search/?pg=" + String.valueOf(number_of_page);
        setSearchUrl(searchUrl);
        System.out.println(searchUrl);
        Document current_document = Jsoup.connect(searchUrl).get();
        setSearch_page_document(current_document);
        listOfDocuements.add(current_document);
        createDocuments(String.valueOf(number_of_page));
    }

    public static void createDocuments(String number_of_page)throws IOException
    {
        String title = search_page_document.title();
        //System.out.println("title is: " + search_page_document.toString());
        String path = "documents/html/" + title + number_of_page +  ".html";
        File file = new File(path);
        if(file.createNewFile())
            System.out.println("File is created");
        else
            System.out.println("File already exists");

        FileWriter writer = new FileWriter(file);
        writer.write(search_page_document.toString());
        writer.close();
        createHtmlsForIndexing();
    }

    public static void createHtmlsForIndexing()throws IOException
    {
        String previous_href_link = null;

        Elements links = search_page_document.select("a[href]");


        for(Element link: links) {
            if (link.attr("href").contains("objects") && !link.attr("href").equals(previous_href_link)) {
                System.out.println("\nlink: " + link.attr("href"));
                //System.out.println("\ntext: " + link.text());

                //create search files
                Document search_object_on_page = Jsoup.connect(link.attr("href")).timeout(10 * 1000).get();

                String store_path = "documents/html/searchHtmls/" + "museum_object_" + String.valueOf(++number_of_objects) + ".html";
                museum_objects.put(number_of_objects, link.attr("href"));
                File file = new File(store_path);

                if(file.createNewFile())
                {
                    System.out.println("File is created");
                    FileWriter writer = new FileWriter(file);
                    writer.write(search_object_on_page.toString());
                    writer.close();

                }
                else
                    System.out.println("File already exists");


                previous_href_link = link.attr("href");
            }
        }
    }

    public static void parseFile(String filepath)
    {
        String image_url = null;
        String image_link = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                if(line.contains("<meta name=\"image\" content="))
                {
                    //System.out.println("image link ");
                    //System.out.println(line);
                    //System.out.println(line.substring(29, line.length() - 2));
                    image_url = line.substring(30, line.length() - 3);
                }
                if(line.contains("<link rel=\"canonical\" href="))
                {
                    //System.out.println("url link ");
                    //System.out.println(line);
                    //System.out.println(line.substring(29, line.length() - 2));
                    image_link = line.substring(30, line.length() - 3);
                }

                if(image_url != null && image_link != null)
                {
                    image_urls.put(image_link, image_url);
                    break;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createImages() throws Exception
    {

        String imageUrl = "http://media.getty.edu/museum/images/web/enlarge/13553701.jpg";
        String destinationFile = "documents/images/";

        File folder = new File("documents/html/searchHtmls");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                //System.out.println(file.getAbsolutePath());
                parseFile(file.getAbsolutePath());

            }
        }

        //System.out.println("velicina mape je: " + image_urls.size());
        Integer index = 2957;



        Integer i = 0;
        for(Map.Entry<String,  String> entry: image_urls.entrySet())
        {

            //System.out.println("Key = " + entry.getKey() +
            //      ", Value = " + entry.getValue());

            //System.out.println("i is " + i);
            //System.out.println("index is " + index);
            if(i > index)
            {
                //System.out.println("uso");
                saveImage(entry.getValue(), destinationFile + "image_" + index.toString() + ".jpg");
                index++;
            }

            i++;
        }
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {

        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public static void mapUrlWithObject()
    {
        File folder = new File("documents/html/searchHtmls");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                //System.out.println(file.getAbsolutePath());
                //System.out.println(file.getName());
                parseFile_forUrl(file.getAbsolutePath(), file.getName());

            }
        }

    }

    public static void parseFile_forUrl(String file_path, String file_name)
    {
        String url = null;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file_path));
            String line = reader.readLine();
            while (line != null) {
                if(line.contains("<link rel=\"canonical\" href=\""))
                {
                    //System.out.println("image link ");
                    //System.out.println(line);
                    //System.out.println(line.substring(29, line.length() - 2));
                    url = line.substring(30, line.length() - 3);
                }

                if(url != null && file_name != null)
                {
                    file_urls.put(file_name, url);
                    break;
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
