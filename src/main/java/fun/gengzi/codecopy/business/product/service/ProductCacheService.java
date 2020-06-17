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
     * 根据产品id 获取产品信息
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
     * 将可能的key 都存入布隆过滤器
     */
    void putBloomKey();



}
