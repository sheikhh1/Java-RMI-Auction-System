import java.util.*;

public class LoginManager {

    /**
     * Displays options to the user to either login or register an account
     * @param clientType
     * @param server
     * @throws Exception
     */
    public void optionControl(String clientType, IAuctionManager server) throws Exception {
        System.out.println(DisplayUtility.loginMenu());

        Scanner scanner = new Scanner(System.in);
        int selectedOption = scanner.nextInt();

        System.out.println(clientType);

        if (selectedOption == 1) {
           loginClient(clientType, server);
        } else if (selectedOption == 2) {
            registerClient(clientType, server);
            optionControl(clientType, server);
        } else if (selectedOption == 3) {
            System.out.println("\nProgram has been terminated");
            System.exit(0);
        }

        scanner.close();
    }

    /**
     * Registers Client to the system
     * Writes client details to relevant text files
     * @param sellerType - BUYER/SELLER
     * @param server
     * @throws Exception
     */
    private void registerClient(String sellerType, IAuctionManager server) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nRegister Options");
        System.out.println("----------------------");
        System.out.println("Enter your email: ");
        String clientEmail = scanner.nextLine();

        // Check whether the user has signed up before or not
        if (server.checkIfClientExists(clientEmail)) {
            System.out.println("Email is already registered. Please login");
            optionControl(sellerType, server);
        }

        System.out.println("Enter your name: ");
        String clientName = scanner.nextLine();


        if (sellerType.equals("buyer")) {
            Client newBuyer = new Buyer(IDGenerator.generateRandomID(), "BUYER", clientName, clientEmail);
            // Write client details to client data file
            FileManager.writeClientToFile("ClientData", clientInfoToLIst(newBuyer.getClientId(), "BUYER", clientName, clientEmail));
            // Add client to live client handler
            server.registerClient(newBuyer);
            // Write public key to text file
            FileManager.writePublicKeyToFile(String.valueOf(newBuyer.getClientId()), newBuyer.getClientPublicKey());

        } else if (sellerType.equals("seller")) {
            Client newSeller = new Seller(IDGenerator.generateRandomID(), "SELLER", clientName, clientEmail);
            // Write client details to the client data file
            FileManager.writeClientToFile("ClientData", clientInfoToLIst(newSeller.getClientId(),"SELLER", clientName, clientEmail));
            // Add client to live client handler
            server.registerClient(newSeller);
            // Write public key to text file
            FileManager.writePublicKeyToFile(String.valueOf(newSeller.getClientId()), newSeller.getClientPublicKey());
        }

    }

    /**
     * Logs in Client to the system using their unique email
     * @param clientType - Either buyer or seller
     * @param server - Server instance
     * @throws Exception
     */
    private void loginClient(String clientType, IAuctionManager server) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Client clientLoggedIn = null;
        System.out.println("\nLogin Options");
        System.out.println("----------------------");
        System.out.println("Enter your email: ");
        String clientEmail = scanner.nextLine();

        // Check if client exists
        if (server.checkIfClientExists(clientEmail)) {
            clientLoggedIn = server.getClient(clientEmail);
            System.out.println(clientLoggedIn.getClientType());
            // Validation to ensure a buyer isn't trying to act as a seller
            if (!clientLoggedIn.getClientType().toLowerCase(Locale.ROOT).equals(clientType)) {
                System.out.println("\nError: Cannot log in due to difference in selected role and account role (E.g Selected Buyer but logging in as Seller)");
                optionControl(clientType, server);
            }

            // Client generates challenge for server
            String clientChallenge = ChallengeGenerator.generateChallenge(10);
            // Server signs client challenge with private key and returns signed signature
            String verifyClientResponse = server.serverResponse(clientChallenge);
            if (clientLoggedIn instanceof Buyer) {
                // If the server signature is verified using server public key - Then server is verified (3 - Step)
                if (SignatureManager.verify(clientChallenge, verifyClientResponse, FileManager.getPublicKeyFromFile("ServerPublicKey"))) {
                    System.out.println("Server Verified");

                    // Server Generates challenge for Client
                    String serverChallenge = server.generateClientChallenge();
                    // Client signs the challenge with their private key
                    String clientSignature = SignatureManager.sign(serverChallenge, clientLoggedIn.getClientPrivateKey());
                    // Client signature is sent back to server to be verified (5-step)
                    if (server.verifyClientResponse(serverChallenge, clientSignature, clientLoggedIn.getClientId())) {
                        System.out.println("Client Verified");
                        System.out.println("Successfully Logged In Buyer: " + server.getClient(clientEmail).getClientName());
                        // Log In Client
                        BuyerControl.optionManager((Buyer) clientLoggedIn, server);
                    }
                } else {
                    // If server has not been verified - (Fake Server) - Terminate program
                    System.out.println("Server Unverified");
                    System.exit(0);
                }
            } else if (clientLoggedIn instanceof Seller) {
                // If the server signature is verified using server public key - Then server is verified (3 - Step)
                if (SignatureManager.verify(clientChallenge, verifyClientResponse, FileManager.getPublicKeyFromFile("ServerPublicKey"))) {
                    System.out.println("Server Verified");

                    // Server Generates challenge for Client
                    String serverChallenge = server.generateClientChallenge();
                    // Client signs the challenge with their private key
                    String clientSignature = SignatureManager.sign(serverChallenge, clientLoggedIn.getClientPrivateKey());
                    // Client signature is sent back to server to be verified (5-step)
                    if (server.verifyClientResponse(serverChallenge, clientSignature, clientLoggedIn.getClientId())) {
                        System.out.println("Client Verified");
                        System.out.println("Successfully Logged In Seller: " + server.getClient(clientEmail).getClientName());
                        // Log In Client
                        SellerControl.optionManager((Seller) clientLoggedIn, server);
                    }
                } else {
                    System.out.println("Server Unverified");
                    System.exit(0);
                }


            }
        } else {
            System.out.println("Entered Email does not exist. Please retry or register.");
            optionControl(clientType, server);
        }
    }

    /**
     * Returns a list of client details entered by the user
     * @param clientID
     * @param clientType
     * @param clientName
     * @param clientEmail
     * @return
     */
    private List<String> clientInfoToLIst(int clientID, String clientType, String clientName, String clientEmail) {
        List<String> clientData = new ArrayList<>();

        clientData.add(String.valueOf(clientID));
        clientData.add(clientType);
        clientData.add(clientName);
        clientData.add(clientEmail);

        return clientData;
    }
}
