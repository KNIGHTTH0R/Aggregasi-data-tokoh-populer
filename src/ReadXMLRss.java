
import crawlertokoh.GetReadXML;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import datastore.Mysql;

/**
 * Read File XML in data/xml 
 * Get link uri page content news
 * @author ahmadluky
 */
public class ReadXMLRss {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException, SQLException, ClassNotFoundException, ParserConfigurationException {
        /**File f  = null;
        String[] paths;
        f  = new File("data/xml");
        paths   = f.list();
        for(String path:paths){
            GetReadXML xml = new GetReadXML();
            xml.ReadXML("data/xml/"+path); //path xml file result code ReadRSSConfig.java
        }**/
        // select xml_data
    	String sql = "SELECT * FROM xml_data";
    	Mysql connect = new Mysql();
    	ResultSet result = connect.sql(sql);
    	while (result.next()){
            String xml_data = result.getString("data");
            GetReadXML xml = new GetReadXML();
            xml.ReadXML(xml_data, connect);
    	}
        
    }
}
