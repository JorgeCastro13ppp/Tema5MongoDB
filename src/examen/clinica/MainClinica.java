
package examen.clinica;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

public class MainClinica {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String uri = "TU_URI";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase db = mongoClient.getDatabase("clinica");

            MongoCollection<Document> pacientes = db.getCollection("paciente");
            MongoCollection<Document> citas = db.getCollection("cita");

            int opcion;

            do {

                System.out.println("\n1 Insertar paciente");
                System.out.println("2 Insertar cita");
                System.out.println("3 Mostrar paciente por DNI");
                System.out.println("4 Listar pacientes");
                System.out.println("5 Actualizar telefono");
                System.out.println("6 Eliminar paciente");
                System.out.println("0 Salir");

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {

                case 1: System.out.println("Nombre:");
                String nombre = sc.nextLine();

                System.out.println("DNI:");
                String dni = sc.nextLine();

                System.out.println("Telefono:");
                int telefono = sc.nextInt();

                Document paciente = new Document("nombre", nombre)
                        .append("dni", dni)
                        .append("telefono", telefono);

                pacientes.insertOne(paciente);

                System.out.println("Paciente insertado");
                break;
                
                case 2:

                	System.out.println("ID cita:");
                	int idCita = sc.nextInt();

                	System.out.println("ID paciente:");
                	int idPaciente = sc.nextInt();

                	sc.nextLine();

                	System.out.println("Fecha:");
                	String fecha = sc.nextLine();

                	System.out.println("Motivo:");
                	String motivo = sc.nextLine();

                	Document cita = new Document("id_cita", idCita)
                	        .append("id_paciente", idPaciente)
                	        .append("fecha", fecha)
                	        .append("motivo", motivo);

                	citas.insertOne(cita);

                	System.out.println("Cita insertada");
                	break;
                	
                case 3:

                	System.out.println("Introduce DNI:");
                	String dniBuscar = sc.nextLine();

                	Document doc = pacientes.find(eq("dni", dniBuscar)).first();

                	if (doc != null) {
                	    System.out.println(doc.toJson());
                	} else {
                	    System.out.println("Paciente no encontrado");
                	}

                	break;
                	
                case 4:

                	for (Document p : pacientes.find()) {
                	    System.out.println(p.toJson());
                	}

                	break;
                	
                case 5:

                	System.out.println("DNI del paciente:");
                	String dniUpdate = sc.nextLine();

                	Document existe = pacientes.find(eq("dni", dniUpdate)).first();

                	if (existe != null) {

                	    System.out.println("Nuevo telefono:");
                	    int nuevoTel = sc.nextInt();

                	    Document filtro = new Document("dni", dniUpdate);

                	    Document actualizacion =
                	            new Document("$set", new Document("telefono", nuevoTel));

                	    pacientes.updateOne(filtro, actualizacion);

                	    System.out.println("Telefono actualizado");

                	} else {
                	    System.out.println("Paciente no existe");
                	}

                	break;
                	
                case 6:

                	System.out.println("DNI a eliminar:");
                	String dniDelete = sc.nextLine();

                	Document existe2 = pacientes.find(eq("dni", dniDelete)).first();

                	if (existe2 != null) {

                	    pacientes.deleteOne(eq("dni", dniDelete));
                	    System.out.println("Paciente eliminado");

                	} else {
                	    System.out.println("Paciente no encontrado");
                	}

                	break;
                	
                case 0:
                	System.out.println("Programa finalizado");
                	break;
                	}
                	} while (opcion != 0);

                	}
                	}
                	}
