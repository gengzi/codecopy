package fun.gengzi.codecopy.business.shorturl.service;

/**
 * <h1>shorturl service interface</h1>
 *
 * @author gengzi
 * @date 2020年5月26日12:14:12
 */
public interface ShortUrlGeneratorService {

    /**
     * 返回短链接
     *
     * @param longUrl 普通链接
     * @return 短链接
     */
    String generatorShortUrl(String longUrl);

    /**
     * 返回长链接
     * @param shortUrl 短链接
     * @return 长链接
     */
    String getLongUrl(String shortUrl);
}
