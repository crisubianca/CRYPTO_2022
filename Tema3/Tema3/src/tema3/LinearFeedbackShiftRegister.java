package tema3;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

public class LinearFeedbackShiftRegister {
    private final String initialState;
    private final Integer degree;
    private final boolean[] onOffBit;
    private final FileOutputStream outputStream;
    private String[] states;
    private Integer numberOfZeros;

    public LinearFeedbackShiftRegister(Integer degree, boolean[] onOffBit, FileOutputStream outputStream) {
        this.initialState = generateInitialState(degree);
        this.degree = degree;
        this.onOffBit = onOffBit;
        this.outputStream = outputStream;
        this.numberOfZeros = 0;

        states = new String[(int) Math.pow(2, degree)];
    }

    public String generateInitialState(Integer length) {
        String state = "";
        Random random = new Random();
        for (int index = 0; index < length; index++) {
            state = state + random.nextInt(2);
            if (index + 1 == length) {
                if (!isStateNonZero(state)) {
                    index = 0;
                    state = "";
                }
            }
        }
        return state;
    }

    public boolean isStateNonZero(String state) {
        for (int index = 0; index < state.length(); index++) {
            if (state.charAt(index) != '0') {
                return true;
            }
        }
        return false;
    }

    public String shiftRight(String input, int numberOfShifts) {
        String shiftedInput = "";
        for (int i = 0; i < numberOfShifts; i++) {
            shiftedInput = '0' + input.substring(0, input.length() - 1);
            input = shiftedInput;
        }
        return shiftedInput;
    }

    public String feedbackFunction(String previousState) {
        String state = shiftRight(previousState, 1);
        int sum = 0;
        for (int index = 0; index < degree; index++) {
            if (onOffBit[index]) {
                sum = sum + (int) previousState.charAt(index);
            }
        }
        sum = sum % 2;
        state = sum + state.substring(1);
        return state;
    }

    public void generateOutput(){
        PrintStream oldOut = System.out;
        PrintStream results = new PrintStream(this.outputStream);
        System.setOut(results);

        String previousState = initialState;
        for (int index = 0; index < (int) Math.pow(2, degree); index++) {
            states[index] = feedbackFunction(previousState);
            previousState = states[index];
            results.print(states[index].charAt(degree - 1));
            if(states[index].charAt(degree - 1) == '0'){
                numberOfZeros++;
            }
        }

        System.setOut(oldOut);
        validateResult((int) Math.pow(2, degree));
    }

    public void validateResult(int numberOfBits) {
        double floatNumberOfBits = numberOfBits;
        double floatNumberOfZeros = this.numberOfZeros;
        double onesPercent = (floatNumberOfBits-(floatNumberOfZeros))/(floatNumberOfBits)*100;
        double zerosPercent = floatNumberOfZeros/(floatNumberOfBits)*100;
        System.out.println("Percentage for zeros = " + onesPercent);
        System.out.println("Percentage for ones = " + zerosPercent);
        System.out.println();
    }

}

