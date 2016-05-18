package mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class mongoConfig {
	private String user;
	private String password;
	private String url;
	private String port;	
	
	public mongoConfig(){
		this.user = "admin";
		this.password = "VC8tvNP64pQP";
		this.url = "127.0.0.1";
		this.port = "27017";
	}
	public mongoConfig(String user, String password, String url, int port){
		this.user = user;
		this.password = password;
		this.url = url;
		this.port = port+"";
	}
	
	public void setUser(String user){
		this.user = user;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setURL(String url){
		this.url = url;
	}
	
	public void setPort(int port){
		this.port = port+"";
	}
	
	public MongoClient newConnection(){
		String connector = "mongodb://" + this.user + ":" + this.password + "@" + this.url + ":" + this.port;
		MongoClientURI mguri = new MongoClientURI (connector);
		return new MongoClient(mguri);
	}	
}
