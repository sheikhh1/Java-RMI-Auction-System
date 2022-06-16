

import java.io.Serializable;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public abstract class Client implements Serializable{

    private int clientId;
    private String clientType;
    private String clientName;
    private String clientEmail;
    private PrivateKey clientPrivateKey;

    private KeyPair generatedPair = RSAKeyGenerator.generateKeyPair();

    /**
     * Blueprint for client
     * @param clientId unique client ID
     * @param clientType client role (Seller, Buyer)
     * @param clientName client name
     * @param clientEmail client email
     * @throws NoSuchAlgorithmException
     */
    public Client(int clientId, String clientType, String clientName, String clientEmail) throws NoSuchAlgorithmException {
        this.clientId = clientId;
        this.clientType = clientType;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPrivateKey = generatedPair.getPrivate(); // Saves private key to client object
    }

    public int getClientId() {
        return this.clientId;
    }

    public String getClientType() {
        return this.clientType;
    }

    public String getClientName() {
        return this.clientName;
    }

    public String getClientEmail() {
        return this.clientEmail;
    }

    public void setClientEmail(String newEmail) {
        this.clientEmail = newEmail;
    }

    public void setClientName(String newName) {
        this.clientName = newName;
    }

    public PrivateKey getClientPrivateKey() {
        return this.clientPrivateKey;
    }

    public PublicKey getClientPublicKey() {
        return this.generatedPair.getPublic();
    }
}
