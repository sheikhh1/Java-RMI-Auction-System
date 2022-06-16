import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Digitally signs and verifies a signature with some randomly generated string data
 *  - Sign - returns a signature
 *  - Verify - returns a boolean to see if the signature is correct using public keys
 */
public class SignatureManager {

    /**
     * Creates a digital signature using string data
     * @param plainText Generated Challenge
     * @param privateKey Private key to sign signature with
     * @return Signature
     * @throws Exception
     */
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    /**
     * Verifies the signature
     * @param plainText Generated Challenge
     * @param signature Signature
     * @param publicKey Public key to verify signature
     * @return True if verification is successful, else false
     * @throws Exception
     */
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
}
