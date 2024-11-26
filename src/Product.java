import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String description;
    private double cost;

    // Fixed field lengths for Random Access
    private static final int ID_LENGTH = 6;
    private static final int NAME_LENGTH = 35;
    private static final int DESCRIPTION_LENGTH = 75;

    public Product(String id, String name, String description, double cost) {
        this.id = padString(id, ID_LENGTH);
        this.name = padString(name, NAME_LENGTH);
        this.description = padString(description, DESCRIPTION_LENGTH);
        this.cost = cost;
    }

    public String getId() {
        return id.trim();
    }

    public String getName() {
        return name.trim();
    }

    public String getDescription() {
        return description.trim();
    }

    public double getCost() {
        return cost;
    }

    public static String padString(String input, int length) {
        if (input.length() > length) {
            return input.substring(0, length);
        }
        return String.format("%-" + length + "s", input);
    }

    public static String readFixedString(RandomAccessFile raf, int length) throws IOException {
        byte[] buffer = new byte[length];
        raf.read(buffer);
        return new String(buffer).trim();
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Desc: %s, Cost: %.2f", getId(), getName(), getDescription(), getCost());
    }
}