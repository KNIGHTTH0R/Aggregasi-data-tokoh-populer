
package crawlertokoh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
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

import datastore.Mysql;

import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author ahmadluky
 */
public class GetReadXML {
    
    @SuppressWarnings({ "unused", "resource" })
	public String Getxml(URL url, String name) {
        InputStream is = null;
        BufferedWriter out = null;
        BufferedReader br;
        File file = new File("data/xml/"+ name + ".xml");
        try {
            // read conten xml
            URLConnection con       = url.openConnection(); //read from url
            BufferedReader in       = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line             = in.readLine();
            StringBuilder builder   = new StringBuilder();
            do {
                builder.append(line).append("\n");
            } while ( (line = in.readLine()) != null);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));// write file
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
    @SuppressWarnings({"unused"})
	public String Getxml(URL url, String name, Mysql conect) throws SQLException {
        InputStream is = null;
        BufferedWriter out = null;
        BufferedReader br;
        try {
            // read conten xml
            URLConnection con       = url.openConnection(); //read from url
            BufferedReader in       = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line             = in.readLine();
            StringBuilder builder   = new StringBuilder();
            do {
                builder.append(line).append("\n");
            } while ( (line = in.readLine()) != null);
            String results = StringEscapeUtils.escapeJava(builder.toString());
            String sql = "Insert Into xml_data (`data`,`from`) values ('"+results.replaceAll("\'","")+"','"+name+"')";
            conect.sqlInsert(sql);
            
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
    public void ReadXML(String xml_data, Mysql connect) throws SAXException, ParserConfigurationException, SQLException, IOException {
        DocumentBuilder dBuilder = null;
        try {         
            final InputStream stream = new ByteArrayInputStream(xml_data.getBytes(StandardCharsets.UTF_8));
            dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList itemList = doc.getElementsByTagName("item");
                for (int temp = 0; temp < itemList.getLength(); temp++) {
                    Node link = itemList.item(temp);
                    if (link.getNodeType() == Node.ELEMENT_NODE) 
                    {
                        Element eElement = (Element) link;
                        String link_data = eElement.getElementsByTagName("link").item(0).getTextContent(); // mendapatkan Tag link disimpan di list_url
                        String sql = "Insert Into xml_linkhtml (`uri_link`) values ('"+link_data+"')";
                        try {
                            connect.sqlInsert(sql);
						} catch (Exception e) {
							System.out.println("Is exsist");
						}
                    }
                }       
        } catch (IOException ex) {
            Logger.getLogger(GetReadXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 }
 

































    