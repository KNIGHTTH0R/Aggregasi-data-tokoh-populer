
import crawlertokoh.GetReadHtml;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.xml.sax.SAXException;
import datastore.Mysql;

/**
 * Get HTML code dari Uri
 * @throws java.io.FileNotFoundException
 * @author ahmadluky
 */
public class GetHtml {
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException, ClassNotFoundException, SQLException {
        /**GetReadHtml r               = new GetReadHtml();
        BufferedReader b            = new BufferedReader(new FileReader(utils.TokohUntils.LIST_URI));
        String s; //list link url artikel berita
        Mysql  conect = new Mysql();
        try {
            while ((s  = b.readLine()) != null) {
                r.GetHtml_Stream(s, conect);
            }
        } catch (IOException ex) {
            Logger.getLogger(GetHtml.class.getName()).log(Level.SEVERE, null, ex);
        }**/
        
    	String sql = "SELECT * FROM xml_linkhtml";
    	Mysql connect = new Mysql();
    	GetReadHtml r  = new GetReadHtml();
    	ResultSet result = connect.sql(sql);
    	while (result.next()){
            String uri = result.getString("uri_link");
            r.GetHtml_Stream(uri, connect);
    	}
    }
}
