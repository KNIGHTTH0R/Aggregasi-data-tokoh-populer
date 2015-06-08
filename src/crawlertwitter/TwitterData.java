/**
 * define('TWITTER_CONSUMER_KEY','DirV4f3gp3vIUVZDZH5W3k6mT');
 * define('TWITTER_CONSUMER_SECRET','GYPvZahLYb0JKw1etZlByBEtXZulpQnt8TcQfGtk0d7uFkjw6q');
 * define('OAUTH_TOKEN','58413351-7sWPSdF3cXvRdq7DwLWfnAgABX6bFJWNpC3LXWUyB');
 * define('OAUTH_SECRET','fUUwbYJmnDMAtHSLBuYycKzx9RvLKvcKK9Bke1WoqHljo');
 * define('TWEET_ERROR_INTERVAL',10);
 * define('TWEET_ERROR_ADDRESS','*****');
 */
package crawlertwitter;

import datastore.Csv;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author ahmadluky
 */
public class TwitterData {
    
    private final String TWITTER_CONSUMER_KEY = "DirV4f3gp3vIUVZDZH5W3k6mT";
    private final String TWITTER_CONSUMER_SECRET = "GYPvZahLYb0JKw1etZlByBEtXZulpQnt8TcQfGtk0d7uFkjw6q";
    private final String OAUTH_TOKEN = "58413351-7sWPSdF3cXvRdq7DwLWfnAgABX6bFJWNpC3LXWUyB";
    private final String OAUTH_SECRET = "fUUwbYJmnDMAtHSLBuYycKzx9RvLKvcKK9Bke1WoqHljo";
    
    
    public void datastream(String str_query)
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
          .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
          .setOAuthAccessToken(OAUTH_TOKEN)
          .setOAuthAccessTokenSecret(OAUTH_SECRET);
        TwitterFactory tf   = new TwitterFactory(cb.build());
        Twitter twitter     = tf.getInstance();

        try {
            Csv c               = new Csv();
            Query query         = new Query(str_query);
            Format formatter    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                tweets.stream().forEach((Status tweet) -> {
                    System.out.println( tweet.getId() + ">"
                            + "@" + tweet.getUser().getScreenName() + " > "
                            + tweet.getText() + " > "
                            + tweet.getCreatedAt() + ""
                            + tweet.getGeoLocation());
                    
                    // create data file csv
                    try {
                        c.writeCsvFile(str_query+"opinion",
                                String.valueOf(tweet.getId()),
                                tweet.getUser().getScreenName(),
                                tweet.getText(),
                                formatter.format(tweet.getCreatedAt()),
                                ""+tweet.getGeoLocation()+"");
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(TwitterData.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            if (te.getStatusCode() == 429) {
                
            }
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
    
    public void datatimeline(String user) throws IOException{
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
          .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
          .setOAuthAccessToken(OAUTH_TOKEN)
          .setOAuthAccessTokenSecret(OAUTH_SECRET);
        TwitterFactory tf   = new TwitterFactory(cb.build());
        Twitter twitter     = tf.getInstance();
        
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
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
          .setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET)
          .setOAuthAccessToken(OAUTH_TOKEN)
          .setOAuthAccessTokenSecret(OAUTH_SECRET);
        TwitterFactory tf   = new TwitterFactory(cb.build());
        Twitter twitter     = tf.getInstance();
        
        try {
            Format formatter    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}
