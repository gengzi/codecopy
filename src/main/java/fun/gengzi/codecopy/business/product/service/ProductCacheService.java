package fun.gengzi.codecopy.business.product.service;

import fun.gengzi.codecopy.business.product.entity.Product;

import java.util.List;

/**
 * <h1>演示产品信息缓存服务</h1>
 * @author gengzi
 * @date 2020年6月16日10:55:36
 */
public interface ProductCacheService {

    /**
     * 根据类型获取产品信息
     * @param type 产品类型
     * @return
     */
    List<Product> getProductCacheInfo(String type);


    /**
     * 根据产品id 获取产品信息  - 解决缓存穿透，缓存null 数据
     * @param id
     * @return
     */
    Product getOneProductCacheInfo(Integer id);

    /**
     * 根据产品id 获取产品信息  -  解决缓存穿透： 布隆过滤器
     * @param id
     * @return
     */
    Product getOneProductCacheInfoBloom(Integer id);


    /**
     * 根据产品id 获取产品信息  -  解决缓存穿透： Redis布隆过滤器
     * @param id
     * @return
     */
    Product getOneProductCacheInfoRedisBloom(Integer id);




    /**
     * 根据产品id 获取产品信息  -  解决缓存雪崩,缓存穿透，缓存击穿
     * @param id
     * @return
     */
    Product getOneProductCacheInfoTest(Integer id);

    /**
     * 根据产品id 获取产品信息  -  解决缓存雪崩 增加内存缓存，避免缓存在同一时刻，全部失效
     * @param id
     * @return
     */
    Product getOneProductCacheInfoTest2(Integer id);


    /**
     * 更新产品数据信息
     * @param product 产品
     */
    void updateProductInfo(Product product);

    /**
     * 将可能的key 都存入布隆过滤器
     */
    void putBloomKey();

    /**
     * 将可能的key 都存入Redis布隆过滤器
     */
    void putRedisBloomKey();



}
