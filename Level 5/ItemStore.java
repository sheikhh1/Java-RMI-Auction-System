import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ItemStore manages all functionality for each item stored within the server
 */
public class ItemStore {

    public ConcurrentHashMap<Integer, AuctionItem> itemStore = new ConcurrentHashMap<>();

    public ItemStore() {
        super();
    }

    /**
     * Add Item to itemStore
     * @param itemId
     * @param item
     */
    public void add(int itemId, AuctionItem item) {
        itemStore.put(itemId, item);
    }

    /**
     * Remove item from item store
     * @param itemId
     */
    public void remove(int itemId) {
        itemStore.remove(itemId);
    }

    /**
     * Checks if item exists or not
     * @param itemId
     * @return
     */
    public boolean checkIfItemExists(int itemId) {
        return itemStore.containsKey(itemId);
    }

    /**
     * Get auction item from item ID
     * @param itemId
     * @return
     */
    public AuctionItem getItemById(int itemId) {
        return itemStore.get(itemId);
    }

    /**
     * List all items with their current bid
     * @return - A string of formatted items - alongside any current bid
     */
    public String listAllItems() {
        List<String> listOfItemNames = new ArrayList<>();
        String message = "";


        for(AuctionItem value : this.itemStore.values()) {

            if (value.getCurrentBid() == null) {
                message = "[No Bidder Currently]";
            } else {
                message = "[Current Bidder: " + value.getCurrentBid().getBuyer().getClientName() + " with bid of " + value.getCurrentBid().getBidValue() + "]";
            }
            String s = "Item ID: " +  value.getAuctionItemID() + " | Item Name: " + value.getAuctionItemName() +  " | Item Start Price " + value.getAuctionStartPrice() + " | " +  message;
            listOfItemNames.add(s);
        }

        return  String.join("\n", listOfItemNames);
    }

    /**
     * Check if items are empty or not
     * @return - boolean value (true if empty, false if not empty)
     */
    public boolean itemStoreIsEmpty() {
        return itemStore.isEmpty();
    }
}
