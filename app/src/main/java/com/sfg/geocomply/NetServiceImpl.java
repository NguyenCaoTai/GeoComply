package com.sfg.geocomply;

import org.jsoup.Jsoup;

import java.io.IOException;

public class NetServiceImpl implements NetService {
    @Override
    public String getPageTitle(String url) throws IOException {
        return Jsoup.connect(url)
                .get()
                .title();
    }
}
