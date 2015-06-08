/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package User;

import crawlertwitter.TwitterData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmadluky
 */
public class Userinfo {
    
    public static void main(String[] args) throws IOException{
      /**
         * Main function for get <b>TIMELINE</b> in twitter tokoh
         * file name keyword : nama_tokoh.txt
         */
        String NAMA_TOKOH       = "data/result/nama_tokoh.txt";
        
        try {
            BufferedReader buf;
            String sCurrentLine;
            String sParator             = "\t";
            buf                         = new BufferedReader(new FileReader(NAMA_TOKOH));
            TwitterData twitterUserinfo = new TwitterData();
            
            while ((sCurrentLine  = buf.readLine()) != null) {
                String[] keyword = sCurrentLine.split(sParator);
                twitterUserinfo.InfoUser(keyword[1]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwitterData.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
