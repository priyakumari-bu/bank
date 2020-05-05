
import java.util.Random;

// final public class for IDs
// All IDs are hex strings of length 14

final public class ID {

    // HexString for the id of length 14
    public final String id = generate();

    // generates the random ID
    private String generate() {
        Random random = new Random();
        return Integer.toHexString(random.nextInt()) + Integer.toHexString(random.nextInt());
    }

    public String toString() {
        return id;
    }

}
