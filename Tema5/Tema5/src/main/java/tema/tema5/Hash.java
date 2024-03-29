package tema.tema5;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Adm
 */

public class Hash {
    
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
	{
		// static getInstance method is called with hashing SHA
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		// digest() method called
		// to calculate message digest of an input
		// and return array of byte
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
    
    public static String toHexString(byte[] hash)
	{
		// convert byte array into signum representation
		BigInteger number = new BigInteger(1, hash);

		// convert message digest into hex value
		StringBuilder hexString = new StringBuilder(number.toString(16));

		// pad with leading zeros
		while (hexString.length() < 64)
		{
		    hexString.insert(0, '0');
		}

		return hexString.toString();
	}
    
    public static void main(String args[])
    {
	try
	{
	    System.out.println("HashCode Generated by SHA-256 for:");

	    String s1 = "ABC";
	    System.out.println("\n" + "'" + s1 + "'" + " is : " + toHexString(getSHA(s1)));
            
            String[] result1;
            result1 = toHexString(getSHA(s1)).split("");

	    String s2 = "BBC";
	    System.out.println("\n" + "'" + s2 + "'" + " is : " + toHexString(getSHA(s2)));
            
            String[] result2;
            result2 = toHexString(getSHA(s2)).split("");
            
            int k = 0;
            
            for(int i=0; i< result1.length; i++){
                if(result1[i] != result2[i]) k++;
            }
            System.out.println("\n" + "The number of different bits is : " + k);
            
            //System.out.println("The hamming distance for 'ABC' and 'BBC' is : " + hammingDist(s1, s2));
			
	    String s3 = "testing program";
	    System.out.println("\n" + "'" + s3 + "'" + " is : " + toHexString(getSHA(s3)));
            
            String s4 = "kL123Vuy5";
	    System.out.println("\n" + "'" + s4 + "'" + " is : " + toHexString(getSHA(s4)));
            
            //i checked here the strings from above: https://emn178.github.io/online-tools/sha256.html
	}
	// for specifying wrong message digest algorithms
	catch (NoSuchAlgorithmException e) {
	    System.out.println("Exception thrown for incorrect algorithm: " + e);
	}
    }
    
    static int hammingDist(String str1, String str2)
    {
        int i = 0, count = 0;
        while (i < str1.length())
        {
            if (str1.charAt(i) != str2.charAt(i)) count++;
            i++;
        }
        return count;
    }    
}

