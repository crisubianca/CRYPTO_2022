package tema3;

import java.util.Random;

public class RC4 {
    private String key;
    private String plainText;
    private String encryptDecryptResult;
    private int keyBytes;
    private int[] initialState;
    private int iState;
    private int jState;
    private int[] currentState;
    private int[] keyByte;

    public RC4(String key, int keyBytes) {
        if (key.compareTo("") == 0) {
            throw new IllegalArgumentException("Key must be non-empty!");
        }
        if (keyBytes != key.length()) {
            throw new IllegalArgumentException("Key length doesn't match the number of bytes!");
        }
        this.key = fromStrToBinaryByte(key);
        this.keyBytes = keyBytes;

        currentState = new int[256];
    }

    public int getKeyBytes() {
        return keyBytes;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String fromStrToBinaryByte(String str) {
        String binByte = "";
        for (int index = 0; index < str.length(); index++) {
            binByte = binByte + toBinaryByte(str.charAt(index));
        }
        return binByte;
    }

    public int[] init() {
        int[] s0 = new int[256];
        int j = 0;
        for (int i = 0; i < 256; i++) {
            s0[i] = i;
        }
        for (int i = 0; i < 256; i++) {
            j = (j + s0[i] + (int) key.charAt(i % keyBytes)) % 256;
            int aux = s0[i];
            s0[i] = s0[j];
            s0[j] = aux;
        }
        return s0;
    }

    public int trans(int i, int j, int[] state0) {
        i = (i + 1) % 256;
        j = (j + state0[i]) % 256;
        //swap
        int aux = state0[i];
        state0[i] = state0[j];
        state0[j] = aux;
        //
        //keeping state internal
        iState = i;
        jState = j;
        this.currentState = state0;
        //
        return state0[(state0[i] + state0[j]) % 256];
    }

    public String toBinaryByte(int number) {
        String binaryResult = "";
        while (number > 0) {
            binaryResult = binaryResult + number % 2;
            number = number / 2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(binaryResult);
        binaryResult = String.valueOf(stringBuilder.reverse());
        if (binaryResult.length() < 8) {
            String newByteString = "";
            for (int index = 0; index < 8 - binaryResult.length(); index++) {
                newByteString = newByteString + '0';
            }
            binaryResult = newByteString + binaryResult;
        }
        return binaryResult;
    }

    public String xorString(String s1, String s2) {
        String result = "";
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                result = result + '1';
            } else {
                result = result + '0';
            }
        }
        return result;
    }

    public void generateKeys() {
        this.initialState = init();
        int textLength = this.plainText.length();
        keyByte = new int[textLength];
        this.iState = 0;
        this.jState = 0;
        this.currentState = this.initialState;
        int index = 0;
        while (textLength > 0) {
            keyByte[index] = trans(iState, jState, this.currentState);
            index++;
            textLength--;
        }
    }

    public void encryptDecrypt(String text) {
        generateKeys();
        encryptDecryptResult = "";
        for (int index = 0; index < text.length(); index++) {
            encryptDecryptResult = encryptDecryptResult + ((char) Integer.parseInt(xorString(toBinaryByte(text.charAt(index)),
                    toBinaryByte(keyByte[index])), 2));
        }
        System.out.println(encryptDecryptResult);
    }

    public void checkBiases(int rounds) {
        //0, 8, 9, 10, 13, 32, 160
        Random random = new Random();
        int[] count = new int[keyByte.length];
        for (int index = 0; index < rounds; index++) {
            String newRandKey = "";
            for (int indexLength = 0; indexLength < this.keyByte.length; indexLength++) {
                char letter = (char) random.nextInt(256);
                while (!(letter != 0 && letter != 8 && letter != 9
                        && letter != 10 && letter != 13
                        && letter != 32 && letter != 160)) {
                    letter = (char) random.nextInt(256);
                }
                newRandKey = newRandKey + letter;
            }
            this.key = newRandKey;
            init();
            generateKeys();
            for (int i = 0; i < keyByte.length; i++) {
                if (keyByte[i] == 0) {
                    count[i]++;
                }
            }
        }
        double probability = (double) 1 / 128;
        System.out.println("\nExpected ideal probability : " + probability);
        for (int i = 0; i < keyByte.length; i++) {
            double result = (double) count[i] / rounds;
            if (i != 1) {
                System.out.println("Probability for " + i + " byte is : "
                        + result);
            }
            else{
                System.out.println("  << Our targeted probability is : "
                        + result);
            }
        }
    }

    public String getEncryptDecryptResult() {
        return encryptDecryptResult;
    }
}
