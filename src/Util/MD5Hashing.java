package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  MD5 hasher class.
 */
public class MD5Hashing {

    /**
     * Hash a String with MD5 algorithm.
     * @param str The string to hash.
     * @return The hashed string.
     */
    static public String hash(String str) {

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(str.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
