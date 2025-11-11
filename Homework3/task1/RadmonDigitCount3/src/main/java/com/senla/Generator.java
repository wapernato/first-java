

public class Generator {
    public static void main(String[] args) {
        int n = DigitRandom.RandomThreeDigitGenerator.generate(); // 100..999
        int maxDigit = DigitRandom.DigitUtils.maxDigit(n);

        System.out.println("Число: " + n);
        System.out.println("Наибольшая цифра: " + maxDigit);
    }
}
