import java.util.ArrayList;
import java.util.List;

import parser.RedditParser;
import parser.RssParser;
import subscription.SingleSubscription;
import subscription.Subscription;
import feed.Article;
import feed.Feed;
import httpRequest.httpRequester;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;
import namedEntity.NamedEntity;

import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.apache.spark.api.java.JavaRDD;



public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne] or [-word]");
	}
	
	/* Parse Articles from a Single Subscription */
	private static List<Article> parseArticles (SingleSubscription singSub, String inputFile) {
		List<Article> artList = new ArrayList<Article> ();
		if (singSub.getUrlType().equals("reddit")) {			// Check URL type
			RedditParser parser = new RedditParser(inputFile);
			artList = parser.parse();							// Call reddit parser
		} else {
			RssParser parser = new RssParser(inputFile);		// Call rss parser
			artList = parser.parse();
		}
		return artList;
	}

	/* Given an Article List, print Feed */
	private static void printFeed(List<Article> artList, String url) {
		if (!artList.isEmpty()) {
			Feed f = new Feed(url);
			f.setArticleList(artList);	// Set list to Feed
			f.prettyPrint();			// Print feed
		} else {
			System.out.println("There's no articles in this subscription");
		}
	}

	/* Given an Article List and an heuristic, return a namedEntity dictionary */
	public static void callHeuristic(List<Article> artList, String mode, Map<String, NamedEntity> namedEntityStored) {
		for (Article art : artList) {
			String text = art.getText();
			String link = art.getLink();
			if (mode.equals("Quick")) {
				QuickHeuristic qh = new QuickHeuristic();
				qh.runQuickHeuristic(text,namedEntityStored,link);
			} 
			else if (mode.equals("Random")) {
				RandomHeuristic rh = new RandomHeuristic();
				rh.runRdmHeuristic(text,namedEntityStored,link);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("************* FeedReader version 1.0 *************");
		if (args.length != 0 && args.length != 2) {
			printHelp();
			return;
		}
		else {
			/* Parse JSON File subscription.json from dir ´config´ */
			Subscription sub = new Subscription("../config/subscriptions.json");
			if (sub.getLength() > 0) {
				Map<String, NamedEntity> namedEntityStored = new HashMap<String, NamedEntity>();
				
				/* Define Spark configuration */
				Logger.getLogger("org").setLevel(Level.WARN);
				Logger.getLogger("akka").setLevel(Level.WARN);

				SparkConf conf = new SparkConf().setAppName("Map-Reduce").setMaster("local[*]");
				JavaSparkContext sc = new JavaSparkContext(conf);
				
				//////////////////////////////////////////////////////////////////////
				/* Define parallelized collections ~ SingleSubscription */
				List<SingleSubscription> colection = sub.getSubscriptionsList();
				JavaRDD<SingleSubscription> rddSinglesub = sc.parallelize(colection);
				
				/* Map function ~ create article list*/
				JavaRDD<List<List<Article>>> articleList = rddSinglesub.map(new Function< SingleSubscription, List<List<Article>> >() {
					public List<List<Article>> call(SingleSubscription singSub) { 
						List<List<Article>> artList = new ArrayList<List<Article>>();
						httpRequester hr = new httpRequester();
						/* Process each parameter from a subscription */
						for (int param = 0; param < singSub.geturlParamsSize(); ++param) {
							String inputFile = hr.getFeed(singSub.getFeedToRequest(param));	// Get feed content
							if (!inputFile.isEmpty()) {
								List<Article> tempArticles = parseArticles(singSub, inputFile);
								artList.add(tempArticles);	
								hr.deleteFile(inputFile);		// Delete used file
							}
							else 
								System.out.println("There was an error parsing the request");
						}	
						return artList;
					}
				});
				 
				/* Reduce function ~ put all articleList into a list*/
				List<List<Article>> finalArt = articleList.reduce(new Function2< List<List<Article>>,List<List<Article>>, List<List<Article>> >() {
					public List<List<Article>> call(List<List<Article>> a, List<List<Article>> b) {
						a.addAll(b);
						return a;
					}
				});
				//////////////////////////////////////////////////////////////////////

				//////////////////////////////////////////////////////////////////////
				/* Define parallelized collections ~ finalArt */
				JavaRDD<List<Article>> rddArt = sc.parallelize(finalArt);

				/* Map function ~ processes each List<Article> into HashMap*/
				JavaRDD<Map<String, NamedEntity>> parcialStored = rddArt.map(new Function<List<Article>, Map<String, NamedEntity>>() {
					public Map<String, NamedEntity> call(List<Article> artList) { 
						/* Temporary hashmap */
						Map<String, NamedEntity> temp = new HashMap<String, NamedEntity>();
						if (args.length == 2 && args[0].equals("-ne")) 
							callHeuristic(artList, args[1], temp);
						else if (args.length == 2 && args[0].equals("-word"))
							callHeuristic(artList, "Quick", temp);
						
							/* Return the partial hashmap*/
						return temp;
					}
				});
				 
				/* Reduce function ~ Merge the partial HashMap */
				Map<String, NamedEntity> finalMap = parcialStored.reduce(new Function2<Map<String, NamedEntity> ,
				 Map<String, NamedEntity>, Map<String, NamedEntity> >() {
					public Map<String, NamedEntity> call(Map<String, NamedEntity> a, Map<String, NamedEntity> b) {
						/* Merge the partial hashmaps together */
						for (Map.Entry<String, NamedEntity> entry : b.entrySet()){
							String key = entry.getKey();
							NamedEntity value = entry.getValue();
							if (a.containsKey(key)) {
								NamedEntity merge = a.get(key);
								merge.setFrequency(value.getFrequency() + merge.getFrequency());
								
								for (int i = 0; i < value.getInvertIndexSize(); i++){
									String l = value.getLinkNameIndex(i);
									Integer f = value.getLinkFrecIndex(i);
									merge.addOcurrence(l, true, f);
								}
								a.put(key, merge);
							}
							else 
								a.put(key,value);
						}
						return a; 
					}
				});
				namedEntityStored.putAll(finalMap);
				/* Stop spark session */
				sc.stop();
				/* Stop the workers and release the allocated resources */
				sc.close();
				//////////////////////////////////////////////////////////////////////


				if (args.length == 2 && args[0].equals("-word")) {
					if (namedEntityStored.containsKey(args[1])){
						NamedEntity namedEntity =  namedEntityStored.get(args[1]);
						namedEntity.prePrint();
						namedEntity.prettyPrint();
					}
					else{
						System.out.println("No se encontro la entidad nombrada `" + args[1] + "` en ningun archivo");
					}	
				}
				
				else if (args.length > 0) {
					for (NamedEntity value : namedEntityStored.values()) {
						value.prePrint();
						value.prettyPrint();
					}
				}
			}
			else {
				System.out.println("Subscription's file is empty. Please add some.");
			}
		}
	}
}
