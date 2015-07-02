
import crawlertwitter.TwitterDataStream;
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
public class GetTwitterByStream {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        
        /**
         * Main function for get <b>STREAMING</b> data twitter by keyword defined in directory result
         * file name keyword : nama_tokoh.txt
         */
        
        try {
            BufferedReader buf;
            String sCurrentLine;
            String sParator             = ":";
            buf                         = new BufferedReader(new FileReader(utils.TokohUntils.NAMA_TOKOH));
            TwitterDataStream twitter   = new TwitterDataStream();
            
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] keyword = sCurrentLine.split(sParator);
                //String[] keyword  = {"Jokowi","jokowi_do2"};
                System.out.println(keyword[0]+" >> @"+keyword[1]);
                twitter.startTwitter(keyword);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetTwitterByStream.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
