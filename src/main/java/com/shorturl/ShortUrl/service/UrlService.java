package com.shorturl.ShortUrl.service;

import com.shorturl.ShortUrl.compoents.Base62Encoder;
import com.shorturl.ShortUrl.model.UrlEntity;
import com.shorturl.ShortUrl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private Base62Encoder base62Encoder;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String BASE_URL = "https://short.ly/";

    private static final String CACHE_PREFIX = "shortUrl:";

    public String createShortUrl(String originalurl) {
        UrlEntity url= UrlEntity.builder().
                originalUrl(originalurl)
                .createdAt(LocalDateTime.now())
                .clickcount(0L)
                .build();

        UrlEntity saved = urlRepository.save(url);

        String shortUrl = base62Encoder.encode(saved.getId());
        saved.setShortUrl(shortUrl);

        urlRepository.save(saved);

        String key=CACHE_PREFIX+shortUrl;

        redisTemplate.opsForValue().set(key,originalurl,10,TimeUnit.MINUTES);
        return BASE_URL+shortUrl;
    }

    public String getOrginalUrl(String shortUrl) {
        String key=CACHE_PREFIX+shortUrl;

        String cachedurl=redisTemplate.opsForValue().get(key);
        if(cachedurl!=null){
            return cachedurl;
        }
        UrlEntity url=urlRepository.findByShortUrl(shortUrl).orElseThrow(()->new RuntimeException("URL not found"));
        url.setClickcount(url.getClickcount()+1);
        urlRepository.save(url);
        redisTemplate.opsForValue().set(key,url.getShortUrl(),10, TimeUnit.MINUTES);
        return url.getOriginalUrl();
    }
}
