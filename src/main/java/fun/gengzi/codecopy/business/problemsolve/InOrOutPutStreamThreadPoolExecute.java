package fun.gengzi.codecopy.business.problemsolve;

import java.util.concurrent.*;

public class InOrOutPutStreamThreadPoolExecute extends ThreadPoolExecutor {


    public ConcurrentHashMap<Long, Task> hashMap = new ConcurrentHashMap();

    public InOrOutPutStreamThreadPoolExecute(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        Task task = new Task(t, r);
        hashMap.put(System.currentTimeMillis(), task);
        super.beforeExecute(t, r);
    }


}
