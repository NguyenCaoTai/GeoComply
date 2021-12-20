package com.sfg.geocomply;

import java.io.IOException;

public interface NetService {
    String getPageTitle(String url) throws IOException;
}
