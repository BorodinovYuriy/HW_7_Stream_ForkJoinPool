import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        int n = 10;

        BigInteger result;
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            FactorialTask factorialTask = new FactorialTask(n);

            result = forkJoinPool.invoke(factorialTask);
        }

        System.out.println("Факториал " + n + "! = " + result);
    }
}