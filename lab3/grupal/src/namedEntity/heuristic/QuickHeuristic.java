package namedEntity.heuristic;

import java.util.List;
import java.util.Map;

import namedEntity.NamedEntity;
import namedEntity.heuristic.myClasses.Company;
import namedEntity.topics.CountrySports;
import namedEntity.topics.Others;
import namedEntity.topics.PersonPolitics;
import namedEntity.topics.PersonCulture;

public class QuickHeuristic extends Heuristic {
	
	private static List<String> keyWords = List.of(
		    "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you",
		    "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she",
		    "her", "hers", "herself", "it", "its", "itself", "they", "them", "your",
		    "their", "theirs", "themselves", "what", "which", "who", "whom",
		    "this", "that", "these", "those", "am", "is", "are", "was", "were",
		    "be", "been", "being", "have", "has", "had", "having", "do", "does",
		    "did", "doing", "a", "an", "the", "and", "but", "if", "or",
		    "because", "as", "until", "while", "of", "at", "by", "for", "with",
		    "about", "against", "between", "into", "through", "during", "before",
		    "after", "above", "below", "to", "from", "up", "down", "in", "out",
		    "off", "over", "under", "again", "further", "then", "once", "here",
		    "there", "when", "where", "why", "how", "all", "any", "both", "each",
		    "few", "more", "most", "other", "some", "such", "no", "nor", "not",
		    "only", "own", "same", "so", "than", "too", "very", "s", "t", "can",
		    "will", "just", "don", "should", "now", "on",
		    // Contractions without '
		    "im", "ive", "id", "Youre", "youd", "youve",
		    "hes", "hed", "shes", "shed", "itd", "were", "wed", "weve",
		    "theyre", "theyd", "theyve",
		    "shouldnt", "couldnt", "musnt", "cant", "wont",
		    // Common uppercase words
		    "hi", "hello"
			);
	
	public boolean isEntity(String word) {
		return word.length() > 1 && word.substring(0, 1).compareTo(word.substring(0, 1).toUpperCase()) == 0 && !keyWords.contains(word.toLowerCase());
	}

	private static NamedEntity instanceTopics(String name, String firstWord) {
		QuickHeuristic h = new QuickHeuristic();
		String category = h.getCategory(firstWord);
		if (category != null) {
			if (category.equals("CountrySports")) {
				CountrySports i1 = new CountrySports(name, category, "Basket");
				return (NamedEntity) i1;
			}
			else if (category.equals("Company")) {
				Company i2 = new Company(name, category, name);
				return (NamedEntity) i2;
			}
			else if (category.equals("PersonPolitics")) {
				PersonPolitics i3 = new PersonPolitics(name, category, "International");
				return (NamedEntity) i3;
			}
			else if (category.equals("PersonCulture")) {
				PersonCulture i4 = new PersonCulture(name, category, "Music");
				return (NamedEntity) i4;
			}
			else {
				Others i5 = new Others(name);
				return (NamedEntity) i5;
			}

				/* Aca habria que hacer pattern matching con todas las combinaciones entre las clases y los temas */
				/* La informacion necesaria para instanciar los objetos deberian estar en el diccionario, aca no- */
				/* sotros lo que hacemos es completar con algunos valores al azar */
		}
		else {
			return null;
		}
	}
	
	public void runQuickHeuristic(String args, Map<String, NamedEntity> mapEN,String link) {
		// Separate the file into words
		String[] text_split = args.split("[^a-zA-Z]+");
		// Parse the doc, looking for named entities 
		for (int i = 0; i < text_split.length; i++) {
			String name = text_split[i];
			String firstWord = null;
			if (isEntity(name)) {  //isEntity from this file
				firstWord = name;
				while (i != text_split.length-1 && isEntity(text_split[i+1])) {
					name = name + " " + text_split[i+1];
					i++;
				}
					
				if (mapEN.containsKey(name)) {
					NamedEntity aux = mapEN.get(name);
					aux.incFrequency();
					mapEN.put(name,aux);
					aux.addOcurrence(link, false, 0);
				}
				else {
					NamedEntity instanceTopic = (NamedEntity) instanceTopics(name, firstWord);
					if (instanceTopic != null) {
						mapEN.put(name, instanceTopic);
						instanceTopic.addOcurrence(link, false, 0);
					}
				}
			}
		}
	}
/* 
 * Si en la palabra i es una identidad nombrada, mira a la izq para ver la categoria y
 * a la derecha para ver si la entidad nombrada es una composicion de dos palabras.
 * El resto de los casos faltan de implementar pero son triviales
 * obs : separa algunos casos dependiendo de donde se detecta la entidad
*/
}

