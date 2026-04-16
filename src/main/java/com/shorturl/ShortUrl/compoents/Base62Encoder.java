package com.shorturl.ShortUrl.compoents;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String BASE62="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encode( long value) {
        StringBuilder sb=new StringBuilder();

        while(value>0) {
            sb.append(BASE62.charAt((int)(value%BASE62.length())));
            value=value/BASE62.length();
        }
        return sb.reverse().toString();
    }
}
