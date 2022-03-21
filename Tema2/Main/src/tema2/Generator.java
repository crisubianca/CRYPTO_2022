package tema2;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public abstract class Generator {

    protected BigInteger p;
    protected BigInteger q;
    protected BigInteger n;
    protected BigInteger numberOfZeros;

    public Generator() {

    }

    protected BigInteger generatePrime() {
        BigInteger generatedPrime = BigInteger.probablePrime(512, new Random());
        while (generatedPrime.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) != 0) {
            generatedPrime = BigInteger.probablePrime(512, new Random());
        }
        return generatedPrime;
    }

    public void validateResult(BigInteger numberOfBits, FileOutputStream destinationFile) {
        PrintStream results = new PrintStream(destinationFile);
        BigDecimal floatNumberOfBits = new BigDecimal(numberOfBits);
        BigDecimal floatNumberOfZeros = new BigDecimal(this.numberOfZeros);
        System.setOut(results);
        BigDecimal onesPercent = (floatNumberOfBits.subtract(floatNumberOfZeros)).divide(floatNumberOfBits).multiply(BigDecimal.valueOf(100));
        BigDecimal zerosPercent = floatNumberOfZeros.divide(floatNumberOfBits).multiply(BigDecimal.valueOf(100));
        System.out.println("Percentage for zeros = " + onesPercent);
        System.out.println("Percentage for ones = " + zerosPercent);
        if (onesPercent.subtract(zerosPercent).abs().compareTo(BigDecimal.valueOf(1)) < 1) {
            System.out.println("Good results : " + onesPercent.subtract(zerosPercent).abs());
        } else {
            System.out.println("Bad results : " + onesPercent.subtract(zerosPercent).abs());
        }
    }

    public void generateOnes(BigInteger number, FileOutputStream destinationFile) throws FileNotFoundException {
        PrintStream oldOut = System.out;
        PrintStream fis = new PrintStream(destinationFile);
        System.setOut(fis);
        while (number.compareTo(BigInteger.valueOf(0)) != 0) {
            System.out.print(1);
            number = number.subtract(BigInteger.valueOf(1));
        }
        System.setOut(oldOut);
    }
}

