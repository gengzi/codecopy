package fun.gengzi.codecopy.business.shorturl.service.impl;

import fun.gengzi.codecopy.business.shorturl.constant.ShortUrlConstant;
import fun.gengzi.codecopy.business.shorturl.dao.ShortUrlGeneratorDao;
import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import fun.gengzi.codecopy.business.shorturl.service.ShortUrlGeneratorService;
import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.utils.BaseConversionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * <h1>短连接生成器接口实现</h1>
 */
@Service
public class ShortUrlGeneratorServiceImpl implements ShortUrlGeneratorService {

    private String linkUrlPre = "localhost:8089/";




    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ShortUrlGeneratorDao shortUrlGeneratorDao;

    /**
     * 返回短链接
     *
     * @param longUrl 普通链接
     * @return
     */
    @Transactional
    @Override
    public String generatorShortUrl(String longUrl) {
        // 判断当前连接能否在redis 查找到，查找到直接返回短链接，并更新这个key value 的过期时间为1小时
        // 逻辑发号器
        // 返回号码，作为数据库的主键，检测主键是否冲突，冲突重新尝试拿新的号码
        // 将长连接和号码绑定，将10进制的号码，转换为62进制
        // 组拼短链接，设置超时时间
        // 存入数据库
        // 存入redis，key value 的形式，key 62进制 ，对应一个长连接
        // 再次存入reids， key value 的形式，长连接，对应一个 短链接的62进制，设置失效时间是 1 小时
        // 返回
        // 判断当前连接不能为null 或者 " "
        if (StringUtils.isNoneBlank(longUrl)) {
            boolean isExist = redisUtil.hasKey(longUrl);
            if (isExist) {
                String shortUrl = (String) redisUtil.get(longUrl);
                redisUtil.expire(longUrl, ShortUrlConstant.UPDATETIMEHOUR, TimeUnit.HOURS);
            } else {
                long number = redisUtil.getRedisSequence();
                String str62 = BaseConversionUtils.to62RadixString(number);

                Shorturl shorturl = new Shorturl();
                shorturl.setId(number);
                shorturl.setLongurl(longUrl);
                shorturl.setShorturl(linkUrlPre + str62);
                shorturl.setIsoverdue(Integer.valueOf(ShortUrlConstant.ISOVERDUE));
                shorturl.setTermtime(new Date());
                shorturl.setCreatetime(new Date());
                shorturl.setUpdatetime(new Date());

                shortUrlGeneratorDao.save(shorturl);
                redisUtil.set(linkUrlPre + str62, longUrl);
                redisUtil.set(longUrl, linkUrlPre + str62, 1, TimeUnit.HOURS);
                return "success";

            }

        }

        return null;
    }

    /**
     * 返回长链接
     *
     * @param shortUrl 短链接
     * @return 长链接
     */
    @Override
    public String getLongUrl(String shortUrl) {
        // 判断当前连接能否在redis 查找到，查找到直接返回长连接
        // 将62进制，转为10进制
        // 判断返回长连接是无，则在数据库中查找
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
    // 逻辑发号器逻辑，使用redis 递增，
}
