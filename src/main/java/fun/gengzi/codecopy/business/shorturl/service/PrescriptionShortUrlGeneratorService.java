package fun.gengzi.codecopy.business.shorturl.service;


import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;

import java.util.Optional;

/**
 *  时效短链接生成器 sevice
 * @author gengzi
 * @date 2020年6月1日15:58:53
 */
public interface PrescriptionShortUrlGeneratorService extends ShortUrlGeneratorService{


    Optional<Shorturl> getPrescriptionShortUrl(String longUrl);


}
