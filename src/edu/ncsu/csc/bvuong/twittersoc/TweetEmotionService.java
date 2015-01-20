package edu.ncsu.csc.bvuong.twittersoc;


import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import edu.ncsu.csc.bvuong.twittersoc.ui.TwitterSOCFrame;



public class TweetEmotionService {
	
	private TwitterStream stream;
	private boolean started;
	private StatusListener listener;
	private String hashTag;
	private int limit;
	private int tweets;
	
	private TwitterSOCFrame frame;

	public TweetEmotionService() {
		stream = new TwitterStreamFactory().getInstance();
		hashTag = "";
		setTweets(0);
		
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
				TweetSentiment sentiment = new TweetSentiment(arg0);
				
				System.out.println("tweet input: "+sentiment.getTweet());
				System.out.println(sentiment);
				
				if (tweets > limit) {
					frame.setStop();
					tweets = 0;
				}
				setTweets(getTweets() + 1);
					//frame.publishTweet(status);
				frame.publishSentiment(sentiment);
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
		//frame.resetTree();
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
