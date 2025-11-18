package DigitRandom;

public class RandomThreeDigitGenerator {
    public static int generate() {
        return (new java.util.Random()).nextInt(900) + 100; // 100..999
    }
}
