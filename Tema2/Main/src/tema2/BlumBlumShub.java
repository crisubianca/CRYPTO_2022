package tema2;


import java.io.*;
import java.math.BigInteger;

public class BlumBlumShub extends Generator {

    private BigInteger x0;

    public BlumBlumShub() {
        this.p = generatePrime();
        this.q = generatePrime();
        this.n = this.p.multiply(this.q);
        this.x0 = BigInteger.valueOf(System.currentTimeMillis()).modPow(BigInteger.valueOf(2), this.n);
    }

    public void generateOutput(BigInteger number, FileOutputStream fileOutputStream, FileOutputStream destinationFile) throws IOException {
        PrintStream fis = new PrintStream(fileOutputStream);
        System.setOut(fis);
        System.out.print(this.x0.mod(BigInteger.valueOf(2)));
        BigInteger currentX;
        BigInteger nr = BigInteger.valueOf(0);
        if(this.x0.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
            nr = nr.add(BigInteger.valueOf(1));
        }
        BigInteger numberDup = number;
        BigInteger remainder;
        while (numberDup.compareTo(BigInteger.valueOf(1)) != 0) {
            currentX = x0.modPow(BigInteger.valueOf(2), this.n);
            x0 = currentX;
            numberDup = numberDup.subtract(BigInteger.valueOf(1));
            remainder = currentX.mod(BigInteger.valueOf(2));
            System.out.print(remainder);
            if (remainder.compareTo(BigInteger.valueOf(0)) == 0) {
                nr = nr.add(BigInteger.valueOf(1));
            }
        }
        this.numberOfZeros = nr;
        fileOutputStream.close();
        validateResult(number, destinationFile);
    }

}
