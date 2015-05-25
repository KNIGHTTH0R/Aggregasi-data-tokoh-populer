/**
 * Rule System
 * ===========
 * [1] Tedapat 2 file konfigurasi uri Rss media online (rss.conf) dan uri (list_uri.conf) 
 * online (web resmi partai) yang kemungkinan ada nama tokoh yang akan digunakan
 * [2] Sistem membaca rss.cof yang kemudian xml file disimpan di dir /xml (done)
 * [3] Sistem membaca xml file dan mengambil uri dari setiap judul berita yang selanjutnya di <append> ke file list_uri.conf
 * [4] Sistem membaca file list_uri dan mendapatkan html code dari server (internet) disimpan di /html
 * [5] lakukan proses DOM dan REGEX untuk mendapatkan nama pada berita.
 */

/**
 * @author ahmadluky
 */



import crawlertokoh.GetReadHtml;
import crawlertokoh.GetReadXML;
import crawlertokoh.SearchObjectTokoh;
import crawlertwitter.TwitterData;
import crawlertwitter.TwitterDataStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

public class CrowlerTokoh {
    
    /**
     * Read rss.conf
     * @throws java.io.IOException
     */

    public static void read_rsscofg() throws IOException{
        try {
            GetReadXML xmlrss = new GetReadXML();
            BufferedReader buf;
            String sCurrentLine;
            String sParator       = "\t";
            buf                   = new BufferedReader(new FileReader("data/conf/rss.conf"));
            
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] data = sCurrentLine.split(sParator);
                System.out.println(data[1]);
                String Getxml = xmlrss.Getxml(new URL(data[1]), data[0]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrowlerTokoh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Read File XML in data/xml 
     * ambil link berita--> save list_uri
     * @throws org.xml.sax.SAXException
     */

    public static void readxml_file() throws SAXException{
        File f = null;
        String[] paths;

        f = new File("data/xml");
        paths = f.list();
        for(String path:paths)
        {
            GetReadXML xml = new GetReadXML();
            xml.ReadXML("data/xml/"+path);
        }
    }
    
    /**
     * Get HTML code dari Uri
     * @throws java.io.FileNotFoundException
     */
    public static void gethtml() throws FileNotFoundException{
        GetReadHtml r = new GetReadHtml();
        
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;

        BufferedReader b = new BufferedReader(new FileReader("data/conf/list_uri.conf"));
        String s;
        try {
            while ((s  = b.readLine()) != null) {
                File file = new File("data/html/" + dateFormat.format(date) + ".html") ;
                r.GetHtml_Stream(s, file);
                System.out.println(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(CrowlerTokoh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Search Tokoh berdasarkan regex
     */
    private static void search_key() { 
        String s;
        BufferedReader b    = null;
        File f              = new File("data/html");
        String[] paths      = f.list();
        
        for(String path:paths)
        {
            try {
                b = new BufferedReader(new FileReader("data/html/" + path));
                
                SearchObjectTokoh search = new SearchObjectTokoh();
                
                try {
                    while ((s  = b.readLine()) != null) {
                        search.RegexKetua(s, "data/result/nama_tokoh.txt");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CrowlerTokoh.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(CrowlerTokoh.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public static void main(String[] args) throws IOException, SAXException, InterruptedException {
        /*read_rsscofg();
        readxml_file();
        gethtml();
        search_key();
        */
        
        //search by keyword
        try {
            BufferedReader buf;
            String sCurrentLine;
            String sParator       = "\t";
            buf                   = new BufferedReader(new FileReader("data/result/nama_tokoh.txt"));
            
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] data = sCurrentLine.split(sParator);
                
                TwitterData cr = new TwitterData();
                System.out.println(data[0]+" >> @"+data[1]);
                cr.datastream(data[1]);
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrowlerTokoh.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //streaming by keyword
        TwitterDataStream twitter = new TwitterDataStream();
        twitter.startTwitter();
        Thread.sleep(20000);
        twitter.stopTwitter();
    }

}