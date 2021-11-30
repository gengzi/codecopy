package fun.gengzi.codecopy.cache;

import org.apache.commons.lang3.concurrent.Computable;

import java.util.concurrent.*;

/**
 * <h1>缓存阻塞机制</h1>
 * <p>在看Mybatis源码的时候，二级缓存提供了一个blocking标识，表示当在缓存中找不到元素时，
 * 它设置对缓存键的锁定；这样其他线程将等待此元素被填充，而不是命中数据库；
 * 其实我们使用缓存的目的就是因为被缓存的数据生成比较费时，比如调用对外的接口，查询数据库，
 * 计算量很大的结果等等；这时候如果多个线程同时调用get方法获取的结果都为null，
 * 每个线程都去执行一遍费时的计算，其实也是对资源的浪费；最好的办法是只有一个线程去执行，
 * 其他线程等待，计算一次就够了；但是此功能基本上都交给使用者来处理，很少有本地缓存有这种功能；
 *
 * 作者：ksfzhaohui
 * 链接：https://juejin.im/post/5dd942e15188257324096fe7
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。</p>
 *
 * 当缓存中未查到相关数据，获取该数据比较耗时，防止其他线程访问该缓存数据，也去执行这个耗时操作，
 * 将其他线程阻塞，只有当前线程获取数据成功后，其他线程直接获取该数据
 *
 * @param <A>  key
 * @param <V>  value
 */
public class Memoizer<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache
            = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}