
package crawlertwitter;

import java.util.ArrayList;
import java.util.List;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author ahmadluky
 */
public class TweetDataReply {
    
    private final Twitter twitter; 
    
    public TweetDataReply(){
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
    
    
    public void searchReply(String str_query)
    {
    	
    	Query query = new Query(str_query);
        
        List<Status> tweets = new ArrayList<Status>();
        
//        Twitter twitter = new TwitterFactory().getInstance();
        try {
            QueryResult result;
            do {
                result = twitter.search(query);
                
                for (Status tweet : result.getTweets()) {
                    // Replace this logic to check if it's a response to a known tweet
                	System.out.println(tweet.getId()+ " >> "+tweet.getInReplyToStatusId());
                    if (tweet.getInReplyToStatusId() > 0) {
                        tweets.add(tweet);
                    }
                }               
            } while ((query = result.nextQuery()) != null);

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        
        System.out.println(tweets);
    	
    	/*
    	try {
        	Query query         = new Query(str_query);
            int wantedTweets    = 12;
            int remainingTweets = wantedTweets;
            QueryResult result;
            while(remainingTweets > 0)
            {	
            	result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                tweets.stream().forEach((Status tweet) -> {
                	
                	Status replyStatus = null;
					try {
						replyStatus = twitter.showStatus(tweet.getInReplyToStatusId());
					} catch (Exception e) {
						e.printStackTrace();
					}
                	
                	System.out.print(tweet.getId()+" >> ");
                	System.out.println(tweet.getRetweetCount());
                   	
                });
            }
        } catch (TwitterException te) {
            if (te.getStatusCode() == 429) {}
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    	*/
    }
  }
