
import java.io.Serializable;

public class AuctionItem implements Serializable{

    private int auctionItemId;
    private String auctionItemName;
    private String auctionItemDescription;
    private String auctionItemCondition;
    private double auctionReservePrice;
    private double auctionStartPrice;
    private Bid currentBid;
    private Seller seller;

    /**
     * Blueprint of auction item
     * @param auctionItemName Item Name
     * @param auctionItemDescription Item Description
     * @param auctionItemCondition Item Condition (USED/NEW)
     * @param auctionReservePrice Item Reserve Price
     * @param auctionStartPrice Item Start Price
     * @param seller Person who listed the item
     */
    public AuctionItem(String auctionItemName, String auctionItemDescription, String auctionItemCondition, double auctionReservePrice, double auctionStartPrice, Seller seller) {
        this.auctionItemId = IDGenerator.generateRandomID();
        this.auctionItemName = auctionItemName;
        this.auctionItemDescription = auctionItemDescription;
        this.auctionItemCondition = auctionItemCondition;
        this.auctionReservePrice = auctionReservePrice;
        this.auctionStartPrice = auctionStartPrice;
        this.currentBid = null;
        this.seller = seller;
    }

    public int getAuctionItemID() {
        return this.auctionItemId;
    }

    public String getAuctionItemName() {
        return this.auctionItemName;
    }

    public Bid getCurrentBid() {
        return this.currentBid;
    }

    public double getAuctionReservePrice() {
        return this.auctionReservePrice;
    }

    public double getAuctionStartPrice() {
        return this.auctionStartPrice;
    }

    public void setAuctionItemBid(Bid newBid) {
        this.currentBid = newBid;
    }

    public Seller getSeller() {
        return this.seller;
    }

    @Override
    public String toString() {
        return "Auction Item: " + this.auctionItemId + "\n" + "Item Name: " + this.auctionItemName + "\n" + "Item Description: " + this.auctionItemDescription + "\n" + "Item Condition: " + this.auctionItemCondition + "\n" + "Item Price: " + this.auctionStartPrice;
    }
}
