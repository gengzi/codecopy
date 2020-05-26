package fun.gengzi.codecopy.business.shorturl.service.impl;

import fun.gengzi.codecopy.business.shorturl.constant.ShortUrlConstant;
import fun.gengzi.codecopy.business.shorturl.dao.ShortUrlGeneratorDao;
import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import fun.gengzi.codecopy.business.shorturl.service.ShortUrlGeneratorService;
import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.utils.BaseConversionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * <h1>短连接生成器接口实现</h1>
 *
 * 方案1：单机，redis 发号器
 * 方案2：多实例部署，redis 分段发号器
 * 方案3：多实例部署，使用分布式发号器（雪花算法（SnowFlake），Leaf 等等）
 *
 *
 *
 * @author gengzi
 * @date 2020年5月26日16:20:18
 */
@Service
public class ShortUrlGeneratorServiceImpl implements ShortUrlGeneratorService {

    private Logger logger = LoggerFactory.getLogger(ShortUrlGeneratorServiceImpl.class);
    // 考虑之后项目会有nginx 负载，这里指定生成短链接的前缀地址
    @Value("${shorturl.pre}")
    private String linkUrlPre;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ShortUrlGeneratorDao shortUrlGeneratorDao;

    /**
     * 返回短链接
     * // 判断当前连接能否在redis 查找到，查找到直接返回短链接，并更新这个key value 的过期时间为1小时
     * // 逻辑发号器
     * // 返回号码，作为数据库的主键，检测主键是否冲突，冲突重新尝试拿新的号码（也可以不验证是否主键冲突，只要能保证发号器发的号码是唯一的）
     * // 将长连接和号码绑定，将10进制的号码，转换为62进制
     * // 组拼短链接，设置超时时间
     * // 存入数据库
     * // 存入redis，key value 的形式，key 62进制 ，对应一个长连接，如果数量太多，可以设置一个失效时间（比如 三天），防止redis中缓存太多
     * // 再次存入reids， key value 的形式，长连接，对应一个 短链接的62进制，设置失效时间是 1 小时，当同一个长链接再来，就可以直接从redis中返回
     * // 返回
     *
     * @param longUrl 普通链接
     * @return
     */
    @Transactional
    @Override
    public String generatorShortUrl(String longUrl) {
        logger.info("-- longurl to shorturl start --");
        logger.info("param longurl : {}", longUrl);
        // 判断当前连接不能为null 或者 " "
        if (StringUtils.isNoneBlank(longUrl)) {
            boolean isExist = redisUtil.hasKey(longUrl);
            if (isExist) {
                String shortUrl = (String) redisUtil.get(longUrl);
                redisUtil.expire(longUrl, ShortUrlConstant.UPDATETIMEHOUR, TimeUnit.HOURS);
                logger.info("redis get shorturl success, return shorturl : {}", linkUrlPre + shortUrl);
                return linkUrlPre + shortUrl;
            } else {
                long number = redisUtil.getRedisSequence();
                String str62 = BaseConversionUtils.to62RadixString(number);
                String genShortUrl = linkUrlPre + str62;

                Shorturl shorturl = new Shorturl();
                shorturl.setId(number);
                shorturl.setLongurl(longUrl);
                shorturl.setShorturl(genShortUrl);
                shorturl.setIsoverdue(Integer.valueOf(ShortUrlConstant.ISOVERDUE));
                shorturl.setTermtime(new Date());
                shorturl.setCreatetime(new Date());
                shorturl.setUpdatetime(new Date());

                shortUrlGeneratorDao.save(shorturl);
                // 将62进制跟长链接保存到session，这里没有保存短链接，因为前缀没必要存入缓存
                redisUtil.set(str62, longUrl);
                redisUtil.set(longUrl, str62, 1, TimeUnit.HOURS);
                logger.info("insert shorturl success , return shorturl : {} ", genShortUrl);
                return genShortUrl;
            }
        }

        return "";
    }

    /**
     * 返回长链接
     * // 判断当前连接能否在redis 查找到，查找到直接返回长连接
     * // 将62进制，转为10进制
     * // 判断返回长连接是无，则在数据库中查找
     *
     * @param shortUrl 短链接
     * @return 长链接
     */
    @Override
    public String getLongUrl(String shortUrl) {
        String longUrl = (String) redisUtil.get(shortUrl);
        if (StringUtils.isNoneBlank(longUrl)) {
            return longUrl;
        }
        long shortUrlId = BaseConversionUtils.radixString(shortUrl);
        Shorturl shorturl = shortUrlGeneratorDao.getOne(shortUrlId);
        if (shorturl != null) {
            return shorturl.getLongurl();
        }
        return "";
    }
}
