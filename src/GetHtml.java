
import crawlertokoh.GetReadHtml;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;


/**
 *
 * @author ahmadluky
 */
public class GetHtml {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        
        
        /**
         * Get HTML code dari Uri
         * @throws java.io.FileNotFoundException
         */
        
        GetReadHtml r               = new GetReadHtml();
        Date date                   = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
        BufferedReader b            = new BufferedReader(new FileReader(utils.TokohUntils.LIST_URI));
        String s;
        try {
            while ((s  = b.readLine()) != null) {
                File file = new File("data/html/" + dateFormat.format(date) + ".html") ;
                r.GetHtml_Stream(s, file);
                System.out.println(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(GetHtml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
