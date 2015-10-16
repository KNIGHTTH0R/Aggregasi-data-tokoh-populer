
package crawlertokoh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * @author ahmadluky
 */
public class GetReadXML {
    
    
    @SuppressWarnings("unused")
	public String Getxml(URL url, String name) {
        InputStream is = null;
        BufferedWriter out = null;
        BufferedReader br;
        File file = new File("data/xml/"+ name + ".xml");
        try {
            // read conten xml
            URLConnection con       = url.openConnection();
            BufferedReader in       = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line             = in.readLine();
            StringBuilder builder   = new StringBuilder();
            do {
                builder.append(line).append("\n");
            } while ( (line = in.readLine()) != null);
            // write file             
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            out.write(builder.toString());
            out.flush();
        } catch (MalformedURLException mue) {
        } catch (IOException ioe) {
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
            }
        }
        return null;
    }
        
    public void ReadXML(String xml) throws SAXException {
        try {
            
            /**
             * file list link
             */
            File file =new File("data/conf/list_uri.conf");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWritter      = new FileWriter(file,true);
            
            /**
             * read file xml
             */
            try (BufferedWriter bufferWritter = new BufferedWriter(fileWritter)) {
                File fXmlFile = new File(xml);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = null;
                try {
                    dBuilder = dbFactory.newDocumentBuilder();
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(GetReadXML.class.getName()).log(Level.SEVERE, null, ex);
                }
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();
                NodeList itemList = doc.getElementsByTagName("item");
                for (int temp = 0; temp < itemList.getLength(); temp++) {
                    Node link = itemList.item(temp);
                    if (link.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) link;
                        bufferWritter.write(eElement.getElementsByTagName("link").item(0).getTextContent() + "\n");
                    }
                }
            }                    
        } catch (IOException ex) {
            Logger.getLogger(GetReadXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 }
    
    