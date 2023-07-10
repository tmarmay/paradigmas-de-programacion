package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import feed.Article;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */

public class RssParser extends GeneralParser {
	
    public RssParser(String link) {
		super(link);
	}

    /* Get content from an ´item´ given a tag */
	private String getNode (Element el, String tag) {
        Node node = el.getElementsByTagName(tag).item(0);
        return node.getTextContent();
    }

    /* Create an article from an ´item´ */
    private Article createArticle(Node iNode) {
        Element eElement = (Element) iNode;
                    
        String pattern = "E, dd MMMM yyyy HH:mm:ss Z";  // Format rss date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("en"));
        
        String title = getNode(eElement, "title");
        String description = getNode(eElement, "description");
        String link = getNode(eElement, "link");
        String d = getNode(eElement,"pubDate");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(d);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Article art = new Article(title, description, date, link);

        return art;
    }

    /* Parse a rss (xml) request */
    @Override
    public List<Article> parse() {
        List<Article> artList = new ArrayList<Article>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(this.path);   // Read xml file
            doc.getDocumentElement().normalize();

            /* Obtain ´item´ elements from xml */
            NodeList itemList = doc.getElementsByTagName("item");
            
            /* Parse each ´item´ to Article */
            for (int temp = 0; temp < itemList.getLength(); temp++){
                Node iNode = itemList.item(temp);   // Get an item
                
                if (iNode.getNodeType() == Node.ELEMENT_NODE) {
                    Article art = createArticle(iNode);
                    artList.add(art);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return artList;
    }
}

