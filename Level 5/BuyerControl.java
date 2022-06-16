import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Manages buyer control options
 * - Asks user for input
 * - BuyerControl directs buyer to correct functionality
 */
public class BuyerControl {

    public static void optionManager(Buyer buyer, IAuctionManager server)  throws RemoteException {
        while (true) {
            System.out.println(DisplayUtility.buyerMenu());

            Scanner scanner = new Scanner(System.in);
            int selectedOption = scanner.nextInt();

            if (selectedOption == 1) {
                System.out.println(server.listAllAuctionItems());
            } else if (selectedOption == 2) {
                System.out.println("Enter Item Id: ");
                int selectedItemID = scanner.nextInt();
                System.out.println(server.getSpec(selectedItemID, buyer.getClientId()));
            } else if (selectedOption == 3) {
                System.out.println("Enter Item Id");
                int itemID = scanner.nextInt();
                System.out.println("Please enter bid amount");
                double bidAmount = scanner.nextDouble();
                System.out.println(server.setBid(buyer, bidAmount, itemID));
            } else if (selectedOption == 4) {
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Invalid option detected. Please select from the options.");
            }
        }
    }
    
}
