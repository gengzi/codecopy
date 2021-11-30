package fun.gengzi.codecopy.utils;

public class BaseConversionUtils {

    static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String to62RadixString(long seq) {
        StringBuilder sBuilder = new StringBuilder();
        while (true) {
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        return sBuilder.reverse().toString();
    }

    public static long radixString(String str) {
        long sum = 0L;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            sum += indexDigits(str.charAt(len - i - 1)) * Math.pow((double) 62, (double) i);

        }
        return sum;
    }

    private static int indexDigits(char ch) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (ch == DIGITS[i]) {
                return i;
            }
        }
        return -1;
    }

}
