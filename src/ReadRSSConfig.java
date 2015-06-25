
import crawlertokoh.GetReadXML;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
public class ReadRSSConfig {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        
        /**
         * Main function for <b>RSS CONFIG</b> data in directory conf
         * Rss Config for a read RSS XML from online news
         * @result  lists link content news page in online news
         * @conf    file name keyword : rss.conf
         */
        
        try {
            BufferedReader buf;
            String sCurrentLine;
            GetReadXML xmlrss     = new GetReadXML();
            String sParator       = "\t";
            buf                   = new BufferedReader(new FileReader(utils.TokohUntils.RSS_CONFIG));
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] data     = sCurrentLine.split(sParator);
                System.out.println(data[1]);
                String Getxml     = xmlrss.Getxml(new URL(data[1]), data[0]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrowlerTokoh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
