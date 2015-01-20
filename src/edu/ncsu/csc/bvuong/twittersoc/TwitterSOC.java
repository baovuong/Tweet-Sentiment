package edu.ncsu.csc.bvuong.twittersoc;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class TwitterSOC {
	
	
	public static List<Status> getTweetsWithHashtag(String hashtag) {
		List<Status> statuses = null;
		Twitter twitter = new TwitterFactory().getInstance();
		try {
			QueryResult result = twitter.search(new Query("#"+hashtag));
			statuses = result.getTweets();
		} catch (TwitterException te) {
			te.printStackTrace();
		}
		return statuses;
		
	}
	
	
	
	

}
