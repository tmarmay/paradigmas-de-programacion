package parser;

import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import subscription.SingleSubscription;

/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 */

/* Inherits from GeneralParser */
public class SubscriptionParser extends GeneralParser {

    public SubscriptionParser(String link) {
		super(link);
	}

    /* Parses URL parameters */
	private static List<String> parseURLParams(JSONObject obj) {
        List<String> retList = new ArrayList<String>();
        JSONArray arr = (JSONArray) obj.get("urlParams"); // Get urlParams array
        
        for (Object c : arr) {
            retList.add((String)c); // Append each parameter to return List
        }

        return retList;
    }

    /* Parses each subscription to SingleSubscription */
    private static SingleSubscription parseSingleSubscription(JSONObject obj) {
        String url = (String) obj.get("url");
        List<String> urlParams = parseURLParams(obj);
        String urlType = (String) obj.get("urlType");

        SingleSubscription s = new SingleSubscription(url, urlParams, urlType);
        return s;
    }

    /* Parses JSON File */
    @Override
    public List<SingleSubscription> parse() {
        JSONParser jsonParser = new JSONParser();
        List<SingleSubscription> retList = new ArrayList<SingleSubscription>();

        try (FileReader reader = new FileReader(this.path)) {
            Object obj = jsonParser.parse(reader);  // Open the file
            JSONArray arr = (JSONArray) obj;        // Create an array with each subscription
            for (int i = 0; i < arr.size(); ++i) {
                retList.add(parseSingleSubscription((JSONObject)arr.get(i)));   // Parse each subscriptions
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return retList;
    }
}
