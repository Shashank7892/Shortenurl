package com.shorturl.ShortUrl.controller;

import com.shorturl.ShortUrl.helperDTOs.RequestDTO;
import com.shorturl.ShortUrl.helperDTOs.ResponseDTO;
import com.shorturl.ShortUrl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/urls")
public class UrlController {

    @Autowired
    private UrlService urlService;


    @PostMapping
    public ResponseEntity<ResponseDTO> createShortUrl(@RequestBody RequestDTO requestDTO) {
        String shorturl = urlService.createShortUrl(requestDTO.getOriginalUrl());
        return ResponseEntity.ok(ResponseDTO.builder().shortUrl(shorturl).build());
    }

    @GetMapping("/{shorturl}")
    public ResponseEntity<Void> redirect(@PathVariable String shorturl) {
        String originalUrl = urlService.getOrginalUrl(shorturl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }
}
