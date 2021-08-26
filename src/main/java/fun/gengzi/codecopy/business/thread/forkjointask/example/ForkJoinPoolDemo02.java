package fun.gengzi.codecopy.business.thread.forkjointask.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


public class ForkJoinPoolDemo02 {
    static class SumTask extends RecursiveTask < Long > {
        private static final int THRESHOLD = 10;
        private long start;
        private long end;
        public SumTask(long n) {
            this(1, n);
        }
        public SumTask(long start, long end) {
            this.start = start;
            this.end = end;
        }
        @Override
        protected Long compute() {
            long sum = 0;
            // 如果计算的范围在threshold之内，则直接进行计算
            if ((end - start) <= THRESHOLD) {
                for (long l = start; l <= end; l++) {
                    System.out.println(Thread.currentThread().getName() + ":" + sum);
                    sum += l;
                }
            } else {
                // 否则找出起始和结束的中间值，分割任务
                long mid = (start + end) >>> 1;
                SumTask left = new SumTask(start, mid);
                SumTask right = new SumTask(mid + 1, end);
                left.fork();
                right.fork();
                // 收集子任务计算结果
                sum = left.join() + right.join();
                System.out.println("==========和============="+sum);
            }
            return sum;
        }
    }
    public static void main(String[] args) throws ExecutionException,
            InterruptedException {
        SumTask sum = new SumTask(100);
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask < Long > future = pool.submit(sum);
        Long aLong = future.get();
        System.out.println(aLong);
        pool.shutdown();
    }
}