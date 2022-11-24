import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {

        BlockingQueue<Request> requests = new ArrayBlockingQueue<>(1000);
        final int timeToProcessRequest = 100;
        final int numberOfRequestsForSpecialistToProcess = 100;
        final int timeForRequestsGenerationHold = 1000;
        final int numberOfRequests = 60;
        final int numberOfIterations = 5;

        // Thread to simulate first specialist
        new Thread(() -> {
            for (int i = 0; i < numberOfRequestsForSpecialistToProcess; i++) {
                Request request = requests.poll();
                if (request == null) {
                    System.out.println("Queue is empty operator1");
                }
                else {
                    try {
                        Thread.sleep(timeToProcessRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i--;
                    System.out.println("Processed request operator1");
                }
            }
        }).start();


        // Thread to simulate second specialist
        new Thread(() -> {
            for (int i = 0; i < numberOfRequestsForSpecialistToProcess; i++) {
                Request request = requests.poll();
                if (request == null) {
                    System.out.println("Queue is empty operator2");
                }
                else {
                    try {
                        Thread.sleep(timeToProcessRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Processed request operator2");
                    i--;
                }
            }
        }).start();


        // Thread to simulate third specialist
        new Thread(() -> {
            for (int i = 0; i < numberOfRequestsForSpecialistToProcess; i++) {
                Request request = requests.poll();
                if (request == null) {
                    System.out.println("Queue is empty operator3");
                }
                else {
                    try {
                        Thread.sleep(timeToProcessRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Processed request operator3");
                    i--;
                }
            }
        }).start();


        // Thread to simulate requests
        new Thread(() -> {
            for (int i = 0; i < numberOfIterations; i++) {
                for (int j = 0; j < numberOfRequests; j++) {
                    requests.add(new Request());
                }
                System.out.println("Generated 60 requests");
                try {
                    Thread.sleep(timeForRequestsGenerationHold);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}