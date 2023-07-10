package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.FileReader;
import feed.Article;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implemntacion 
 */

/* Inherits from GeneralParser */
public class RedditParser extends GeneralParser {
    
    public RedditParser(String link) {
		super(link);
	}

    /* Parse a reddit request */
    @Override
    public List<Article> parse() {
        JSONParser jsonParser = new JSONParser();
        List<Article> retList = new ArrayList<Article>();
        
        try (FileReader reader = new FileReader(this.path)) {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);                     // Get JSON Object
            JSONObject data = (JSONObject) obj.get("data");
            JSONArray arr = (JSONArray) data.get("children");                           // Get each article

            for (int i = 0; i < arr.size(); ++i) {
                JSONObject child = (JSONObject)((JSONObject) arr.get(i)).get("data");   // Get a single article
                String link = (String) child.get("url");
                String title = (String) child.get("title");
                Date date = new Date();
                String description = (String) child.get("selftext");

                Article art = new Article(title, description, date, link);
                retList.add(art);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return retList;
    }
}
