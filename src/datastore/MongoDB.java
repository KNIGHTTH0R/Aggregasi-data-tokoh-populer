/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datastore;

/**
 *
 * @author ahmadluky
 */
public class MongoDB {
    
    /**
     *  public static void main(String[] args) throws TwitterException {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setJSONStoreEnabled(true);

            Twitter twitter = new TwitterFactory(cb.build()).getInstance();
            Query query = new Query("lizardbill");
            QueryResult result = twitter.search(query);
            for (Tweet tweet : result.getTweets()) {
                System.out.println(tweet.getFromUser() + ":" + tweet.getText());
                String json = DataObjectFactory.getRawJSON(tweet);
                System.out.println(json);
            }
        }
     */
}
