package com.shorturl.ShortUrl.helperDTOs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
public class ResponseDTO {

    private String shortUrl;
}
