package DigitRandom;

public class DigitUtils {
    public static int maxDigit(int n) {
        int a = n / 100;        // сотни
        int b = (n / 10) % 10;  // десятки
        int c = n % 10;         // единицы
        return Math.max(a, Math.max(b, c));
    }
}
