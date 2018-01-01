package q3;

import java.util.Scanner;

/**
 * Encodes and decodes user input.
 *
 * @author David Lu
 * @version 1.0
 */
public final class MIXChar {
    /**An array that holds mixchar values.*/
    private static MIXChar[] mix;
    
    /**Holds the delta character.*/
    private static final char DELTA = '\u0394';
    
    /**Holds the sigma character.*/
    private static final char SIGMA = '\u03A3';
    
    /**Holds the pi character.*/
    private static final char PI = '\u03A0';
    
    /**An array that contains all possible mixchar values.*/
    private static char[] possible = {' ', 'A', 'B', 'C', 'D', 
            'E', 'F', 'G', 'H', 'I', DELTA, 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', SIGMA, PI, 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '.', ',', '(', ')', '+', '-', '*', '/', '=', '$', '<',
            '>', '@', ';', ':', '\''};
    
    
    /**Stores the mixchar value.*/
    private int value;
    
    
    
    
    /**
     * Constructs an object of type MIXChar.
     * @param value an integer
     */
    private MIXChar(int value) {
        this.value = value;
    }
    
    /**
     * Checks to see if a character is a mixchar.
     * @param c a character
     * @return a boolean
     */
    static boolean isMIXChar(char c) {
        for (char myChar: possible) {
            if (myChar == c) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Converts c to a MIXChar.
     * @param c a char
     * @return a MIXChar
     */
    static MIXChar toMIXChar(char c) {
        if (isMIXChar(c)) {
            for (int i = 0; i < possible.length; i++) {
                if (possible[i] == c) {
                    MIXChar temp = new MIXChar(i);
                    return temp;
                } 
            }
            throw new IllegalArgumentException("Not a valid MIX Char!");
        } else {
            throw new IllegalArgumentException("Not a valid MIX Char!");
        }
    }

    /**
     * Converts a MIXChar to a java character.
     * @param x a MIXChar
     * @return a char
     */
    static char toChar(MIXChar x) {
        char temp;
        temp = possible[x.ordinal()];
        return temp;
    }
    
    /**
     * Overrides the toString method to print the characters.
     * @param x a MIXChar
     * @return a string
     */
    static String toString(MIXChar[] x) {
        String result = "";
        
        for (MIXChar mixChar : x) {
            result += toChar(mixChar);
        }
        return result;
    }
    
    /**
     * Converts a string to mixchar values.
     * @param e a string
     * @return a MIXChar
     */
    static MIXChar[] toMIXChar(String e) {
        mix = new MIXChar[e.length()];
        for (int i = 0; i < e.length(); i++) {
            if (isMIXChar(e.charAt(i))) {
                mix[i] = toMIXChar(e.charAt(i));
            } else {
                throw new IllegalArgumentException("Not a valid MIX char!");
            }
        }
        return mix;
    }
    
    /**
     * Encodes the mix chars into a long number.
     *
     * @param m a MIXChar
     * @return a long array
     */
    static long[] encode(MIXChar[] m) {
        final double capacity = 11.0;
        final int full = 11;
        final int max = 56;
        long[] encode = new long[(int) (Math.ceil(mix.length / capacity))];
        int iter = 0;
        int count = 0;
        for (MIXChar n : m) {
            encode[count] += (n.ordinal() * (long) Math.pow(max, iter));
            iter++;
            if (iter % full == 0 && iter != 0) {
                count++;
                iter = 0;
            } 
        }
        return encode;
    }
    
    /**
     * Decodes the long array into MIXChar values.
     * @param l a long array
     * @return a MIXChar array
     */
    static MIXChar[] decode(long[] l) {
        int count = 0;
        int length = 0;
        final int base = 56;
        final int remain = 55;
        final int maxLength = 11;
        MIXChar[] temp = new MIXChar[mix.length];
        for (int i = 0; i < l.length; i++) {
            long total = l[i];
            while (total > remain || (count < temp.length 
                    && length < maxLength)) {
                MIXChar x = new MIXChar((int) 
                        Long.remainderUnsigned(total, base));
                total = Long.divideUnsigned(total, base);
                temp[count] = x;
                length++;
                count++;
            }
            length = 0;
        }
        return temp;
    }

    /**
     * Returns the location of the MIXChar.
     *
     * @return an integer
     */
    int ordinal() {
        return this.value;
    }

    /**
     * Drives the program.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter some MIX characters:");
        String test = scan.nextLine();
        try {
            toMIXChar(test);
            long[] code = encode(mix);
            MIXChar[] y = decode(code);
            System.out.println("Original: " + test);
            System.out.print("Encoded: ");
            for (long myLong: code) {
                System.out.print(myLong + " ");
            }
            System.out.println("\nDecoded: " + toString(y));
        } catch (IllegalArgumentException ex) {
            System.out.println("Not a valid MIX Char!");
        }
        scan.close();
    }

}
