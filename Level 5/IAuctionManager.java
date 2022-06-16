import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IAuctionManager extends Remote{

    /**
     * This is to create an auction item
     * @param auctionItemName - item name
     * @param auctionItemDescription - item description
     * @param auctionItemCondition - item conditon (USED/NEW)
     * @param auctionReservePrice - reserve price of item
     * @param auctionStartPrice - start price of item
     * @param seller - The person who is selling the item
     * @return - Auction Item ID
     * @throws RemoteException
     */
    int createAuctionItem(String auctionItemName, String auctionItemDescription, String auctionItemCondition, double auctionReservePrice, double auctionStartPrice, Seller seller) throws RemoteException;

    /**
     * This closes an auction
     * @param seller - Seller trying to close auction
     * @param auctionItemID - ID of item to be removed
     * @return - Returns confirmation message or error message
     * @throws RemoteException
     */
    String closeAuction(Seller seller, int auctionItemID) throws RemoteException;

    /**
     * List All Items
     * @return - List converted to String
     * @throws RemoteException
     */
    String listAllAuctionItems() throws RemoteException;

    /**
     *
     * @param auctionItemId - ID of the item
     * @param clientId - Client ID that is wanting the spec
     * @return - Auction Item Spec
     * @throws RemoteException
     */
    AuctionItem getSpec(int auctionItemId, int clientId) throws RemoteException;

    /**
     * Set Bid for an item
     * @param buyer - Buyer that wants to bid
     * @param bidAmount - Amount of Bid
     * @param auctionID -Item ID to bid on
     * @return - Confirmation message
     * @throws RemoteException
     */
    String setBid(Buyer buyer, double bidAmount, int auctionID) throws RemoteException;

    /**
     * Checks if client exists
     * @param email - email to be checked
     * @return - true if client does exist, false if client does not exist
     * @throws RemoteException
     */
    Boolean checkIfClientExists(String email) throws RemoteException;

    /**
     * Register Client to system
     * @param newClient - New client to register
     * @return - Confirmation message
     * @throws RemoteException
     */
    String registerClient(Client newClient) throws RemoteException;

    /**
     * Looks for a specific client
     * @param email - Client email to search for
     * @return Client found
     * @throws RemoteException
     */
    Client getClient(String email) throws RemoteException;

    /**
     * Server signs a challenge from client
     * @param challengeContent Generated string
     * @return Server signature back to client
     * @throws Exception
     */
    String serverResponse(String challengeContent) throws Exception;

    /**
     * Generates a challenge for the client
     * @return Generated challenge
     * @throws RemoteException
     */
    String generateClientChallenge() throws RemoteException;

    /**
     * Gets client signature and verifies using client public key
     * @param challengeContent Challenge
     * @param clientSignature Client Signature
     * @param clientID Client ID
     * @return True if verification is successful, else false
     * @throws Exception
     */
    boolean verifyClientResponse(String challengeContent, String clientSignature, int clientID) throws Exception;
}