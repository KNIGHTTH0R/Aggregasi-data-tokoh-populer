
import crawlertokoh.GetReadXML;
import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;

/**
 *
 * @author ahmadluky
 */
public class ReadXMLRss {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        /**
         * Read File XML in data/xml 
         * Get link uri page content news
         */
    
        File f  = null;
        String[] paths;

        f       = new File("data/xml");
        paths   = f.list();
        for(String path:paths)
        {
            GetReadXML xml = new GetReadXML();
            xml.ReadXML("data/xml/"+path);
        }
        
    }
}
