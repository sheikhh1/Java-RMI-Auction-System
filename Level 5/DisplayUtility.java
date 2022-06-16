import java.util.ArrayList;
import java.util.List;

/**
 * DisplayUtility can be called in a static manner to display the relevant menus to the clients
 * - Menus all stored in one class for ease of usability
 */
public class DisplayUtility {

    public static String loginMenu() {
        List<String> loginOptions = new ArrayList<>();

        loginOptions.add("\nClient Login / Client Register");
        loginOptions.add("----------------------");
        loginOptions.add("[1] -> Client Login");
        loginOptions.add("[2] -> Client Registration");
        loginOptions.add("[3] -> Exit");

        return String.join("\n", loginOptions);
    }

    public static String buyerMenu() {

        List<String> clientOptions = new ArrayList<>();

        clientOptions.add("\nBuyer Options");
        clientOptions.add("----------------------");
        clientOptions.add("[1] -> View All Items");
        clientOptions.add("[2] -> Get Item Specification");
        clientOptions.add("[3] -> Bid on a Item");
        clientOptions.add("[4] -> Exit");

        String menu = String.join("\n", clientOptions);

        return menu;
    }

    public static String sellerMenu() {

        List<String> clientOptions = new ArrayList<>();

        clientOptions.add("\nSeller Options");
        clientOptions.add("----------------------");
        clientOptions.add("[1] -> View All Items");
        clientOptions.add("[2] -> Create an Auction");
        clientOptions.add("[3] -> Close an Auction");
        clientOptions.add("[4] -> Exit");

        String menu = String.join("\n", clientOptions);

        return menu;
    }

    public static String startMenu() {

        List<String> startOptions = new ArrayList<>();

        startOptions.add("Welcome. Please select what best describes you: ");
        startOptions.add("----------------------");
        startOptions.add("[1] -> Buyer");
        startOptions.add("[2] -> Seller");
        startOptions.add("[3] -> Exit");

        String menu = String.join("\n", startOptions);

        return menu;
    }
}
