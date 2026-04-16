package com.shorturl.ShortUrl.repository;

import com.shorturl.ShortUrl.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
