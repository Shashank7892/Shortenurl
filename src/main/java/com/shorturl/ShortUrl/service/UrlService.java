package com.shorturl.ShortUrl.service;

import com.shorturl.ShortUrl.compoents.Base62Encoder;
import com.shorturl.ShortUrl.model.UrlEntity;
import com.shorturl.ShortUrl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private Base62Encoder base62Encoder;

    private static final String BASE_URL = "https://short.ly/";

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

        return BASE_URL+shortUrl;
    }

    public String getOrginalUrl(String shortUrl) {
        UrlEntity url=urlRepository.findByShortUrl(shortUrl).orElseThrow(()->new RuntimeException("URL not found"));
        url.setClickcount(url.getClickcount()+1);
        urlRepository.save(url);
        return url.getOriginalUrl();
    }
}
