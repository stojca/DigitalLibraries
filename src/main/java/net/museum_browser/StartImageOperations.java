package net.museum_browser;

import org.apache.lucene.queryparser.classic.ParseException;

public class StartImageOperations {

    public static void image_operations(String image_path) throws ParseException
    {
        try {
            //ImageIndexer.index_images();
            SearcherImage.searchImage(image_path);

        }
        catch (Exception e)
        {

        }


    }
}
