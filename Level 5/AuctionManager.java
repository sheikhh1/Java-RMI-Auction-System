import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all operations to and from the server
 * Manages all items currently available
 * Manages the registration and login of clients
 */
public class AuctionManager implements IAuctionManager {

    private ItemStore itemStore = new ItemStore();
    private ConcurrentHashMap<Integer, Client> clientHandler = FileManager.readClientsFromFile("ClientData");

    public AuctionManager() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    }

    @Override
    public int createAuctionItem(String auctionItemName, String auctionItemDescription, String auctionItemCondition,
            double auctionReservePrice, double auctionStartPrice, Seller seller) throws RemoteException {
        
        AuctionItem newItem = new AuctionItem(auctionItemName, auctionItemDescription, auctionItemCondition, auctionReservePrice, auctionStartPrice, seller);
        this.itemStore.add(newItem.getAuctionItemID(), newItem);

        return newItem.getAuctionItemID();
    }

    @Override
    public String listAllAuctionItems() throws RemoteException {
        String message = "";
       if (this.itemStore.itemStoreIsEmpty()) {
           message = "\nError: No auction items currently available. Try again later";
           System.out.println(message);
       } else {
           message = this.itemStore.listAllItems();
       }

       return message;
    }

    @Override
    public String closeAuction(Seller seller, int auctionItemID) throws RemoteException {
        AuctionItem itemToClose = this.itemStore.getItemById(auctionItemID);
        String message = "";

        if (seller.getClientId() != itemToClose.getSeller().getClientId()) {
            message = "\nYou did not create this auction. You cannot close it";
        } else {
            if (itemToClose.getCurrentBid() == null || itemToClose.getCurrentBid().getBidValue() < itemToClose.getAuctionReservePrice()) {
                message = "\nReserve Price was not met. Item Removed: " + auctionItemID;
            } else  {
                message = "\nBuyer Name: " + itemToClose.getCurrentBid().getBuyer().getClientName() + " | Buyer Email: " + itemToClose.getCurrentBid().getBuyer().getClientEmail()+ " has bought item: " + itemToClose.getAuctionItemID();
            }

            this.itemStore.remove(auctionItemID);
        }
        return message;
    }
    
    @Override
    public AuctionItem getSpec(int itemId, int clientRequest) throws RemoteException {
        AuctionItem returnItem = null;
        
        if (itemStore.checkIfItemExists(itemId)) {
            returnItem = this.itemStore.getItemById(itemId);
        } else {
            System.out.println("Specified Item ID does not exist");
        }

        return returnItem;
    }

    @Override
    public String setBid(Buyer buyer, double bidAmount, int auctionID) throws RemoteException {
        AuctionItem itemToBidOn = this.itemStore.getItemById(auctionID);
        String message = "";

        if (bidAmount < itemToBidOn.getAuctionStartPrice()) {
            message = "Error: Bid has not been placed as bid amount is less than item start price. Please enter a bigger amount.";
        } else {
            message = "Bid has been placed on item: " + itemToBidOn.getAuctionItemID();
            itemToBidOn.setAuctionItemBid(new Bid(buyer, bidAmount, itemToBidOn));
        }

        return message;
    }

    @Override
    public Boolean checkIfClientExists(String email) throws RemoteException {
        boolean clientExists = false;

        for (Client currentClient : this.clientHandler.values()) {
            if (currentClient.getClientEmail().equals(email)) {
                clientExists = true;
            }
        }

        return clientExists;
    }

    @Override
    public String registerClient(Client newClient) throws RemoteException {
        this.clientHandler.put(newClient.getClientId(), newClient);
        return "Client: " + newClient.getClientName() + " has been registered";
    }

    @Override
    public Client getClient(String email) throws RemoteException {
        Client clientGet = null;

        for (Client currentClient : this.clientHandler.values()) {
            if (currentClient.getClientEmail().equals(email)) {
                clientGet = currentClient;
            }
        }

        return clientGet;
    }

    // The methods below are implemented by the server

    @Override
    public String serverResponse(String challengeContent) throws RemoteException {
        return null;
    }

    @Override
    public String generateClientChallenge() throws RemoteException {
        return null;
    }

    @Override
    public boolean verifyClientResponse(String challengeContent, String clientSignature, int clientID) throws RemoteException {
        return false;
    }

}
