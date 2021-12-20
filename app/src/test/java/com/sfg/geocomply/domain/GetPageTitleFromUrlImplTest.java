package com.sfg.geocomply.domain;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.sfg.geocomply.data.NetService;
import com.sfg.geocomply.ui.main.model.Link;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

public class GetPageTitleFromUrlImplTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    NetService netService;

    GetPageTitleFromUrl getPageTitleFromUrl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        getPageTitleFromUrl = new GetPageTitleFromUrlImpl(netService);
    }

    @Test
    public void return_title_from_page_html() throws IOException {
        String url = "https://google.com";
        String mockTitle = "This is title";

        Mockito.when(netService.getPageTitle(url))
                .thenReturn(mockTitle);

        Link expected = new Link(url, "This is title");
        Link actual = getPageTitleFromUrl.execute(url).blockingFirst();

        Assert.assertEquals(expected.url, actual.url);
        Assert.assertEquals(expected.title, actual.title);
    }

    @Test
    public void title_is_empty_when_load_page_throw_exception() throws IOException {
        String url = "https://google.com";
        String mockTitle = "This is title";

        Mockito.when(netService.getPageTitle(url))
                .thenThrow(new IOException());

        Link expected = new Link(url, "");
        Link actual = getPageTitleFromUrl.execute(url).blockingFirst();

        Assert.assertEquals(expected.url, actual.url);
        Assert.assertEquals(expected.title, actual.title);
    }
}