package edu.ncsu.csc.bvuong.twittersoc;

import java.util.LinkedList;
import java.util.Queue;

import edu.ncsu.csc.bvuong.twittersoc.ui.TwitterSOCFrame;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetReverseService {
	private TwitterStream stream;
	private boolean started;
	private StatusListener listener;
	private String hashTag;
	private Twitter twitter;
	private Queue<Status> statusQueue;
	private int limit;
	private int tweets;
	
	private TwitterSOCFrame frame;
	
	public TweetReverseService() {
		stream = new TwitterStreamFactory().getInstance();
		hashTag = "";
		twitter = TwitterFactory.getSingleton();
		setTweets(0);
		statusQueue = new LinkedList<Status>();
		
		listener = new StatusListener() {

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				arg0.printStackTrace();
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatus(Status arg0) {
				// TODO Auto-generated method stub
				frame.printMessage("looking at: "+arg0.getText());
				String reversedTweet = new StringBuilder(arg0.getText()).reverse().toString();
				Status status = null;
				if (tweets > limit) {
					frame.setStop();
					tweets = 0;
				}
				try {
					
					status = twitter.updateStatus(reversedTweet);
					
					statusQueue.add(status);
					setTweets(getTweets() + 1);
					tweets++;
					frame.publishTweet(status);
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.displayStatus("running... " + tweets + "/" + limit);
				
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		stream.addListener(listener);
		
	}
	
	public void start() {
		frame.displayStatus("running... " + tweets + "/" + limit);
		started = true;
		String[] search = {hashTag};
		FilterQuery query = new FilterQuery();
		query.track(search);
		stream.filter(query);
	}
	
	public void stop() {
		frame.displayStatus("done.");
		started = false;
		stream.cleanUp();
	}
	
	public boolean hasStarted() {
		return started;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		frame.printMessage("setting hash tag: "+hashTag);
		this.hashTag = hashTag;
	}

	public int getTweets() {
		return tweets;
	}

	public void setTweets(int tweets) {
		this.tweets = tweets;
	}

	public TwitterSOCFrame getFrame() {
		return frame;
	}

	public void setFrame(TwitterSOCFrame frame) {
		this.frame = frame;
	}

	public void setLimit(int value) {
		// TODO Auto-generated method stub
		limit = value;
		
	}
}
