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
     * Searching Tokoh by using <b>REGEX</b>
     */
    private static void search_key(String path_content_NewSPage, String file) { 
        String s;
        BufferedReader b    = null;
        File f              = new File(path_content_NewSPage);
        String[] paths      = f.list();
        for(String path:paths)
        {
            try {
                b = new BufferedReader(new FileReader(path_content_NewSPage + path));
                SearchObjectTokoh search = new SearchObjectTokoh();
                try {
                    while ((s  = b.readLine()) != null) {
                        search.RegexKetua(s, file);
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
        
        String NAMA_TOKOH       = "data/result/nama_tokoh.txt";
        String RSS_CONFIG       = "data/conf/rss.conf";
        String LIST_URI         = "data/conf/list_uri.conf";
        String path_NewSPage    = "data/html/";
        
        
        
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
            buf                   = new BufferedReader(new FileReader(RSS_CONFIG));
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] data     = sCurrentLine.split(sParator);
                System.out.println(data[1]);
                String Getxml     = xmlrss.Getxml(new URL(data[1]), data[0]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CrowlerTokoh.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        
        /**
         * Get HTML code dari Uri
         * @throws java.io.FileNotFoundException
         */
        
        GetReadHtml r               = new GetReadHtml();
        Date date                   = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        BufferedReader b            = new BufferedReader(new FileReader(LIST_URI));
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
        
        /**
         * Main function for search or matching string.
         * @resutl  get nama tokoh on news page
         */
        
        search_key(path_NewSPage, NAMA_TOKOH);
        
        
        /**
         * Main function for get <b>SEARCH</b> data twitter by keyword defined in directory result
         * file name keyword : nama_tokoh.txt
         */
        
        try {
            BufferedReader buf;
            String sCurrentLine;
            String sParator       = "\t";
            buf                   = new BufferedReader(new FileReader(NAMA_TOKOH));
            
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] data       = sCurrentLine.split(sParator);
                TwitterData cr      = new TwitterData();
                System.out.println(data[0]+" >> @"+data[1]);
                cr.datastream(data[1]);
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwitterDataStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /**
         * Main function for get <b>STREAMING</b> data twitter by keyword defined in directory result
         * file name keyword : nama_tokoh.txt
         */
        
        try {
            BufferedReader buf;
            String sCurrentLine;
            String sParator             = "\t";
            buf                         = new BufferedReader(new FileReader(NAMA_TOKOH));
            TwitterDataStream twitter   = new TwitterDataStream();
            
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] keyword = sCurrentLine.split(sParator);
                twitter.startTwitter(keyword);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwitterDataStream.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}