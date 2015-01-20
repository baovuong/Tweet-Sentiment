package edu.ncsu.csc.bvuong.twittersoc;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.OWL2;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import twitter4j.Status;

public class TweetSentiment {
	public static final String EMOTION_RDF = "emotion_rdf.rdf";
	public static final String EMOTION_OWL = "emotion_owl.owl";
	
	private String overallSentiment;
	private Map<String,String> wordSentiment;
	private String tweet;
	
	public TweetSentiment(Status status) {
		this(status.getText());
	}
	
	public TweetSentiment(String status) {
		tweet = status;
		
		wordSentiment = new HashMap<String,String>();
		
		String[] words = status.split(" ");
		
		Model schema = FileManager.get().loadModel(EMOTION_OWL);
		Model data = FileManager.get().loadModel(EMOTION_RDF);
		Reasoner reasoner;// = ReasonerRegistry.getOWLReasoner();

		OntModel baseOntModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,schema);
		reasoner = ReasonerRegistry.getOWLReasoner().bindSchema(baseOntModel);
		InfModel inf = ModelFactory.createInfModel(reasoner, data);
		
		Map<String,Integer> emotionCount = new HashMap<String,Integer>();
		
		for (String word : words) {
			word = word.replaceAll("[^a-zA-Z ]", "").toLowerCase();
			if (containsWord(inf,word)) {
				String emotion = getEmotion(inf,word);
				wordSentiment.put(word,emotion);
				
				if (!emotionCount.keySet().contains(emotion)) {
					emotionCount.put(emotion,0);
				}
				emotionCount.put(emotion,emotionCount.get(emotion)+1);
			}
		}
		
		// determine overallSentiment
		overallSentiment = "";
		int count = 0;
		for (String emotion : emotionCount.keySet()) {
			if (count < emotionCount.get(emotion)) {
				overallSentiment = emotion;
				count = emotionCount.get(emotion);
			}
		}
		
	}
	
	private boolean containsWord(InfModel model, String word) {
		String uri ="http://www.w3.org/2006/03/wn/wn20/schema/"+word;
		Statement statement = model.getResource(uri).getProperty(RDF.type);
		
		return (statement != null);
	}
	
	private String getEmotion(InfModel model, String word) {
		if (!containsWord(model, word))
			return "";
		String uri = "http://www.w3.org/2006/03/wn/wn20/schema/"+word;
		Resource emotionWord = model.getResource(uri);
		Statement statement = emotionWord.getProperty(RDF.type);
		String emotion = statement.getResource().getURI();
		Resource emotionType = model.getResource(emotion);
		StmtIterator iterator = emotionType.listProperties(RDFS.subClassOf);
		while (iterator.hasNext()) {
			Statement stmt = iterator.nextStatement();
			if (stmt.getResource().toString().contains("http://www.w3.org/2006/03/wn/wn20/schema/") && 
					!stmt.getResource().toString().equals(emotionType.toString())) {
				emotion = stmt.getResource().toString();
				break;
			}
		}
		return emotion.substring("http://www.w3.org/2006/03/wn/wn20/schema/".length());
	}
	
	public String getOverallSentiment() {
		return overallSentiment;
	}
	
	public Map<String,String> getSentiments() {
		return wordSentiment;
	}
	
	public String toString() {
		String result = "";
		result += "Overall sentiment - " + overallSentiment + "\n";
		for (String key : wordSentiment.keySet()) {
			result += key + " - " + wordSentiment.get(key) + "\n";
		}
		return result;
	}

	public String getTweet() {
		// TODO Auto-generated method stub
		return tweet;
	}
}
