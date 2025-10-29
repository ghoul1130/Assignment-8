import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncTaskProcessor {
    public void processTasksAsync() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int id = i;
            CompletableFuture<Void> cf = CompletableFuture
                    .supplyAsync(() -> {
                        System.out.println("Task " + id + " started on " + Thread.currentThread().getName());
                        try {
                            TimeUnit.SECONDS.sleep((long) (Math.random() * 3) + 1);
                            if (id == 2 || id == 4) throw new RuntimeException("Simulated failure for task " + id);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        return "Result_" + id;
                    }, executor)
                    .thenApply(res -> res + "_processed")
                    .exceptionally(ex -> "Handled_" + ex.getMessage())
                    .thenAccept(result -> System.out.println("Task " + id + " => " + result));

            futures.add(cf);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        System.out.println("Async tasks completed.\n");
    }
}
