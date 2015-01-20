package edu.ncsu.csc.bvuong.twittersoc.ui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import edu.ncsu.csc.bvuong.twittersoc.TweetEmotionService;
import edu.ncsu.csc.bvuong.twittersoc.TweetSentiment;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JList;

import twitter4j.Status;

import javax.swing.JTree;

public class TwitterSOCFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1566168966580602323L;
	private JPanel contentPane;
	private JTextField hashtagTextField;
	private JButton btnStop;
	private JButton btnStart;
	private JTextArea textArea;
	private JLabel numTweetsLabel;
	
	private TweetEmotionService service;
	private JSpinner tweetLimitSpinner;
	private JLabel statusLabel;
	private JTree tree;
	private DefaultMutableTreeNode root;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TwitterSOCFrame frame = new TwitterSOCFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TwitterSOCFrame() {
		setTitle("Tweet Sentiments");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Hash Tag");
		
		hashtagTextField = new JTextField();
		hashtagTextField.setColumns(10);
		
		btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setStart();
			}
		});
		
		btnStop = new JButton("Stop");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setStop();
			}
		});
		btnStop.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		numTweetsLabel = new JLabel("");
		
		JLabel lblTweetLimit = new JLabel("Tweet Limit");
		
		tweetLimitSpinner = new JSpinner();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblPublishedTweets = new JLabel("Analyzed Tweets");
		
		JLabel lblOutput = new JLabel("Output");
		
		JPanel statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel("ready");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTweetLimit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tweetLimitSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(304, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(hashtagTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
									.addComponent(btnStop)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnStart)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(6))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(statusPanel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOutput)
								.addComponent(numTweetsLabel))
							.addGap(160)
							.addComponent(lblPublishedTweets)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPublishedTweets)
						.addComponent(lblOutput))
					.addGap(15)
					.addComponent(numTweetsLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTweetLimit)
								.addComponent(tweetLimitSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(hashtagTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnStop)
							.addComponent(btnStart)))
					.addGap(18)
					.addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		tree = new JTree();
		root = new DefaultMutableTreeNode("tweets") ;
		tree.setModel(new DefaultTreeModel(root));
		
		scrollPane_1.setViewportView(tree);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
		service = new TweetEmotionService();
		service.setFrame(this);
		
		service.getTweets();
		
	}
	
	public void printMessage(String message) {
		textArea.append(message+"\n");
	}
	
	public void setStart() {
		btnStop.setEnabled(true);
		btnStart.setEnabled(false);
		
		service.setLimit((int)(tweetLimitSpinner.getValue()));
		
		service.setHashTag(hashtagTextField.getText());
		if (service.getHashTag().charAt(0) != '#')
			service.setHashTag("#"+service.getHashTag());
		
		service.start();
		
	}
	
	public void setStop() {
		btnStop.setEnabled(false);
		btnStart.setEnabled(true);
		
		service.stop();
		
	}
	
	public void publishTweet(Status status) {
	}
	
	
	public void displayStatus(String text) {
		statusLabel.setText(text);
	}

	public void publishSentiment(TweetSentiment sentiment) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(sentiment.getTweet());
		Map<String,String> sentiments = sentiment.getSentiments();
		
		newNode.add(new DefaultMutableTreeNode("Overall sentiment - "+sentiment.getOverallSentiment()));
		
		for (String word : sentiments.keySet()) {
			newNode.add(new DefaultMutableTreeNode(word + " - " + sentiments.get(word)));
		}
		
		root.add(newNode);
		
	}

	public void resetTree() {
		// TODO Auto-generated method stub
		tree = new JTree();
		root = new DefaultMutableTreeNode("tweets") ;
		tree.setModel(new DefaultTreeModel(root));
	}
}
