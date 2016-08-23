
import crawlertokoh.SearchObjectTokoh;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

/**
 * Searching Tokoh by using <b>REGEX</b>
 * Main function for search or matching string.
 * @resutl  get nama tokoh on news page
 * @author ahmadluky
 */
public class SearchTokoh {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        
        String s;
        BufferedReader b    = null;
        File f              = new File(utils.TokohUntils.path_NewSPage);
        String[] paths      = f.list();
        for(String path:paths)
        {
        	System.out.println(path);
            try {
                b = new BufferedReader(new FileReader(utils.TokohUntils.path_NewSPage + path));
                SearchObjectTokoh search = new SearchObjectTokoh();
                try {
                    while ((s  = b.readLine()) != null) {
                        search.Kontekstual(s, utils.TokohUntils.NAMA_TOKOH);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SearchTokoh.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SearchTokoh.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
