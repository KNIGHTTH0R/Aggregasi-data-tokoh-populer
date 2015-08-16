/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datastore;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import twitter4j.Status;
import twitter4j.json.DataObjectFactory;

/**
 *
 * @author ahmadluky
 */
public class MongoDB {
    
    public String host          = "localhost";
    public int port             = 27017;
    public String databases     = "local"; 
    public DBCollection table ;
    
    /**
     * @param collection 
     * @return  
     */
    public  MongoDB(String collection)
    {
	MongoClient mongo   = new MongoClient(this.host, this.port);
        DB db               = mongo.getDB(this.databases);
        this.table          = db.getCollection(collection);
    }
    
    /**
     *
     * @param host
     * @param database 
     * @param collection 
     */
    public MongoDB(String host, String database, String collection) 
    {
	MongoClient mongo = new MongoClient(host, 27017);
        DB db = mongo.getDB(database);
        this.table = db.getCollection(collection);
    }
    
    public ArrayList Read_mongoD(Object a, Object b)
    {       
        ArrayList Mongo_Dis         = new ArrayList();
        BasicDBObject searchQuery   = new BasicDBObject();
	searchQuery.put((String) a,b);
	DBCursor cursor             = this.table.find(searchQuery);
	while (cursor.hasNext()) 
        {
            Mongo_Dis.add(cursor.next());
	}
        return Mongo_Dis;
    }
    
    public void Insert_mongoD(HashMap m)
    {
        BasicDBObject document = new BasicDBObject();
        
        Set set = m.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()) {
           Map.Entry me = (Map.Entry)i.next();
           document.put((String)me.getKey(), me.getValue()); 
        }
	
	this.table.insert(document);
    }
    
    public void Insert_mongoDJSON(Status json)
    {
        String _json = DataObjectFactory.getRawJSON(json);
    }
}
