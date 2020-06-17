package fun.gengzi.codecopy.business.product.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import fun.gengzi.codecopy.business.product.dao.ProductDao;
import fun.gengzi.codecopy.business.product.entity.Product;
import fun.gengzi.codecopy.business.product.service.ProductCacheService;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import io.swagger.models.auth.In;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <h1>产品信息缓存实现服务</h1>
 * <p>
 * 根据类型获取产品信息，针对 产品的基础信息，不会发生变动，变动的是 数目和金额  ，在活动页面系统可以容忍短期的不一致，可以缓存在 redis 中，缓存时间为 10 - 20 秒，
 * 来降低 高并发下，获取产品信息将网卡流量打满，系统负载饱和。
 * redis 可以看做一个近似单点的缓存服务，对于多个实例的服务，如果使用内存作为缓存的地方，会在每个服务实例，缓存一份数据，数据是冗余的，并且不好保证数据一致性
 * <p>
 * 演示  缓存雪崩（key 在同一时间失效），增加容错时间
 * 缓存穿透（不存在的key，一直被访问）  布隆过滤器解决 问题，如果把可能出现的key ，存入布隆过滤器
 * 缓存击穿 （一个缓存的key，大量请求，在缓存失效的某一刻，大量请求落到底层数据库） 缓存key一直不失效，设置缓存一致存在，使用互斥锁，只有一个线程可以访问数据，其他线程等待
 * <p>
 * 限流  限制同一ip 每秒访问同一接口的次数
 * 降级  服务降级，系统负荷比较高时，将服务降低，执行降级逻辑。
 * <p>
 * 预加载数据
 * 如果数据很少，在系统启动就加载缓存数据
 * 如果数据多一点，系统上线后，手动在后台页面，加载缓存数据
 * 定时任务，估计时间节点，刷新缓存
 * <p>
 * <p>
 * 刷新缓存策略：
 * 先操作数据库，在操作删除，直接删掉缓存数据
 * <p>
 * <p>
 * <p>
 * 数据一致性 ， 最终一致性
 * <p>
 * <p>
 * 更新缓存策略
 * Cache Aside Pattern
 * <p>
 * 问题: 本地缓存， 怎么判定那些是热点数据
 * 查询次数？
 * <p>
 * 当某个key，访问次数越多，就存入本地缓存
 *
 * @author gengzi
 * @date 2020年6月16日11:01:18
 */
@Service
public class ProductCacheServiceImpl implements ProductCacheService {
    // 存放的数量
    private static int size = 1000000;
    // 创建布隆过滤器
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size);


    @Autowired
    private ProductDao productDao;

    /**
     * 根据类型获取产品信息
     *
     * @param type 产品类型
     * @return
     */
    @Cacheable(cacheManager = "localhostRedisCacheManager", value = "ACTIVITY_PRODUCT_TYPE", key = "#type")
    @Override
    public List<Product> getProductCacheInfo(String type) {
        // 添加耗时
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productDao.findByType(type);
    }

    /**
     * 根据产品id 获取产品信息
     * <p>
     * 检查一下，查询一个不存在的 id ，会不会缓存 null 数据  (会缓存null)
     * 通过缓存null 解决缓存穿透的问题
     *
     * @param id
     * @return
     */
    @Cacheable(cacheManager = "localhostRedisCacheManager", value = "ACTIVITY_PRODUCT_ID", key = "#id")
    @Override
    public Product getOneProductCacheInfo(Integer id) {
        return productDao.findById(id.longValue()).orElse(null);
        // 会延迟加载  在进行json 转换时会报错 https://blog.csdn.net/ypp91zr/article/details/77707312
        // return productDao.getOne(id.longValue());
    }


    /**
     * 根据产品id 获取产品信息
     * <p>
     * 使用布隆过滤器，判断id 是否存布隆过滤器中 存在
     * 查缓存，如果有，返回
     * 无，直接返回
     *
     * @param id
     * @return
     */
    @Cacheable(cacheManager = "loclRedisCacheManagers", value = "PRODUCT_INFO_ID_NOPREFIX", key = "#id", cacheNames = {"PRODUCT_INFO_ID_NOPREFIX"})
    @Override
    public Product getOneProductCacheInfoBloom(Integer id) {
        // 如果存在返回 true ，不存在返回 false
        return getProduct(id);
    }

    /**
     * 根据产品id 获取产品信息  -  解决缓存雪崩,缓存穿透，缓存击穿
     *
     * @param id
     * @return
     */
    @SneakyThrows
    @Override
    public Product getOneProductCacheInfoTest(Integer id) {
        // 如果存在返回 true ，不存在返回 false
        return getProduct(id);
    }

    @Cacheable(cacheManager = "loclRedisCacheManagers", key = "#id",
            cacheNames = {"PRODUCT_INFO_ID_CACHE_AVALANCHE1"})
    public Product cacheAvalanche1(Integer id) {
        return getProduct(id);
    }

    @Cacheable(cacheManager = "loclRedisCacheManagers", key = "#id",
            cacheNames = {"PRODUCT_INFO_ID_CACHE_AVALANCHE2"})
    public Product cacheAvalanche2(Integer id) {
        // 如果存在返回 true ，不存在返回 false
        return getProduct(id);
    }
    @Cacheable(cacheManager = "loclRedisCacheManagers", key = "#id",
            cacheNames = {"PRODUCT_INFO_ID_CACHE_AVALANCHE3"})
    public Product cacheAvalanche3(Integer id) {
        // 如果存在返回 true ，不存在返回 false
        return getProduct(id);
    }
    @Cacheable(cacheManager = "loclRedisCacheManagers", key = "#id",
            cacheNames = {"PRODUCT_INFO_ID_CACHE_AVALANCHE4"})
    public Product cacheAvalanche4(Integer id) {
        // 如果存在返回 true ，不存在返回 false
        return getProduct(id);
    }

    private Product getProduct(Integer id) {
        // 如果存在返回 true ，不存在返回 false
        boolean isContain = bloomFilter.mightContain(id);
        if (isContain) {
            return productDao.findById(id.longValue()).orElseThrow(() -> new RrException("error ", RspCodeEnum.FAILURE.getCode()));
        } else {
            throw new RrException("error ", RspCodeEnum.FAILURE.getCode());
        }
    }



    @Override
    public void putBloomKey() {
        List<Integer> allId = productDao.getAllId();
        allId.forEach(id -> {
            bloomFilter.put(id);
        });
    }
}

