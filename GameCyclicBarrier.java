import java.util.concurrent.*;

public class GameCyclicBarrier {
    public void runGame() {
        CyclicBarrier barrier = new CyclicBarrier(3, () ->
                System.out.println("ALL PLAYERS READY! Game starting..."));

        ExecutorService exec = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 3; i++) {
            int player = i;
            exec.submit(() -> {
                try {
                    System.out.println("Player " + player + " loading game...");
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 3) + 1);
                    System.out.println("Player " + player + " LOADED, waiting...");
                    barrier.await();

                    System.out.println("Player " + player + " playing Round 1...");
                    TimeUnit.MILLISECONDS.sleep(1500);
                    System.out.println("Player " + player + " finished Round 1, waiting...");
                    barrier.await();

                    System.out.println("Player " + player + " playing Round 2...");
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("Player " + player + " completed!");
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
            try {
                TimeUnit.MILLISECONDS.sleep(300);
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
        System.out.println("Game completed.\n");
    }
}