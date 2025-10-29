import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n=== ASYNCHRONOUS TASK PROCESSOR ===");
        AsyncTaskProcessor asyncTaskProcessor =  new AsyncTaskProcessor();
        asyncTaskProcessor.processTasksAsync();

        System.out.println("\n=== PRODUCER-CONSUMER (ReentrantLock + Condition) ===");
        ProducerConsumerReentrant producerConsumerReentrant = new ProducerConsumerReentrant();
        producerConsumerReentrant.runProducerConsumer();

        System.out.println("\n=== PARKING LOT (Semaphore) ===");
        ParkingLotSemaphore parkingLotSemaphore = new ParkingLotSemaphore();
        parkingLotSemaphore.runParkingLot();

        System.out.println("\n=== RACE COORDINATION (CountDownLatch) ===");
        RaceCountDownLatch raceCountDownLatch = new RaceCountDownLatch();
        raceCountDownLatch.runRace();

        System.out.println("\n=== GAME SYNCHRONIZATION (CyclicBarrier) ===");
        GameCyclicBarrier gameCyclicBarrier = new GameCyclicBarrier();
        gameCyclicBarrier.runGame();
    }
}