package subscription;

import java.util.List;


/* Esta clase abstrae el contenido de una sola suscripcion que ocurre en lista de suscripciones
 * que figuran en el archivo de subscripcion(json) 
 */
public class SingleSubscription {
	
	private String url;
	private List<String> urlParams;
	private String urlType;
	
	
	public SingleSubscription(String url, List<String> urlParams, String urlType) {
		this.url = url;
		this.urlParams = urlParams;
		this.urlType = urlType;
	}

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> geturlParams() {
		return this.urlParams;
	}
	public String geturlParams(int i) {
		return this.urlParams.get(i);
	}
	public void seturlParams(String urlParam) {
		this.urlParams.add(urlParam);
	}
	public int geturlParamsSize() {
		return this.urlParams.size();
	}
	public String getUrlType() {
		return this.urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	} 
	
	@Override
	public String toString() {
		return "{url=" + getUrl() + ", urlParams=" + geturlParams().toString() + ", urlType=" + getUrlType() + "}";
	}
	
	public void prettyPrint(){
		System.out.println(this.toString());
	}
	
	
	public String getFeedToRequest(int i){
		return this.getUrl().replace("%s",this.geturlParams(i));
	}
}
