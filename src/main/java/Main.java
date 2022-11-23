import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger threeCharsCount = new AtomicInteger(0);
        AtomicInteger fourCharsCount = new AtomicInteger(0);
        AtomicInteger fiveCharsCount = new AtomicInteger(0);

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
            //System.out.println(texts[i]);
        }

        System.out.println("------------------------------------------");

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                    if (checkForSameChars(texts[i])) {
                        //System.out.println("checkForSameChars - " + texts[i]);
                        if (texts[i].length() == 3) threeCharsCount.getAndIncrement();
                        if (texts[i].length() == 4) fourCharsCount.getAndIncrement();
                        if (texts[i].length() == 5) fiveCharsCount.getAndIncrement();
                    }
            }

        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (checkForPalindrome(texts[i])) {
                    //System.out.println("checkForPalindrome - " + texts[i]);
                    if (texts[i].length() == 3) threeCharsCount.getAndIncrement();
                    if (texts[i].length() == 4) fourCharsCount.getAndIncrement();
                    if (texts[i].length() == 5) fiveCharsCount.getAndIncrement();
                }
            }

        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (checkForCharsSequence(texts[i])) {
                    //System.out.println("checkForCharsSequence - " + texts[i]);
                    if (texts[i].length() == 3) threeCharsCount.getAndIncrement();
                    if (texts[i].length() == 4) fourCharsCount.getAndIncrement();
                    if (texts[i].length() == 5) fiveCharsCount.getAndIncrement();
                }
            }

        });


        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("------------------------------------------");
        System.out.println("threeCharsCount = " + threeCharsCount);
        System.out.println("fourCharsCount = " + fourCharsCount);
        System.out.println("fiveCharsCount = " + fiveCharsCount);

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean checkForPalindrome(String string) {

        String rev = "";

        for (int i = string.length() - 1; i >= 0; i--) {
            rev = rev + string.charAt(i);
        }

        return string.equals(rev);
    }

    public static boolean checkForCharsSequence(String string) {

        char[] charset = string.toCharArray();

        for (int i = 0; i < string.length() - 1; i++) {
            if (charset[i] > charset[i + 1]) return false;
        }
        return true;
    }

    public static boolean checkForSameChars(String string) {

        char[] charset = string.toCharArray();

        for (int i = 1; i < charset.length; i++) {
            if (charset[i] != charset[0]) return false;
        }
        return true;
    }

}
