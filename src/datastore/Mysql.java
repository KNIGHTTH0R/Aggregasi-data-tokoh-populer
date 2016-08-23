package datastore;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import com.mysql.jdbc.Connection;
/**
 * 
 * @author @ahmadluky
 *
 */
public class Mysql {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Mysql.class);
    public static Connection connect = null;
    public String user;
    public String password;
    public String database;

	public Mysql() throws ClassNotFoundException, SQLException{
        LOG.info("Load configuration connection .....");
        user = "root";
        password = "";
        database = "lexicon";
        LOG.info("Connecting.....");
        Class.forName("com.mysql.jdbc.Driver");
        connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database, user, password);
    }
    public ResultSet sql(String sqlString) throws SQLException{
        Statement statement = connect.createStatement();
        return  statement.executeQuery(sqlString);
    }
    public void sqlDelete(String table, String whereField, String whereVal) throws SQLException{
        PreparedStatement preparedStatement = connect.prepareStatement("delete from "+table+" where "+whereField+"= ? ; ");
        preparedStatement.setString(1, whereVal);
        preparedStatement.executeUpdate();
    }
    public void sqlInsert(String sqlInsert) throws SQLException{
        PreparedStatement preparedStatement = connect.prepareStatement(sqlInsert);
        preparedStatement.executeUpdate();
    }
    public void sqlUpdate(String sql) throws SQLException{
		Statement statement = connect.createStatement();
		System.out.println(sql);
		statement.execute(sql);
    }
    public void closex() throws SQLException{
        connect.close();
    }
    /**
     * Main function Test
     * @param args
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
     public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, SQLException, ClassNotFoundException{
            Mysql  conect = new Mysql();
            LOG.info(conect.toString());
            String sql = "Insert Into data_xml (`xml_data`, `id`, `name`) values ('data_xml_example', NULL, 'test')";
            conect.sqlInsert(sql);
     }
         
}
