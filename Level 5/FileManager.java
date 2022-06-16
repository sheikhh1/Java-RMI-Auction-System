import java.io.*;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all operations from reading and writing to text files stored locally
 * - Reads and Writes clients to a clientData text file
 * - Writes Client and Server public keys to text files
 * - Reads public keys from those text files
 */
public class FileManager {

    /**
     * Writes client data to text file
     * @param filePath path of file
     * @param clientData data to be added to file
     * @throws IOException
     */
    public static void writeClientToFile(String filePath, List<String> clientData) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath + ".txt", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(String.join(",", clientData));
        bufferedWriter.newLine();
        bufferedWriter.flush();
        System.out.println("Client Data Written To File");
    }

    /**
     * Reads client data from file
     * @param filePath Path of file
     * @return Concurrent hashmap is returned with built client objects
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static ConcurrentHashMap<Integer, Client> readClientsFromFile(String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        ConcurrentHashMap<Integer, Client> readClientsToMap = new ConcurrentHashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath + ".txt"));


        String readLine = "";
        while((readLine = bufferedReader.readLine()) != null) {
            Client newClient = null;
            String[] clientInfoSplit = readLine.split(",");

            if (clientInfoSplit[1].equals("BUYER")) {
                newClient = new Buyer(Integer.parseInt(clientInfoSplit[0]), "BUYER", clientInfoSplit[2], clientInfoSplit[3]);
            } else if (clientInfoSplit[1].equals("SELLER")) {
                newClient = new Seller(Integer.parseInt(clientInfoSplit[0]), "SELLER", clientInfoSplit[2], clientInfoSplit[3]);
            }

            assert newClient != null;
            readClientsToMap.put(Integer.parseInt(clientInfoSplit[0]), newClient);
            writePublicKeyToFile(String.valueOf(newClient.getClientId()), newClient.getClientPublicKey());

            if (newClient.getClientPublicKey().equals(getPublicKeyFromFile(String.valueOf(newClient.getClientId())))) {
                System.out.println("Public Key Loaded " + newClient.getClientId());
            }
        }

        return readClientsToMap;
    }

    /**
     * Writes a public key to a unique text file based on ID
     * @param filename path of file
     * @param clientPublicKey public key to be written
     * @throws IOException
     */
    public static void writePublicKeyToFile(String filename, PublicKey clientPublicKey) throws IOException {
        FileOutputStream fileWriter = new FileOutputStream(filename + ".txt");

        fileWriter.write(clientPublicKey.getEncoded());
        fileWriter.close();

        System.out.println("Client Data Written To File " + filename);
    }

    /**
     * Reads public key from file
     * @param fileName path of file
     * @return Reads key from file
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKeyFromFile(String fileName) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicKeyFile = new File(fileName + ".txt");
        byte[] publicKey = Files.readAllBytes(publicKeyFile.toPath());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey);

        return keyFactory.generatePublic(publicKeySpec);
    }
}
