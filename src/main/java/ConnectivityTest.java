import com.mongodb.client.MongoClients;
import org.bson.Document;

public class ConnectivityTest {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage requires connection string as first and only arg");
            return;
        }
        try (var client = MongoClients.create(args[0])) {
            var reply = client.getDatabase("admin").runCommand(new Document("hello", 1));
            System.out.println(reply.toJson());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
