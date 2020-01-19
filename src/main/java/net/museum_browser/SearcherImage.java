
package net.museum_browser;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.searchers.BitSamplingImageSearcher;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Mathias Lux, mathias@juggle.at
 * Date: 25.05.12
 * Time: 12:19
 */
public class SearcherImage {
    public static List<String> image_paths = new ArrayList<>();

    public static void searchImage(String image_path) throws IOException {


        File f = new File(image_path);
        BufferedImage img = ImageIO.read(f);

        IndexReader ir = DirectoryReader.open(FSDirectory.open(Paths.get("documents/imageIndexes")));
        ImageSearcher searcher = new GenericFastImageSearcher(50, CEDD.class);
        //ImageSearcher searcher = new GenericFastImageSearcher(1, AutoColorCorrelogram.class);

        // searching with a image file ...
        ImageSearchHits hits = searcher.search(img, ir);
        System.out.println("hh " + hits.length());
        // searching with a Lucene document instance ...
//        ImageSearchHits hits = searcher.search(ir.document(0), ir);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println("uso sam");
            String fileName = ir.document(hits.documentID(i)).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
            //System.out.println("fileName " + fileName);
            String temp_string = fileName.split("with_id")[1];
            //System.out.println(temp_string.substring(1, temp_string.length()));
            //System.out.println(hits.score(i) + ": \t" + fileName);
            System.out.println("test " + temp_string.substring(1, temp_string.length()));
            image_paths.add(temp_string.substring(1, temp_string.length()));
        }
    }
}
