package com.sfg.geocomply.data;

import java.io.IOException;

public interface NetService {
    String getPageTitle(String url) throws IOException;
}
