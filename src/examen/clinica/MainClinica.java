package examen.clinica;

import java.util.InputMismatchException;
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

        // URI de conexión a MongoDB Atlas
        String uri = "mongodb+srv://jlcastroc01_db_user:toor@cluster0.jr7c2xx.mongodb.net/?appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // Seleccionamos la base de datos
            MongoDatabase db = mongoClient.getDatabase("clinica");

            // Seleccionamos las colecciones
            MongoCollection<Document> pacientes = db.getCollection("paciente");
            MongoCollection<Document> citas = db.getCollection("cita");

            int opcion = -1;

            // Bucle principal del menú
            do {

                mostrarMenu();

                try {
                    opcion = sc.nextInt();
                    sc.nextLine();

                    switch (opcion) {

                        case 1:
                            insertarPaciente(sc, pacientes);
                            break;

                        case 2:
                            insertarCita(sc, citas);
                            break;

                        case 3:
                            mostrarPaciente(sc, pacientes);
                            break;

                        case 4:
                            listarPacientes(pacientes);
                            break;

                        case 5:
                            actualizarTelefono(sc, pacientes);
                            break;

                        case 6:
                            eliminarPaciente(sc, pacientes);
                            break;

                        case 0:
                            System.out.println("Programa finalizado.");
                            break;

                        default:
                            System.out.println("Opción no válida.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Error: tipo de dato incorrecto.");
                    sc.nextLine(); // limpiar buffer
                }

            } while (opcion != 0);

        }
    }

    // ---------------------------------------------------------
    // Método que muestra el menú principal
    // ---------------------------------------------------------
    public static void mostrarMenu() {

        System.out.println("\n1 Insertar paciente");
        System.out.println("2 Insertar cita");
        System.out.println("3 Mostrar paciente por DNI");
        System.out.println("4 Listar pacientes");
        System.out.println("5 Actualizar telefono");
        System.out.println("6 Eliminar paciente");
        System.out.println("0 Salir");
        System.out.print("Seleccione una opción: ");
    }

    // ---------------------------------------------------------
    // CREATE -> Insertar paciente
    // ---------------------------------------------------------
    public static void insertarPaciente(Scanner sc, MongoCollection<Document> pacientes) {

        try {

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("DNI: ");
            String dni = sc.nextLine();

            System.out.print("Telefono: ");
            int telefono = sc.nextInt();
            sc.nextLine();

            // Crear documento MongoDB
            Document paciente = new Document("nombre", nombre)
                    .append("dni", dni)
                    .append("telefono", telefono);

            // Insertar documento
            pacientes.insertOne(paciente);

            System.out.println("Paciente insertado correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("Error: el teléfono debe ser numérico.");
            sc.nextLine();
        }
    }

    // ---------------------------------------------------------
    // CREATE -> Insertar cita
    // ---------------------------------------------------------
    public static void insertarCita(Scanner sc, MongoCollection<Document> citas) {

        try {

            System.out.print("ID cita: ");
            int idCita = sc.nextInt();

            System.out.print("ID paciente: ");
            int idPaciente = sc.nextInt();

            sc.nextLine();

            System.out.print("Fecha: ");
            String fecha = sc.nextLine();

            System.out.print("Motivo: ");
            String motivo = sc.nextLine();

            Document cita = new Document("id_cita", idCita)
                    .append("id_paciente", idPaciente)
                    .append("fecha", fecha)
                    .append("motivo", motivo);

            citas.insertOne(cita);

            System.out.println("Cita insertada correctamente.");

        } catch (InputMismatchException e) {
            System.out.println("Error: los IDs deben ser numéricos.");
            sc.nextLine();
        }
    }

    // ---------------------------------------------------------
    // READ -> Mostrar paciente por DNI
    // ---------------------------------------------------------
    public static void mostrarPaciente(Scanner sc, MongoCollection<Document> pacientes) {

        System.out.print("Introduce DNI: ");
        String dniBuscar = sc.nextLine();

        Document doc = pacientes.find(eq("dni", dniBuscar)).first();

        if (doc != null) {
            System.out.println(doc.toJson());
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    // ---------------------------------------------------------
    // READ -> Listar todos los pacientes
    // ---------------------------------------------------------
    public static void listarPacientes(MongoCollection<Document> pacientes) {

        System.out.println("\nListado de pacientes:");

        for (Document p : pacientes.find()) {
            System.out.println(p.toJson());
        }
    }

    // ---------------------------------------------------------
    // UPDATE -> Actualizar teléfono comprobando existencia
    // ---------------------------------------------------------
    public static void actualizarTelefono(Scanner sc, MongoCollection<Document> pacientes) {

        try {

            System.out.print("DNI del paciente: ");
            String dniUpdate = sc.nextLine();

            Document existe = pacientes.find(eq("dni", dniUpdate)).first();

            if (existe != null) {

                System.out.print("Nuevo telefono: ");
                int nuevoTel = sc.nextInt();
                sc.nextLine();

                Document filtro = new Document("dni", dniUpdate);

                Document actualizacion =
                        new Document("$set", new Document("telefono", nuevoTel));

                pacientes.updateOne(filtro, actualizacion);

                System.out.println("Telefono actualizado.");

            } else {
                System.out.println("Paciente no existe.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: el teléfono debe ser numérico.");
            sc.nextLine();
        }
    }

    // ---------------------------------------------------------
    // DELETE -> Eliminar paciente
    // ---------------------------------------------------------
    public static void eliminarPaciente(Scanner sc, MongoCollection<Document> pacientes) {

        System.out.print("DNI a eliminar: ");
        String dniDelete = sc.nextLine();

        Document existe = pacientes.find(eq("dni", dniDelete)).first();

        if (existe != null) {

            pacientes.deleteOne(eq("dni", dniDelete));
            System.out.println("Paciente eliminado.");

        } else {
            System.out.println("Paciente no encontrado.");
        }
    }
}

/*
 * 📌 6. Mostrar datos concretos del documento

Ahora muestras todo:

System.out.println(doc.toJson());

Podrían pedir mostrar campos:

System.out.println("Nombre: " + doc.getString("nombre"));
System.out.println("DNI: " + doc.getString("dni"));
System.out.println("Telefono: " + doc.getInteger("telefono"));

Esto aparece en el QuickStart de los apuntes.

📌 7. Comprobar si existe antes de insertar

Esto es un clásico de examen.

Document existe = pacientes.find(eq("dni", dni)).first();

if (existe == null) {
    pacientes.insertOne(paciente);
    System.out.println("Paciente insertado");
} else {
    System.out.println("Ya existe un paciente con ese DNI");
}
📌 8. Insertar varios documentos

A veces piden insertMany.

List<Document> lista = new ArrayList<>();

lista.add(new Document("nombre", "Ana").append("dni", "111"));
lista.add(new Document("nombre", "Luis").append("dni", "222"));

pacientes.insertMany(lista);
📌 9. Buscar dentro de otra colección relacionada

Ejemplo:

// Buscar citas de un paciente
citas.find(eq("id_paciente", idPaciente));

📌 10. Tipos de datos que pueden cambiar

En los apuntes aparecen estos tipos que pueden usar en el examen:

Tipo	Ejemplo
String	nombre
int	edad
long	telefono
boolean	activo
array	telefonos
documento embebido	direccion

Ejemplo array:

List<Integer> telefonos = new ArrayList<>();
telefonos.add(666111222);
telefonos.add(777333444);

Document paciente = new Document("nombre","Juan")
        .append("telefonos", telefonos);
📌 11. Buscar con dos condiciones

Esto también aparece en los apuntes.

Filters.and(
    eq("nombre","Juan"),
    eq("dni","12345678A")
);
⭐ Plantilla mental para el examen

Si sabes estas 5 estructuras, puedes hacer cualquier CRUD:

INSERT
Document doc = new Document("campo", valor)
        .append("campo2", valor2);

collection.insertOne(doc);
READ
collection.find(eq("campo", valor)).first();
LIST
for(Document d : collection.find())
UPDATE
collection.updateOne(
    new Document("campo", valor),
    new Document("$set", new Document("campoActualizar", nuevoValor))
);
DELETE
collection.deleteOne(eq("campo", valor));
 * */
 