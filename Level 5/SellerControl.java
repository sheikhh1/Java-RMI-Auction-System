import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Manages all operations with regards to the seller
 * - View All Items
 * - Create an Auction
 * - Close an Auction
 * - Exit the program
 */
public class SellerControl {

    public static void optionManager(Seller seller, IAuctionManager server) throws RemoteException {
        while (true) {
            System.out.println(DisplayUtility.sellerMenu());

            Scanner scanner = new Scanner(System.in);
            int selectedOption = scanner.nextInt();

            if (selectedOption == 1) {
                System.out.println(server.listAllAuctionItems());
            } else if (selectedOption == 2) {
                System.out.println("Enter Item Name:");
                scanner.nextLine();
                String auctionItemName = scanner.nextLine();
                System.out.println("Enter Item Description:");
                String auctionItemDescription = scanner.nextLine();
                System.out.println("Enter Item Condition - USED/NEW:");
                String auctionItemCondition = scanner.nextLine();
                System.out.println("Enter Item Reserve Price");
                double auctionReservePrice = scanner.nextDouble();
                System.out.println("Enter Item Start Price");
                double auctionStartPrice = scanner.nextDouble();
                server.createAuctionItem(auctionItemName, auctionItemDescription, auctionItemCondition, auctionReservePrice, auctionStartPrice, seller);
                System.out.println("Item has been listed");
            } else if (selectedOption == 3) {
                System.out.println("Enter Auction Item ID to close");
                int auctionItemId = scanner.nextInt();
                System.out.println(server.closeAuction(seller, auctionItemId));
            } else if (selectedOption == 4) {
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Invalid option detected. Please select from the options.");
            }
        }
    }
    
}
