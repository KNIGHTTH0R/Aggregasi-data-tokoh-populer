/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawlertwitter;

import datastore.Csv;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author ahmadluky
 */
public class TwitterDataStream {
    
    private final TwitterStream twitterStream; 
    String label = "";
    String[] lang  = {"id"};
    FileOutputStream fos;
    
    public TwitterDataStream(){
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setJSONStoreEnabled(true)
          .setOAuthConsumerKey(utils.OAuthUtils.TWITTER_CONSUMER_KEY)
          .setOAuthConsumerSecret(utils.OAuthUtils.TWITTER_CONSUMER_SECRET)
          .setOAuthAccessToken(utils.OAuthUtils.OAUTH_TOKEN)
          .setOAuthAccessTokenSecret(utils.OAuthUtils.OAUTH_SECRET);
        twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
    }
    
    public void startTwitter(String[] keywords) {
        
        for (int i = 0; i < keywords.length; i++) {
            this.label += "_"+keywords[i];
        }
        
        twitterStream.addListener(listener);
        System.out.println("Starting down Twitter stream...");
        FilterQuery query = new FilterQuery();
//        query.track(keywords);
        query.language(this.lang);
        // by filter
        twitterStream.filter(query);
        
//        twitterStream.sample();
 
    }
    
    public void stopTwitter() {
 
        System.out.println("Shutting down Twitter stream...");
        twitterStream.shutdown();
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    StatusListener listener = new StatusListener() {
 
        Csv c               = new Csv();
        Format formatter    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date           = new Date();
        @Override
        public void onStatus(Status status) {
            System.out.println(label+" __timestamp : "+ String.valueOf(status.getCreatedAt().getTime()));
            System.out.println(status.getUser().getScreenName() + ": " + status.getText() + " > " + formatter.format(status.getCreatedAt()));
            
            try {
                
                /*
                c.writeCsvFile("stream_"+label+"_"+date,
                String.valueOf(status.getId()),
                status.getUser().getScreenName(),
                status.getText(),
                formatter.format(status.getCreatedAt()),
                ""  + status.getGeoLocation()+"");
                */
                
                c.writeJsonCsvFile("stream_"+label+"_"+date, status);
            
            } catch (IOException ex) {
                Logger.getLogger(TwitterDataStream.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        @Override
        public void onScrubGeo(long userId, long upToStatusId) {}
        @Override
        public void onException(Exception ex) {}
        @Override
        public void onStallWarning(StallWarning warning) {}
    };
}
