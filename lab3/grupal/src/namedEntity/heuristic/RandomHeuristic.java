package namedEntity.heuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;
import namedEntity.NamedEntity;
import namedEntity.topics.CountrySports;
import namedEntity.topics.Others;
import namedEntity.topics.PersonPolitics;
import namedEntity.topics.PersonCulture;

public class RandomHeuristic extends Heuristic {
	
	private Random rnd = new Random();
	
	//usefull for random heuritic's consistency
	private List<String> positiveCases = new ArrayList<String>();
	private List<String> negativeCases = new ArrayList<String>();
	
	private boolean isPositiveCase(String entity){
		return this.positiveCases.contains(entity);
	}

	private boolean isNegativeCase(String entity){
		return this.negativeCases.contains(entity);
	}

	public boolean isEntity(String word){
		//already it was classified
		if (this.isPositiveCase(word)) return true;
		if (this.isNegativeCase(word)) return false;
		
		//if it was not classified yet, then lottery
		boolean b = ((int)(rnd.nextDouble() * 100)) % 2 == 0;
		if (b) this.positiveCases.add(word);
		else this.negativeCases.add(word);
		return b;
	}

	private static Object instanceTopics(String name, String category) {
		if (category != null) {
			if (category.equals("CountrySports")) {
				CountrySports i1 = new CountrySports(name, category, "Basket");
				return (Object) i1;
			}
			else if (category.equals("PersonPolitics")) {
				PersonPolitics i2 = new PersonPolitics(name, category, "International");
				return (Object) i2;
			}
			else if (category.equals("PersonCulture")) {
				PersonCulture i3 = new PersonCulture(name, category, "Music");
				return (Object) i3;
			}
			else {
				Others i4 = new Others(name);
				return (Object) i4;
			}

				/* Aca habria que hacer pattern matching con todas las combinaciones entre las clases y los temas */
				/* La informacion necesaria para instanciar los objetos deberian estar en el diccionario, aca no- */
				/* sotros lo que hacemos es completar con algunos valores al azar */
		}
		else {
			return null;
		}
	}

	public void runRdmHeuristic (String text, Map<String, NamedEntity> mapEN,String link) {
		String[] text_split = text.split("[^a-zA-Z]+");

		for (int i = 0; i < text_split.length; ++i) {
			String name = text_split[i];
			String category = null;
			if (isEntity(name)) {
				RandomHeuristic h = new RandomHeuristic();
				category = h.getCategory(name);
			}
			else if (isEntity(text_split[i])) {
				category = "Others";
			}
			else {
				continue;
			}

			if (mapEN.containsKey(name)) {
				NamedEntity aux = mapEN.get(name);
				aux.incFrequency();
				mapEN.put(name,aux);
				aux.addOcurrence(link, false, 0);
			}
			else {
				NamedEntity instanceTopic = (NamedEntity) instanceTopics(name, category);
				if (instanceTopic != null) {
					mapEN.put(name, instanceTopic);
					instanceTopic.addOcurrence(link, false, 0);
				}
			}

		}
	}
}

/* Debido a la forma en la que randomheuristic determina si es una entidad nombrada o no,
 * no podemos darle una categoria a las entidades
 */