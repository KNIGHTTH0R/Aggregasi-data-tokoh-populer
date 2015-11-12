
package crawlertwitter;

import datastore.Csv;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author ahmadluky
 */
public class TwitterData {
    
    private final Twitter twitter; 
    
    public TwitterData(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setJSONStoreEnabled(true)
          .setOAuthConsumerKey(utils.OAuthUtils.TWITTER_CONSUMER_KEY)
          .setOAuthConsumerSecret(utils.OAuthUtils.TWITTER_CONSUMER_SECRET)
          .setOAuthAccessToken(utils.OAuthUtils.OAUTH_TOKEN)
          .setOAuthAccessTokenSecret(utils.OAuthUtils.OAUTH_SECRET);
        TwitterFactory tf   = new TwitterFactory(cb.build());
        twitter             = tf.getInstance();
    }
    
    
    public void datasearch(String str_query)
    {
        
        try {
            Csv c               = new Csv();
            Query query         = new Query(str_query);
            Date date           = new Date();
                        
            int wantedTweets    = 112;
            int remainingTweets = wantedTweets;
            
            QueryResult result;
            
            while(remainingTweets > 0)
            {
                if(remainingTweets > 100)
                    query.count(100);
                else
                    query.count(remainingTweets);
                
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                tweets.stream().forEach((Status tweet) -> {
                    System.out.println( tweet.getId() + ">"
                            + "@" + tweet.getUser().getScreenName() + " > "
                            + tweet.getText() + " > "
                            + tweet.getCreatedAt() + ""
                            + tweet.getGeoLocation());
                    try {
                        /**
                        c.writeCsvFile(str_query    +   "opinion_" +   date,
                                String.valueOf(tweet.getId()),
                                tweet.getUser().getScreenName(),
                                tweet.getText().replaceAll("\n", ""),
                                formatter.format(tweet.getCreatedAt()), 
                                ""+tweet.getGeoLocation()+"");
                        **/
                        c.writeJsonCsvFile(str_query    +   "opinion_" +   date, tweet);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(TwitterData.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            
        } catch (TwitterException te) {
            if (te.getStatusCode() == 429) {}
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
    
    public void datatimeline(String user) throws IOException{
        
        try {
            Csv c               = new Csv();
            Format formatter    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            List<Status> statuses;
            Paging paging   = new Paging(2, 100);
            statuses        = twitter.getUserTimeline(user,paging);
            System.out.println("Showing @" + user + "'s user timeline. Count :"+ statuses.size());
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                
                // create data file csv
                c.writeCsvFile(user,
                            String.valueOf(status.getId()),
                            status.getUser().getScreenName(),
                            status.getText(),
                            formatter.format(status.getCreatedAt()),
                            ""+status.getGeoLocation()+"");
                
            }
        } catch (TwitterException te) {
            te.printStackTrace(); 
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
    
    public void InfoUser (String at_user) throws IOException{
        
        try {
            //Format formatter    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            User user           = twitter.showUser(at_user); 
            if (user.getStatus() != null) {
                System.out.println("@" + user.getScreenName() + " - " + user.getDescription() +
                                   "\n Location : " + user.getLocation() +
                                   "\n Lang :" + user.getLang()  +
                                   "\n Flolower :" + user.getFollowersCount() +
                                   "\n Following :" + user.getFriendsCount() +
                                   "\n Image :"   + user.getBiggerProfileImageURLHttps()
                                    );
            } else {
                // protected account
                System.out.println("@" + user.getScreenName());
            }
        } catch (TwitterException te) {
            te.printStackTrace(); 
            System.out.println("Failed to get Info User: " + te.getMessage());
            System.exit(-1);
        }
    }

}
