package tema3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Adm
 */
public class Tema3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
                int degree = 20;
        boolean[] onOff = new boolean[degree];
        for (int index = 0; index < degree; index++) {
            if (index == 2 || index == 19) {
                onOff[index] = true;
            } else {
                onOff[index] = false;
            }
        }
        LinearFeedbackShiftRegister lfsr = new LinearFeedbackShiftRegister(
                20,
                onOff,
                new FileOutputStream("LFSR.txt")
        );

        Long startingTime = System.currentTimeMillis();

        lfsr.generateOutput();

        Long finalTime = System.currentTimeMillis() - startingTime;
        String bbsTime = "1618764591866";
        String JacobiTime = "1618764599002";
        System.out.println("Time for BBS : " + bbsTime +
                "\nTime for Jacobi : " + JacobiTime);
        System.out.println("LFSR time : " + finalTime);

        System.out.println("Time difference between LFSR and the two, on average : "
                + ((Long.valueOf(bbsTime) - finalTime)
                + (Long.valueOf(JacobiTime) - finalTime))
                / 2);

        System.out.println();
        HandleFiles handleFiles = new HandleFiles();
        handleFiles.zipFile("LFSR.txt", new FileOutputStream("LFSR.zip"));
        System.out.println(
                "167043 bytes for Jacobi.zip\n" +
                "166853 bytes for bbs.zip\n" +
                handleFiles.getSize("LFSR.zip") + " bytes for LFSR.zip\n" +
                        "1164 bytes for ones.zip\n");

        RC4 rc4 = new RC4("!bcdefgh", 8);
        String plainText = "aplaintextrighthere";
        System.out.println("Plaint text is : " + plainText);
        rc4.setPlainText(plainText);
        System.out.print("Cypher text is : ");
        rc4.encryptDecrypt(plainText);
        System.out.print("Decrypted text is : ");
        rc4.encryptDecrypt(rc4.getEncryptDecryptResult());
        rc4.checkBiases(50000);
    }
    
}
