package service;

//import com.mongodb.BasicDBObject;
//import com.mongodb.BulkWriteOperation;
//import com.mongodb.BulkWriteResult;
//import com.mongodb.Cursor;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
import com.mongodb.MongoClient;
//import com.mongodb.ParallelScanOptions;
//import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import org.bson.Document;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import com.mongodb.MongoClientURI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import com.mongodb.util.JSON;

@Path("/helloworld")
@ApplicationPath("/")
public class HelloWorldService {

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello World";
    }
    
	@GET
    @Path("mongo")
	@Produces("text/plain")
    //@Produces({"application/xml", "application/json"})
    public String mongoSwa() {
	    MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:VC8tvNP64pQP@127.0.0.1:27017"));
		MongoDatabase database = mongoClient.getDatabase("servicestudygroup");
		MongoCollection<Document> collection = database.getCollection("nombre");
		//List<Document> foundDocument = collection.find().into(new ArrayList<Document>());
		collection.insertOne(new Document("name:", "123sad6"));
		List<Document> foundDocument = collection.find().into(new ArrayList<Document>());
		
		mongoClient.close();
		return JSON.serialize(foundDocument);
		//return collection.find().first().toJson();
    }
	
}

