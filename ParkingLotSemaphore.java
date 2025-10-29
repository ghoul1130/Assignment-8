import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ParkingLotSemaphore {
    public void runParkingLot() {
        Semaphore parking = new Semaphore(3);
        ExecutorService exec = Executors.newFixedThreadPool(6);

        for (int i = 1; i <= 6; i++) {
            int car = i;
            exec.submit(() -> {
                System.out.println("Car " + car + " arriving...");
                try {
                    parking.acquire();
                    System.out.println("Car " + car + " PARKED (spaces left: " + parking.availablePermits() + ")");
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    parking.release();
                    System.out.println("Car " + car + " LEFT (spaces now: " + parking.availablePermits() + ")");
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
        System.out.println("Parking Lot simulation completed.\n");
    }
}