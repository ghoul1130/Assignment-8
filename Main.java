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

---OUTPUT---

=== ASYNCHRONOUS TASK PROCESSOR ===
Task 3 started on pool-1-thread-3
Task 1 started on pool-1-thread-1
Task 2 started on pool-1-thread-2
Task 1 => Result_1_processed
Task 4 started on pool-1-thread-1
Task 3 => Result_3_processed
Task 5 started on pool-1-thread-3
Task 2 => Handled_java.lang.RuntimeException: Simulated failure for task 2
Task 5 => Result_5_processed
Task 4 => Handled_java.lang.RuntimeException: Simulated failure for task 4
Async tasks completed.


=== PRODUCER-CONSUMER (ReentrantLock + Condition) ===
Produced: 1
Produced: 2
Produced: 3
Producer waiting (buffer full)
Consumed: 1
Produced: 4
Producer waiting (buffer full)
Consumed: 2
Produced: 5
Consumed: 3
Consumed: 4
Consumed: 5
Producer-Consumer completed.


=== PARKING LOT (Semaphore) ===
Car 1 arriving...
Car 1 PARKED (spaces left: 2)
Car 2 arriving...
Car 2 PARKED (spaces left: 1)
Car 3 arriving...
Car 3 PARKED (spaces left: 0)
Car 4 arriving...
Car 5 arriving...
Car 6 arriving...
Car 4 PARKED (spaces left: 0)
Car 1 LEFT (spaces now: 1)
Car 2 LEFT (spaces now: 1)
Car 5 PARKED (spaces left: 0)
Car 3 LEFT (spaces now: 1)
Car 6 PARKED (spaces left: 0)
Car 4 LEFT (spaces now: 1)
Car 5 LEFT (spaces now: 2)
Car 6 LEFT (spaces now: 3)
Parking Lot simulation completed.


=== RACE COORDINATION (CountDownLatch) ===
Race started! Waiting for runners...
Runner 1 running...
Runner 2 running...
Runner 3 running...
Runner 4 running...
Runner 3 FINISHED! (Remaining: 3)
Runner 1 FINISHED! (Remaining: 2)
Runner 4 FINISHED! (Remaining: 1)
Runner 2 FINISHED! (Remaining: 0)
ALL RUNNERS FINISHED! Race complete!
Race completed.


=== GAME SYNCHRONIZATION (CyclicBarrier) ===
Player 1 loading game...
Player 2 loading game...
Player 3 loading game...
Player 1 LOADED, waiting...
Player 2 LOADED, waiting...
Player 3 LOADED, waiting...
ALL PLAYERS READY! Game starting...
Player 3 playing Round 1...
Player 1 playing Round 1...
Player 2 playing Round 1...
Player 3 finished Round 1, waiting...
Player 1 finished Round 1, waiting...
Player 2 finished Round 1, waiting...
ALL PLAYERS READY! Game starting...
Player 2 playing Round 2...
Player 3 playing Round 2...
Player 1 playing Round 2...
Player 2 completed!
Player 1 completed!
Player 3 completed!
Game completed.


Process finished with exit code 0
