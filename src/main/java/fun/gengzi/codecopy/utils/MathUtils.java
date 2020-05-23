package fun.gengzi.codecopy.utils;

import java.util.Random;

public class MathUtils {
    private static Random random = new Random();

    public MathUtils() {
    }

    public static int getValue(int step) {
        return random.nextInt(step);
    }

    public static int positive(int number) {
        number = number < 0 ? number * -1 : number;
        return number;
    }

    public static void main(String[] args) {
        int d = -1251880588;
        d = positive(d);
        System.out.println(d);
    }
}
