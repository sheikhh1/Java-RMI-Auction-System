import java.security.NoSuchAlgorithmException;

public class Buyer extends Client {

    /**
     * Creates a buyer object extended from client
     * @param clientId buyer ID
     * @param clientType BUYER
     * @param clientName buyers name
     * @param clientEmail buyers email
     * @throws NoSuchAlgorithmException
     */
    public Buyer(int clientId, String clientType ,String clientName, String clientEmail) throws NoSuchAlgorithmException {
        super(clientId, clientType, clientName, clientEmail);
    }
}
