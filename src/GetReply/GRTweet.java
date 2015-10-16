package GetReply;

import crawlertwitter.TweetDataReply;
import java.io.IOException;

/**
 *
 * @author ahmadluky
 */
public class GRTweet {
    
	public static void main(String[] args) throws IOException{
      
        String ID_STR= "jokowi";
        TweetDataReply replay         = new TweetDataReply();
        replay.searchReply(ID_STR);
    }
}
