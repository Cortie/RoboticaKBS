import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

public class SaltedHashPasword {
    // public static void main(String[] args) throws NoSuchAlgorithmException,
    // NoSuchProviderException
    // {
    // String passwordToHash = "password";
    // byte[] salt = getSalt();
    //
    //
    // String securePassword = getSecurePassword(passwordToHash, salt);
    // System.out.println(securePassword); //Prints 83ee5baeea20b6c21635e4ea67847f66
    //
    // String regeneratedPassowrdToVerify = getSecurePassword(passwordToHash, salt);
    // System.out.println(regeneratedPassowrdToVerify); //Prints
    // 83ee5baeea20b6c21635e4ea67847f66
    // }

    public static String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-256");// SHA-512
            // Add password bytes to digest
            md.update(salt);
            // Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    // Add salt
    public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        // Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        // Create array for salt
        byte[] salt = new byte[16];
        // Get a random salt
        sr.nextBytes(salt);
        return salt;

        // stuk voor niet random salt
        // byte[] arraysalt ={-75, 41, 78, 110, 64, 113, -101, -26, 100, 64, 38, 61, 91,
        // 59, 74, -93};
        // byte[] standardSalt;
        // standardSalt = arraysalt;
        // return standardSalt;
        // //d2f056bed7ded0adf19655fb65a8a11d8441f077
        // //d2f056bed7ded0adf19655fb65a8a11d8441f077
    }
}
