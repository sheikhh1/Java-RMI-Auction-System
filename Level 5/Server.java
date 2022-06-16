
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class Server implements IAuctionManager {

    private AuctionManager auctionManager = new AuctionManager();
    private PrivateKey serverPrivateKey;

    public Server() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        super();
        KeyPair serverKeyPair = RSAKeyGenerator.generateKeyPair();
        this.serverPrivateKey = serverKeyPair.getPrivate();
        FileManager.writePublicKeyToFile("ServerPublicKey", serverKeyPair.getPublic());
    }

    public static void main(String args[]) {
        try {
            Server s = new Server();
            String name = "server";
            IAuctionManager stub = (IAuctionManager) UnicastRemoteObject.exportObject(s, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Server Ready");
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }

    public PrivateKey getServerPrivateKey() {
        return this.serverPrivateKey;
    }

    @Override
    public int createAuctionItem(String auctionItemName, String auctionItemDescription, String auctionItemCondition, double auctionReservePrice, double auctionStartPrice, Seller seller) throws RemoteException {
        return this.auctionManager.createAuctionItem(auctionItemName, auctionItemDescription, auctionItemCondition, auctionReservePrice, auctionStartPrice, seller);
    }

    @Override
    public String closeAuction(Seller seller, int auctionItemID) throws RemoteException {
        return this.auctionManager.closeAuction(seller, auctionItemID);
    }

    @Override
    public String listAllAuctionItems() throws RemoteException {
        return this.auctionManager.listAllAuctionItems();
    }

    @Override
    public AuctionItem getSpec(int auctionItemId, int clientId) throws RemoteException {
        return this.auctionManager.getSpec(auctionItemId, clientId);
    }

    @Override
    public String setBid(Buyer buyer, double bidAmount, int auctionID) throws RemoteException {
        return this.auctionManager.setBid(buyer, bidAmount, auctionID);
    }

    @Override
    public Boolean checkIfClientExists(String email) throws RemoteException {
       return this.auctionManager.checkIfClientExists(email);
    }

    @Override
    public String registerClient(Client newClient) throws RemoteException {
        return this.auctionManager.registerClient(newClient);
    }

    @Override
    public Client getClient(String email) throws RemoteException {
        return this.auctionManager.getClient(email);
    }

    @Override
    public String serverResponse(String challengeContent) throws Exception {
        return SignatureManager.sign(challengeContent, this.getServerPrivateKey());
    }

    @Override
    public String generateClientChallenge() throws RemoteException {
        return ChallengeGenerator.generateChallenge(10);
    }

    @Override
    public boolean verifyClientResponse(String challengeContent, String clientSignature, int clientID) throws Exception {
        return SignatureManager.verify(challengeContent, clientSignature, FileManager.getPublicKeyFromFile(String.valueOf(clientID)));
    }
}
