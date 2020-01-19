package net.museum_browser;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainStart {
  public static Map<String, String> image_urls = new HashMap<>();
  public static Map<String, String> image_links = new HashMap<>();
  public static boolean isCreated = false;


  public static void main(String[] args) throws Exception{

    //reorderImages();
    //createImages();
    MuseumBrowser musuem = new MuseumBrowser(true);

  }

  public static void createImages() throws Exception
  {

    File folder = new File("documents/html/searchHtmls");
    File[] listOfFiles = folder.listFiles();

    System.out.println("ovde");
    for (File file : listOfFiles) {
      if (file.isFile()) {
        //System.out.println(file.getAbsolutePath());
        parseFile(file.getAbsolutePath(), file.getName());

      }
    }
    System.out.println("size je: " + image_urls.size());

//    //System.out.println("velicina mape je: " + image_urls.size());
//    Integer i = 0;
//    //Integer index_temp = 114;
//    for(Map.Entry<String,  String> entry: image_urls.entrySet())
//    {
//
//      //System.out.println("Key = " + entry.getKey() +
//      //      ", Value = " + entry.getValue());
//
//      //System.out.println("i is " + i);
//      //System.out.println("index is " + index);
//
//        //index = 0;
//      try {
//        System.out.println("na tom sam " + i);
//        //if(i - index_temp == 2)
//        //if(i > index_temp)
//        //{
//        String numberOnly= entry.getValue().split("enlarge")[1];
//        System.out.println("-->dsadsada  " + entry.getValue());
//        System.out.println("--> " + numberOnly);
//
//          saveImage(entry.getValue(), "documents/images_with_id/" + numberOnly.substring(1, numberOnly.length()));
//          //index_temp++;
//        //}
//
//        //index++;
//        i++;
//      }
//      catch (Exception e)
//      {
//        e.printStackTrace();
//      }
//
//
//
//    }

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

  public static void parseFile(String filepath, String name)
  {
    //System.out.println("file path " + filepath);
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

        if(image_link != null)
        {
          image_links.put(image_link, name);

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

}
