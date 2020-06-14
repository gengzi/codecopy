package fun.gengzi.codecopy.cache;

import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import org.apache.commons.lang3.concurrent.Computable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoizerTest {

    private Logger logger = LoggerFactory.getLogger(LocalCacheAutoInit.class);




    @Autowired
    @Qualifier("localhostCacheManager")
    private CacheManager cacheManager;


    @Test
    void compute() {

        Memoizer<String, Shorturl> memoizer = new Memoizer<>(new Computable<String , Shorturl>() {
            @Override
            public Shorturl compute(String arg) throws InterruptedException {
                // 手动获取缓存中的数据
                Cache mycache = cacheManager.getCache("mycache");
                Cache.ValueWrapper valueWrapper = mycache.get("http://localhost:8089/u/1C");
                Object object = valueWrapper.get();
                Shorturl shorturl = null;
                if (object instanceof ArrayList) {
                    Object o = ((ArrayList) object).get(0);
                    if (o instanceof Shorturl) {
                        shorturl = (Shorturl) o;
                        logger.info("获取信息: {} ", shorturl.getId());
                    }
                }
                return shorturl;
            }
        });
        

        try {
            Shorturl shorturl = memoizer.compute("key");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}