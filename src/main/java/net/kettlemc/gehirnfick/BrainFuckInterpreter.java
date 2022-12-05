package net.kettlemc.gehirnfick;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.*;

public class BrainFuckInterpreter {

    private char[] programChars = new char[0];
    private int currentCell = 0;

    private List<Character> output = new ArrayList<>();
    private HashMap<Integer, Byte> band = new HashMap<>();
    private final HashMap<Integer, Integer> loopKeys = new HashMap<>();


    public BrainFuckInterpreter execute() {
        this.generateLoopSyntax();
        this.run();
        return this;
    }

    private boolean run() {
        int programCounter = 0;
        short current;
        while (programCounter < this.programChars.length) {
            switch (this.programChars[programCounter]) {
                case '>':
                    currentCell++;
                    break;
                case '<':
                    currentCell--;
                    break;
                case '+':
                    current = getValue();
                    this.band.put(this.currentCell, (byte) (current - 1 > 255 ? 0 : current + 1));
                    break;
                case '-':
                    current = getValue();
                    this.band.put(this.currentCell, (byte) (current - 1 < 0 ? 255 : current - 1));
                    break;
                case '.':
                    short cell = this.band.get(this.currentCell);
                    System.out.print((char) cell);
                    output.add((char) cell);
                    break;
                case ',':
                    readInput();
                    break;
                case '[':
                    if (!isSet()) {
                        programCounter = this.loopKeys.get(programCounter);
                    }
                    break;
                case ']':
                    if (isSet()) {
                        programCounter = this.loopKeys.get(programCounter);
                    }
                    break;
                default:
                    return false;
            }
            programCounter++;
        }
        System.out.println();
        return true;
    }

    /**
     * Removes all unused stuff from the program source code (whitespace, ...)
     */
    private String cleanUp(String source) {
        return source.replaceAll("\\s", "");
    }

    /**
     * Generates a map used for the loops
     * @return Whether the program has a correct loop syntax
     */
    private boolean generateLoopSyntax() {
        Stack<Integer> stack = new Stack<>();
        for (int index = 0; index < programChars.length; index++) {
            if (programChars[index] == '[') {
                stack.push(index);
            } else if (programChars[index] == ']') {
                int startIndex = stack.pop();
                this.loopKeys.put(startIndex, index);
                this.loopKeys.put(index, startIndex);
            }
        }
        return stack.isEmpty();
    }

    /**
     * Check whether the value of the current field is not 0
     * @return Whether the value of the current field is not 0
     */
    private boolean isSet() {
        return this.band.containsKey(this.currentCell) && this.band.get(this.currentCell) != 0;
    }

    /**
     * Check whether the value of the current field is not 0
     * @return Whether the value of the current field is not 0
     */
    private byte getValue() {
        return isSet() ? this.band.get(this.currentCell) : 0;
    }

    /**
     * Reads input from the user and puts the first char into the current field
     */
    private void readInput() {
        // TODO
    }

    /**
     * Sets the program to be executed
     * @param program The program source code
     * @return The current instance
     */
    public BrainFuckInterpreter setProgram(String program) {
        this.programChars = cleanUp(program).toCharArray();
        return this;
    }

    /**
     * Fills the band with a default string that gets converted into their ASCII number
     * @param defaultBand The band as a String
     * @return The current instance
     */
    public BrainFuckInterpreter setDefaultBand(String defaultBand) {
        char[] defaultBandArray = defaultBand.toCharArray();
        for (int cell = 0; cell < defaultBandArray.length; cell++) {
            band.put(cell, (byte) defaultBandArray[cell]);
        }
        return this;
    }

    /**
     * The same output that would be printed to the console as a String.
     * @return The generated output by the program.
     */
    public String getOutput() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : output) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    /**
     * Converts the band to a string. Fields are divided by '|'
     * @return The current band as a String
     */
    public String getBandAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        int min = Collections.min(band.keySet());
        int max = Collections.max(band.keySet());
        for (int i = min; i <= max; i++) {
            short value = this.band.containsKey(i) ? this.band.get(i) : 0;
            stringBuilder.append(value);
            stringBuilder.append('|');
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

}
