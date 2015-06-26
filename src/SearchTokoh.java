
import crawlertokoh.SearchObjectTokoh;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ahmadluky
 */
public class SearchTokoh {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        
        /**
         * Searching Tokoh by using <b>REGEX</b>
         * Main function for search or matching string.
         * @resutl  get nama tokoh on news page
         */
        
        String s;
        BufferedReader b    = null;
        File f              = new File(utils.TokohUntils.path_NewSPage);
        String[] paths      = f.list();
        for(String path:paths)
        {
            try {
                b = new BufferedReader(new FileReader(utils.TokohUntils.path_NewSPage + path));
                SearchObjectTokoh search = new SearchObjectTokoh();
                try {
                    while ((s  = b.readLine()) != null) {
                        search.RegexKetua(s, utils.TokohUntils.NAMA_TOKOH);
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
