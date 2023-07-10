package namedEntity;

import java.io.Serializable;

/*Esta clase modela la nocion de entidad nombrada*/

public class NamedEntity implements Serializable{
	String name;
	String category;
	int frequency;
	
	public NamedEntity(String name, String category, int frequency) {
		super();
		this.name = name;
		this.category = category;
		this.frequency = frequency;
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
		System.out.println(this.getName() + " " + this.getCategory() + " " + this.getFrequency());
	}
}



