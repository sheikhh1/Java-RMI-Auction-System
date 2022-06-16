import java.security.NoSuchAlgorithmException;

public class Seller extends Client {

    /**
     * Creates a seller object extended from client
     * @param clientId seller ID
     * @param clientType SELLER
     * @param clientName sellers name
     * @param clientEmail sellers email
     * @throws NoSuchAlgorithmException
     */
    public Seller(int clientId, String clientType, String clientName, String clientEmail) throws NoSuchAlgorithmException {
        super(clientId, clientType, clientName, clientEmail);
    }
}
