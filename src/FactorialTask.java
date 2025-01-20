import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

class FactorialTask extends RecursiveTask<BigInteger> {
    private final int n;
    private final int start;
    private final int end;


    public FactorialTask(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Факториал не определен для отрицательных чисел");
        } else {
            this.n = n;
            this.start = 1;
            this.end = n;
        }
    }
    public FactorialTask(int start, int end, int n) {
        this.start = start;
        this.end = end;
        this.n = n;
    }

    @Override
    protected BigInteger compute() {
        if (start > end) {
            return BigInteger.ONE;
        }
        if (start == end) {
            return BigInteger.valueOf(start);
        }
        //При 10 - вычисляем на месте...
        if (end - start <= 10) {
            BigInteger result = BigInteger.ONE;
            for (int i = start; i <= end; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
        }

        int mid = (start + end) / 2;
        FactorialTask leftTask = new FactorialTask(start, mid, this.n);
        FactorialTask rightTask = new FactorialTask(mid + 1, end, this.n);
        leftTask.fork();
        BigInteger rightResult = rightTask.compute();
        BigInteger leftResult = leftTask.join();
        return leftResult.multiply(rightResult);
    }
    public BigInteger computeSequentially(){
        BigInteger result = BigInteger.ONE;
        for (int i = start; i <= end; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}

