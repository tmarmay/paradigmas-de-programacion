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

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
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
	private static void callHeuristic(List<Article> artList, String mode, Map<String, NamedEntity> namedEntityStored) {
		for (Article art : artList) {
			String text = art.getText();
			if (mode.equals("Quick")) {
				QuickHeuristic qh = new QuickHeuristic();
				qh.runQuickHeuristic(text,namedEntityStored);
			} 
			else if (mode.equals("Random")) {
				RandomHeuristic rh = new RandomHeuristic();
				rh.runRdmHeuristic(text,namedEntityStored);
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
				for (int i = 0; i < sub.getLength(); ++i) {
					SingleSubscription singSub = sub.getSingleSubscription(i);
					httpRequester hr = new httpRequester();
					
					/* Process each parameter from a subscription */
					for (int param = 0; param < singSub.geturlParamsSize(); ++param) {
						String inputFile = hr.getFeed(singSub.getFeedToRequest(param));	// Get feed content
						if (!inputFile.isEmpty()) {
							List<Article> artList = parseArticles(singSub, inputFile);	// Parse Articles
							hr.deleteFile(inputFile);									// Delete used file
							if (args.length == 2 && args[0].equals("-ne")) 
								callHeuristic(artList, args[1], namedEntityStored);
							else
								printFeed(artList, singSub.getUrl());
						}
						else {
							System.out.println("There was an error parsing the request");
							continue;
						}
					}
				}
				if (args.length > 0) {
					for (NamedEntity value : namedEntityStored.values()) {
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
