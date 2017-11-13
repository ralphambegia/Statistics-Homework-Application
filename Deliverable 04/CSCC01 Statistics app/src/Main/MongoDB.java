package Main;

import com.mongodb.*;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.DB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

import com.mongodb.client.model.Indexes;
import org.bson.Document;

import java.util.*;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

public class MongoDB {
    public static MongoClient mongoClient;
    public static int key = 0;
    public MongoDB() {
        MongoClientURI connection = new MongoClientURI("mongodb://raphael:raphael24@ds157475.mlab.com:57475/cscc01");
        this.mongoClient = new MongoClient( connection );
    }
    public void connect(){
        MongoDatabase newDatabase = this.mongoClient.getDatabase("cscc01");
        MongoCollection userCollection = newDatabase.getCollection("Users");
        IndexOptions indexOptions = new IndexOptions().unique(true);
        userCollection.createIndex(Indexes.ascending("stID"), indexOptions);

    }
    public static void addToStudents(String fName, String lName, int stID){
        MongoDatabase userDatabase = mongoClient.getDatabase("cscc01");
        MongoCollection userCollection = userDatabase.getCollection("Users");
        Document student = new Document("fName", fName).append("lName", lName).append("stID", stID);
        try {
            userCollection.insertOne(student);
        }catch (MongoWriteException e){

        }
    }
    public static List<Document> getStudents(){
        MongoDatabase userDatabase = mongoClient.getDatabase("cscc01");
        MongoCollection userCollection = userDatabase.getCollection("Users");
        List<Document> users = (List<Document>) userCollection.find().into(new ArrayList<Document>());
        return users;
    }
    public static void printStudent(){
        MongoDatabase userDatabase = mongoClient.getDatabase("cscc01");
        MongoCollection userCollection = userDatabase.getCollection("Users");
        List<Document> users = (List<Document>) userCollection.find().into(new ArrayList<Document>());
        for(Document document: users){
            System.out.println(document.get("fName"));
        }

    }
    public static void addToAssignmentWDueDate(String assignmentName, String dueDate){
        MongoDatabase userDatabase = mongoClient.getDatabase("cscc01");
        MongoCollection userCollection = userDatabase.getCollection("AssignDueDate");
        Document student = new Document("assignName", assignmentName).append("dueDate", dueDate);
        try {
            userCollection.insertOne(student);
        }catch (MongoWriteException e){
        }
    }
    public static void addToAssignmentNoDueDate(String assignmentName){
        MongoDatabase userDatabase = mongoClient.getDatabase("cscc01");
        MongoCollection userCollection = userDatabase.getCollection("AssignNoDueDate");
        Document assignment = new Document("assignName", assignmentName);
        try {
            userCollection.insertOne(assignment);
        }catch (MongoWriteException e){
        }
    }
    public static void addToQuestions(String assignmentName, String qName, String mc1, String mc2, String mc3, String mc4, String mc5, int soln){
        MongoDatabase userDatabase = mongoClient.getDatabase("cscc01");
        MongoCollection userCollection = userDatabase.getCollection(assignmentName);
        Document question = new Document("assignName", assignmentName).append("question", qName).append("mc1", mc1).append("mc2", mc2).append("mc3", mc3).append("mc4", mc4).append("mc5", mc5).append("soln", soln);
        try {
            userCollection.insertOne(question);
        }catch (MongoWriteException e){
        }
    }

}