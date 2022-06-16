import java.util.Random;

/**
 * IDGenerator used to generate a random number between 1 and 10000 used for an ID for clients
 */
public class IDGenerator {

    private static Random r = new Random();

    public static int generateRandomID() {
        return r.nextInt((10000 - 1) + 1) + 1;
    }
}
