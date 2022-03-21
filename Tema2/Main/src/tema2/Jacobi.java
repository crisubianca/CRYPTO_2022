package tema2;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

public class Jacobi extends Generator {

    private final BigInteger a;

    public Jacobi() {
        this.p = generatePrime();
        this.q = generatePrime();
        this.n = this.p.multiply(this.q);
        this.a = generateA();
    }

    private BigInteger generateA() {
        return BigInteger.valueOf(System.currentTimeMillis()).mod(this.n);
    }

    public void generateSymbols(BigInteger number, FileOutputStream fileOutputStream, FileOutputStream destinationFile) {
        PrintStream results = new PrintStream(fileOutputStream);
        System.setOut(results);
        BigInteger nrOfZeros = BigInteger.valueOf(0);

        BigInteger length = BigInteger.valueOf(2).pow(20);
        for (BigInteger index = BigInteger.valueOf(0); index.compareTo(length) < 0; index = index.add(BigInteger.valueOf(1))) {
            if (computeJacobiSymbol(this.a.add(index).mod(this.n)).compareTo(BigInteger.valueOf(-1)) == 0) {
                System.out.print(0);
                nrOfZeros = nrOfZeros.add(BigInteger.valueOf(1));
            } else {
                System.out.print(1);
            }
        }
        this.numberOfZeros = nrOfZeros;
        validateResult(number, destinationFile);
    }

    public BigInteger computeJacobiSymbol(BigInteger a) {
        //For testing, on a small number:
        //this.n = BigInteger.valueOf(2468379);

        BigInteger b = a.mod(this.n);
        BigInteger c = this.n;
        BigInteger s = BigInteger.valueOf(1);

        while (b.compareTo(BigInteger.valueOf(2)) >= 0) {
            while (b.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(0)) == 0) {
                b = b.divide(BigInteger.valueOf(4));
            }
            if (b.mod(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) == 0) {
                if (c.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(3)) == 0 ||
                        c.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(4)) == 0 ||
                        c.mod(BigInteger.valueOf(8)).compareTo(BigInteger.valueOf(5)) == 0) {
                    s = s.negate();
                }
                b = b.divide(BigInteger.valueOf(2));
            }
            if (b.compareTo(BigInteger.valueOf(1)) == 0) {
                break;
            }
            if (b.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0 &&
                    c.mod(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(3)) == 0) {
                s = s.negate();
            }
            BigInteger aux = b;
            b = c.mod(b);
            c = aux;
        }
        return s.multiply(b);
    }
}
