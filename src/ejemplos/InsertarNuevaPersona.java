package ejemplos;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import java.util.ArrayList;

public class InsertarNuevaPersona {
	public static void main(String[] args) {
		String uri = "mongodb+srv://jlcastroc01_db_user:toor@cluster0.jr7c2xx.mongodb.net/?appName=Cluster0";
// Crear el cliente de MongoDB
		try (MongoClient mongoClient = MongoClients.create(uri)) {
// Obtener la base de datos (si no existe, MongoDB la crea automáticamente)
			MongoDatabase baseDeDatos = mongoClient.getDatabase("miBaseDeDatos");
// Obtener la colección 'persona'
			MongoCollection<Document> coleccion = baseDeDatos.getCollection("persona");
// Crear el ArrayList de teléfonos
			ArrayList<Long> telefonos = new ArrayList<Long>();
			telefonos.add(927536653L);
			telefonos.add(668888833L);
// Crear el documento que queremos insertar
			Document documento = new Document("nombre", "Carmen").append("correo", "").append("telefono", telefonos)
					.append("direccion", new Document("calle", "iglesia").append("población", "Casatejada")
							.append("códigoPostal", 10520));
// Insertar el documento en la colección y obtener el resultado de la operación
			InsertOneResult resultado = coleccion.insertOne(documento);
// Comprobar si la inserción fue exitosa
			if (resultado.getInsertedId() != null) {
				System.out.println("Insertado con el ID: " + resultado.getInsertedId());
			} else {
				System.out.println("La inserción falló.");
			}
		} catch (Exception e) {
			System.out.println("Error al conectarse a MongoDB: " + e.getMessage());
		}
	}
}