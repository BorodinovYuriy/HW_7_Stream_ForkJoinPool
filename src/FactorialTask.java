import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

class FactorialTask extends RecursiveTask<BigInteger> {
    private final int n;
    private int start;
    private int end;

    public FactorialTask(int n) {
        this.n = n;
    }
    public FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
        this.n = 0;
    }
    @Override
    protected BigInteger compute() {
        if (this.n != 0) {
            return factorial(1, n);
        } else {
            //Возврат Task для пула "new FactorialTask(start, mid)..."[ниже]
            return factorial(start,end);
        }
    }

    private BigInteger factorial(int start, int end) {
        if (start > end) {
            return BigInteger.ONE;
        }
        if (start == end) {
            return BigInteger.valueOf(start);
        }
        if (end - start <= 10) {
            BigInteger result = BigInteger.ONE;
            for (int i = start; i <= end; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
        }

        int mid = (start + end) / 2;

        FactorialTask leftTask = new FactorialTask(start, mid);
        FactorialTask rightTask = new FactorialTask(mid + 1, end);
        //Когда поток из пула забирает leftTask,
        //он автоматически вызывает leftTask.compute().
        leftTask.fork();
        //rightResult продолжается в main потоке
        BigInteger rightResult = rightTask.compute();
        BigInteger leftResult = leftTask.join();

        return leftResult.multiply(rightResult);
    }
}
