package subscription;

import java.util.List;
import parser.SubscriptionParser;

/* Esta clase abstrae el contenido del archivo de suscripcion(json) */
public class Subscription {
	
	private List<SingleSubscription> suscriptionsList;
	
	public Subscription(String subscriptionFilePath) {
		super();
		SubscriptionParser parser = new SubscriptionParser(subscriptionFilePath);
		this.suscriptionsList = parser.parse();
	}

	public int getLength() {
		return this.suscriptionsList.size();
	}

	public List<SingleSubscription> getSubscriptionsList(){
		return this.suscriptionsList;
	}

	public void addSingleSubscription(SingleSubscription s) {
		this.suscriptionsList.add(s);
	}
	
	public SingleSubscription getSingleSubscription(int i){
		return this.suscriptionsList.get(i);
	}

	@Override
	public String toString() {
		String str ="";
		for (SingleSubscription s: getSubscriptionsList()){
			str += s.toString();
		}
		return "[" + str + "]";
	}	
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}
}
