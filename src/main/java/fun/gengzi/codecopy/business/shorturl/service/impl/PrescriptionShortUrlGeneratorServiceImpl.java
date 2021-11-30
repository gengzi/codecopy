package fun.gengzi.codecopy.business.shorturl.service.impl;

import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import fun.gengzi.codecopy.business.shorturl.service.PrescriptionShortUrlGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 时效短链接生成服务
 */
@Service
public class PrescriptionShortUrlGeneratorServiceImpl extends ShortUrlGeneratorServiceImpl implements PrescriptionShortUrlGeneratorService {
    @Override
    public Optional<Shorturl> getPrescriptionShortUrl(String longUrl) {

        Shorturl shorturl = new Shorturl();
        shorturl.setLongurl(longUrl);
        shorturl.setShorturl(super.generatorShortUrl(longUrl));
        return Optional.ofNullable(shorturl);
//        return Optional.empty();
    }

    /**
     * 返回短链接
     *
     * @param longUrl 普通链接
     * @return 短链接
     */
    @Override
    public String generatorShortUrl(String longUrl) {
        return super.generatorShortUrl(longUrl);
    }

    /**
     * 返回长链接
     *
     * @param shortUrl 短链接
     * @return 长链接
     */
    @Override
    public String getLongUrl(String shortUrl) {
        return super.getLongUrl(shortUrl);
    }
}
