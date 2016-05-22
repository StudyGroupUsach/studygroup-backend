package mongo;

//import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoEJB {

private MongoClient mongoClient = null;

@Lock(LockType.READ)
public MongoClient getMongoClient(){
return mongoClient;
}

@PostConstruct
public void init() {
	//String mongoIpAddress = "127.0.0.1";
	//Integer mongoPort = 27017;
	String user = "admin";
	String password = "I1aPBBqdCq1i";
	String url = "127.0.0.1";
	String port = "27017";
	
	String connector = "mongodb://" + user + ":" + password + "@" + url + ":" + port;
	MongoClientURI mongoClientURI = new MongoClientURI (connector);

	mongoClient = new MongoClient(mongoClientURI);

}

}