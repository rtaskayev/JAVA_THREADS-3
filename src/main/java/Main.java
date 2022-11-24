import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        Map<Integer, Integer> map1 = new ConcurrentHashMap<>();
        Map<Integer, Integer> map2 = Collections.synchronizedMap(new HashMap<>());


        for (int i = 0; i < 1000; i++) {
            map1.put(i, random.nextInt());
        }

        for (int i = 0; i < 1000; i++) {
            map2.put(i, random.nextInt());
        }

        Thread thread1 = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                map1.put(i, i);
                //System.out.println("ДОБАВИЛИ " + i);
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 300; i < 1300; i++) {
                map1.get(i);
                //System.out.println("ВЗЯЛИ " + map.get(i));
            }
        });

        Thread thread4 = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                map2.put(i, i);
                //System.out.println("ДОБАВИЛИ " + i);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 300; i < 1300; i++) {
                map2.get(i);
                //System.out.println("ВЗЯЛИ " + map.get(i));
            }
        });

        long startTime1 = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        long stopTime1 = System.currentTimeMillis();

        long startTime2 = System.currentTimeMillis();
        thread3.start();
        thread4.start();
        thread3.join();
        thread4.join();
        long stopTime2 = System.currentTimeMillis();

        long elapsedTime1 = stopTime1 - startTime1;
        long elapsedTime2 = stopTime2 - startTime2;
        System.out.println("Time taken for ConcurrentHashMap = " + elapsedTime1);
        System.out.println("Time taken for synchronizedMap = " + elapsedTime2);
    }
}