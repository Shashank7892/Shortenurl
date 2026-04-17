package com.shorturl.ShortUrl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "urldata")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 2048)
    private String originalUrl;
    @Column(unique = true)
    private String shortUrl;

    private LocalDateTime createdAt;

    private Long clickcount;
}
