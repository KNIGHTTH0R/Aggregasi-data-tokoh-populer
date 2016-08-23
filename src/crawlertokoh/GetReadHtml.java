package crawlertokoh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;

import datastore.Mysql;
/**
 * 
 * @author @ahmadluky
 *
 */
public class GetReadHtml {
    
    @SuppressWarnings("unused")
	public ArrayList<String> read_uri(String listUri) {
        BufferedReader br = null;
        try {
	            String sCurrentLine;
	            String[] Uri        = null;
	            ArrayList<String> Uri_collection = new ArrayList<>();
	            br = new BufferedReader(new FileReader(listUri));
	            while ((sCurrentLine = br.readLine()) != null) {
	                Uri_collection.add(sCurrentLine);
	            }
	            return Uri_collection;
        } catch (IOException e) {
            	return null;
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                return null;
            }
        }
    }
	
    @SuppressWarnings("resource")
	public String GetHtml_Stream(String s, File file){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        try {
            FileWriter fw           = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw       = new BufferedWriter(fw);
            url                     = new URL(s);
            is                      = url.openStream();
            br                      = new BufferedReader(new InputStreamReader(is));
            if (!file.exists()) {
                file.createNewFile();
            }
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                bw.write(line);
            }
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
    
    public String GetHtml_Stream(String s, Mysql conect) throws ClassNotFoundException, SQLException{
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        try {
            url = new URL(s);
            is  = url.openStream();
            br  = new BufferedReader(new InputStreamReader(is));
            String buffer = "";
            while ((line = br.readLine()) != null) {
                buffer +=line;
            }
            String rString = Jsoup.parse(buffer).text();
            System.out.println(rString);
            String sql = "Insert Into html_data (`data`) values ('"+rString.replaceAll("\'","")+"')";
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

}
