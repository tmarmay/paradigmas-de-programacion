package namedEntity;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import java.util.Collections;
import java.util.Comparator;

/*Esta clase modela la nocion de entidad nombrada*/
public class NamedEntity implements Serializable {
	String name;
	String category;
	int frequency;
	List<Integer> invertedIndexKey; 
	List<String> invertedIndexValue;

	public NamedEntity(String name, String category, int frequency) {
		super();
		this.name = name;
		this.category = category;
		this.frequency = frequency;
		this.invertedIndexKey = new ArrayList<Integer> ();
		this.invertedIndexValue = new ArrayList<String> ();
	}

	public void addOcurrence(String link, Boolean highMode, Integer frec){
		// La entidad nombrada viene de un link ya usado
		if (this.invertedIndexValue.contains(link)){
			int i = 0;
			for ( String l : this.invertedIndexValue) {
				if (l.equals(link)){
					Integer newOccurrence;
					if (highMode)
						newOccurrence = this.invertedIndexKey.get(i) + frec;
					else
						newOccurrence = this.invertedIndexKey.get(i) + 1;
					this.invertedIndexKey.set(i,newOccurrence);
					break;
				}
				i++;
			}
		}			
		
		//La entidad nombrada viene de un nuevo link
		else{
			System.out.println(link);
			String newElem = link;
			this.invertedIndexValue.add(newElem);
			if (highMode)
				this.invertedIndexKey.add(frec);
			else 
				this.invertedIndexKey.add(1);
		}	
	}
	
	public Integer getInvertIndexSize() {
		return this.invertedIndexKey.size();
	}
	
	public Integer getLinkFrecIndex(Integer i) {
		return this.invertedIndexKey.get(i);
	}

	public String getLinkNameIndex(Integer i) {
		return this.invertedIndexValue.get(i);
	}
	
	public void prePrint() {
		List<Integer> a = this.invertedIndexKey;
		List<String> b = this.invertedIndexValue;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            indices.add(i);
        }

        Collections.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                // Ordenar la lista 'a' de mayor a menor
                return Integer.compare(a.get(i2), a.get(i1));
            }
        });

        this.invertedIndexKey = new ArrayList<>();
        this.invertedIndexValue = new ArrayList<>();
        for (int index : indices) {
            this.invertedIndexKey.add(a.get(index));
            this.invertedIndexValue.add(b.get(index));
        }
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String name) {
		this.category = name;
	}

	public int getFrequency() {
		return this.frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void incFrequency() {
		this.frequency++;
	}

	@Override
	public String toString() {
		return "ObjectNamedEntity [name=" + name + ", frequency=" + frequency + "]";
	}
	
	public void prettyPrint(){
		System.out.println("Esta entidad nombrada : " + this.getName() + " fue categorizada como : " + this.getCategory() + ", y encontrada en : ");
		for (int i = 0; i < this.invertedIndexKey.size(); i++){
			System.out.println(this.invertedIndexKey.get(i) + " " + this.invertedIndexValue.get(i));
		}
		System.out.println("Con un total de " + this.getFrequency() + " apariciones");
	}
 
}



