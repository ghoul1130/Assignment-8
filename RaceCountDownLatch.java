import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RaceCountDownLatch {
    public void runRace() {
        CountDownLatch latch = new CountDownLatch(4);
        ExecutorService exec = Executors.newFixedThreadPool(5);

        // Organizer thread
        exec.submit(() -> {
            System.out.println("Race started! Waiting for runners...");
            try {
                latch.await();
                System.out.println("ALL RUNNERS FINISHED! Race complete!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        for (int i = 1; i <= 4; i++) {
            int runner = i;
            exec.submit(() -> {
                System.out.println("Runner " + runner + " running...");
                try {
                    TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 3000 + 1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Runner " + runner + " FINISHED! (Remaining: " + (latch.getCount() - 1) + ")");
                latch.countDown();
            });
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        exec.shutdown();
        try {
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Race completed.\n");
    }
}