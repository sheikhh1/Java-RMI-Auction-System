import java.util.Random;

/**
 * Generates a random string of text from the characters below in a random format including special characters
 * Length of the string can be decided by the developer
 */
public class ChallengeGenerator {

    public static String generateChallenge(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            stringBuilder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return stringBuilder.toString();
    }
}
