package mongo1;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author oracle
 */
public class Mongo1 {
   
    public static MongoCollection <Document> coleccion;
    
    public static void main(String[] args) {
     
     //crear o obxecto cliente   
     MongoClient cliente=  new MongoClient("localhost",27017);
     //conenctar a base training, podria ser proba...
     MongoDatabase base = cliente.getDatabase("training");
        //acceder a unha coleccion dubha base
        //si no lo hacemos en un metodo esto lo podemos poner asi:
         //MongoCollection <Document> coleccion=base.getCollection("scores");
        coleccion = base.getCollection("scores");
        
        //Metodos:
        
        consultar("kind","essay");
        consultaClave();
        
        
        //cerramos a conexion
            cliente.close();
        
    }
    //buscar por un campo, primero el campo y despues el valor
    public static void consultar(String campo, String valor){
        
        BasicDBObject consulta = new BasicDBObject(campo,valor);
        
        /////****Si solo queremos ver score y student hacemos lo siguiente y la clave hay que ponerle 0
        /////****para no mostrarla pero los demas con no ponerlos llega y siempre despues de la linea de arriba y antes que el cursor
        BasicDBObject claves = new BasicDBObject();
        claves.put("_id",0);
        claves.put("score",1);
        claves.put("student",1);
        ////*****Si queremos mostrar todo omitimos el **.projection(claves)** esto sería para mostrar lo que queramos
        
        FindIterable <Document> cursor = coleccion.find(consulta).projection(claves);
        
        MongoCursor<Document> iterator = cursor.iterator();
        while(iterator.hasNext()){
               Document doc=iterator.next();
               String k=doc.getString("kind");
               Double s=doc.getDouble("score");
               Double st=doc.getDouble("student");
                System.out.println(k+" "+s+" "+st);
        }
        iterator.close();
    }
    //******Este metodo no entra en el examen porque generará el calves qeu no son por defecto por lo cual
    //******para consultar por id en el examen será como un campo normal como kind en el anterior metodo
    public static void consultaClave(){
        BasicDBObject consulta = new BasicDBObject("_id",new ObjectId("4c90f2543d937c033f424738"));
        
        /////****Si solo queremos ver score y student hacemos lo siguiente y la clave hay que ponerle 0
        /////****para no mostrarla pero los demas con no ponerlos llega y siempre despues de la linea de arriba y antes que el cursor
        BasicDBObject claves = new BasicDBObject();
        claves.put("_id",0);
        claves.put("score",1);
        claves.put("student",1);
        ////*****Si queremos mostrar todo omitimos el **.projection(claves)** esto sería para mostrar lo que queramos
        
        Document doc = coleccion.find(consulta).projection(claves).first();
               Double s=doc.getDouble("score");
               Double st=doc.getDouble("student");
               System.out.println(s+" "+st);
    }
}
