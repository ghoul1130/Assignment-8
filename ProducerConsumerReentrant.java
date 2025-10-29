import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerReentrant {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final int CAPACITY = 3;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                lock.lock();
                try {
                    while (buffer.size() == CAPACITY) {
                        System.out.println("Producer waiting (buffer full)");
                        notFull.await();
                    }
                    buffer.add(i);
                    System.out.println("Produced: " + i);
                    notEmpty.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                lock.lock();
                try {
                    while (buffer.isEmpty()) {
                        System.out.println("Consumer waiting (buffer empty)");
                        notEmpty.await(1, TimeUnit.SECONDS);
                    }
                    int val = buffer.poll();
                    System.out.println("Consumed: " + val);
                    notFull.signal();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public void runProducerConsumer() {
        ExecutorService exec = Executors.newFixedThreadPool(2);
        exec.submit(new Producer());
        exec.submit(new Consumer());
        exec.shutdown();
        try {
            exec.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Producer-Consumer completed.\n");
    }
}
