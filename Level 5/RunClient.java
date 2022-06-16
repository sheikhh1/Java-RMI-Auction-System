
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Handles initial client and system interaction
 * - Asks user what userType they are (BUYER/SELLER)
 * - Directs client to relevant options
 */
public class RunClient {

    public static void main(String args[]) {

         LoginManager loginManager = new LoginManager();

        try {
            String name = "server";
            Registry registry = LocateRegistry.getRegistry("localhost");
            IAuctionManager server = (IAuctionManager) registry.lookup(name);

            Scanner scanner = new Scanner(System.in);

            System.out.println(DisplayUtility.startMenu());
            int response = scanner.nextInt();
            String roleType = "";

            if (response == 1) {
                roleType = "buyer";
            } else if (response == 2) {
                roleType = "seller";
            } else if (response == 3) {
                System.out.println("Program has been terminated");
                System.exit(0);
            }


            loginManager.optionControl(roleType, server);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
