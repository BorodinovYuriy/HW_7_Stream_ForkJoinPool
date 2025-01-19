
import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        int n = 10000;
        final long[] timeFJP = new long[1];
        final long[] timeNoFJP = new long[1];

        // Создание и запуск потока для ForkJoinPool
        Thread forkJoinThread = new Thread(() -> {
            BigInteger result;
            long startTime;
            long endTime;

            try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
                FactorialTask factorialTask = new FactorialTask(n);
                startTime = System.currentTimeMillis();
                //Вызов метода compute()
                result = forkJoinPool.invoke(factorialTask);
                endTime = System.currentTimeMillis();
            }

            System.out.println("Факториал (ForkJoinPool): " + n + "! = " + result);

            timeFJP[0] = endTime - startTime;
        });

        // Создание и запуск потока для последовательного вызова
        Thread sequentialThread = new Thread(() -> {
            BigInteger sequentialResult;
            long startTime;
            long endTime;

            FactorialTask factorialTask = new FactorialTask(n);
            startTime = System.currentTimeMillis();
            sequentialResult = factorialTask.computeSequentially();
            endTime = System.currentTimeMillis();

            System.out.println("Факториал (последовательный): " + n + "! = " + sequentialResult);
            timeNoFJP[0] =endTime - startTime;
        });

        forkJoinThread.start();
        sequentialThread.start();
        try {
            forkJoinThread.join();
            sequentialThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Время выполнения с ForkJoinPool: " + timeFJP[0] + " мс");
        System.out.println("Время выполнения без ForkJoinPool: " + timeNoFJP[0] + " мс");
    }
}
