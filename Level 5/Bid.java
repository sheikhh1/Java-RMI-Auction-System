import java.io.Serializable;

/**
 * This class takes in a buyer and auction item along side a bid value to create a BID object
 * Every auctionItem has a BID amount associated to it
 */
public class Bid implements Serializable {

    private Buyer buyer;
    private double bidAmount;
    private AuctionItem auctionItem;

    /**
     * Creates a bid object for an auction item
     * @param buyer Buyer that is bidding
     * @param bidAmount Amount that buyer is bidding
     * @param auctionItem The item the buyer is bidding on
     */
    public Bid(Buyer buyer, double bidAmount, AuctionItem auctionItem) {
        this.buyer = buyer;
        this.bidAmount = bidAmount;
        this.auctionItem = auctionItem;
    }

    public Buyer getBuyer() {
        return this.buyer;
    }

    public double getBidValue() {
        return this.bidAmount;
    }

    public AuctionItem getAuctionItem() {
        return this.auctionItem;
    }
}
