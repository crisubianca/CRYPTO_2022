package tema2;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        PrintStream oldOut = System.out;
        BlumBlumShub blumBlumShub = new BlumBlumShub();
        long initTimeBBS = System.currentTimeMillis();
        blumBlumShub.generateOnes(BigInteger.valueOf(1024 * 1024), new FileOutputStream("ones.txt"));
        initTimeBBS = System.currentTimeMillis() - initTimeBBS;

        blumBlumShub.generateOutput(BigInteger.valueOf(1024 * 1024), new FileOutputStream("bbs.txt"), new FileOutputStream("resultsBBS.txt"));
        long finalTimeBBS = System.currentTimeMillis() - initTimeBBS;

        HandleFiles handleFiles = new HandleFiles();
        handleFiles.zipFile("bbs.txt", new FileOutputStream("bbs.zip"));
        handleFiles.zipFile("ones.txt", new FileOutputStream("ones.zip"));

        long initTimeJacobi = System.currentTimeMillis() - finalTimeBBS;
        Jacobi jacobi = new Jacobi();
        jacobi.generateSymbols(BigInteger.valueOf(1024 * 1024), new FileOutputStream("Jacobi.txt"), new FileOutputStream("resultsJacobi.txt"));
        long finalTimeJacobi = System.currentTimeMillis() - initTimeJacobi;

        handleFiles.zipFile("Jacobi.txt", new FileOutputStream("Jacobi.zip"));
        System.setOut(oldOut);

        System.out.println("Time for BBS : " + finalTimeBBS);
        System.out.println("Time for Jacobi : " + finalTimeJacobi);
        System.out.println("Time difference : " + Math.abs(finalTimeBBS - finalTimeJacobi));
        System.out.println();

        System.out.println(handleFiles.sizeToString("Jacobi.zip", handleFiles.getSize("Jacobi.zip")));
        System.out.println(handleFiles.sizeToString("bbs.zip", handleFiles.getSize("bbs.zip")));
        System.out.println(handleFiles.sizeToString("ones.zip", handleFiles.getSize("ones.zip")));
    }
}
