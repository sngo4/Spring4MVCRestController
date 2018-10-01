package spring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import spring.config.MongoFactory;
import spring.model.User;

@Component
public class UserDAO {
	static String db_name = "mydb", db_collection = "mycollection";
	
	//get list
	public List<User> users;
	{
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
		users = new ArrayList();
		// Fetching cursor object for iterating on the database records.
				DBCursor cursor = coll.find();	
				while(cursor.hasNext()) {			
					DBObject dbObject = cursor.next();

					User user = new User();
					user.setId(dbObject.get("id").toString());
					user.setName(dbObject.get("name").toString());

					// Adding the user details to the list.
					users.add(user);
				}
	}
	public List list() {
		return users;
	}
	public User get(String id){
		return users.stream()
				.filter(u -> u.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	public User create(User user){
		Random ran = new Random();
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

		// Create a new object and add the new user details to this object.
		BasicDBObject doc = new BasicDBObject();
		doc.put("id", String.valueOf(ran.nextInt(100))); 
		doc.put("name", user.getName());			

		// Save a new user to the mongo collection.
		coll.insert(doc);
		return user;
	}
	
	public String delete(String id){
		if (get(id).equals(null)) {
			return null;
		}
		Function<User, String> UsrToIdFunc = c -> c.getId();
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
		
		BasicDBObject doc = new BasicDBObject();
		doc.put("id", id); 
		doc.put("name", users.stream().filter(c -> c.getId().equals(id))
				.findFirst().get().getName());		
		
		coll.findAndRemove(doc);
		
		return id;
		
	}
	
	public User update(String id, User user){
		if (get(id).equals(null)) {
			return null;
		}
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);
		BasicDBObject doc = new BasicDBObject();
		doc.put("id", id); 
		doc.put("name", users.stream().filter(c -> c.getId().equals(id))
				.findFirst().get().getName());	
		
		BasicDBObject doc2 = new BasicDBObject();
		doc.put("id", id); 
		doc.put("name", user.getName());	
		
		coll.findAndModify(doc, doc2);
		return user;
		
	}
}
