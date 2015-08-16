
import crawlertwitter.TwitterData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author ahmadluky
 */
public class GetTwitterBySeach {
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        
        /**
         * Main function for get <b>SEARCH</b> data twitter by keyword defined in directory result
         * file name keyword : nama_tokoh.txt
         */
        
        try {
            BufferedReader buf;
            String sdata;
            buf                   = new BufferedReader(new FileReader(utils.TokohUntils.NAMA_TOKOH_SEARCH));
            
            while ((sdata  = buf.readLine()) != null) 
            {
                TwitterData cr      = new TwitterData();
                System.out.println(sdata+" >> ");
                cr.datasearch(sdata);
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetTwitterBySeach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
