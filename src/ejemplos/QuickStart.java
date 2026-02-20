package ejemplos;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

public class QuickStart {
    public static void main(String[] args) {

        String uri = "mongodb+srv://jlcastroc01_db_user:toor@cluster0.jr7c2xx.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            Document doc = collection.find(eq("title", "Back to the Future")).first();

            if (doc != null) {
                System.out.println("Título: " + doc.getString("title"));
                System.out.println("Año: " + doc.getInteger("year"));
                System.out.println("Géneros: " + doc.get("genres"));
            } else {
                System.out.println("No matching documents found.");
            }
        }
    }
}