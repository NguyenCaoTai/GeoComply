package com.sfg.geocomply.ui.main;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.sfg.geocomply.domain.GetPageTitleFromUrl;
import com.sfg.geocomply.ui.main.model.Link;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Flowable;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    GetPageTitleFromUrl getPageTitle;

    MainViewModel mainViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mainViewModel = new MainViewModel(getPageTitle);
    }

    @Test
    public void main_view_model_init_with_default_value() {
        Assert.assertEquals("", mainViewModel.comment.getValue());
        mainViewModel.mentionResponse
                .observeForever(resp ->
                        Assert.assertEquals("", resp)
                );

        mainViewModel.linkResponse
                .observeForever(resp ->
                        Assert.assertEquals("", resp)
                );
    }

    @Test
    public void mention_response_is_empty_when_comment_not_contain_any_mention() {
        String mentionExpected = "";
        String comment = "do you know billgates";
        mainViewModel.comment
                .postValue(comment);

        mainViewModel.mentionResponse
                .observeForever(resp ->
                        Assert.assertEquals(mentionExpected, resp)
                );
    }

    @Test
    public void mention_response_is_correct_json_format_with_one_mention_when_comment_contain_one_mention() {
        String mentionExpected = "{\"mentions\": [\"billgates\"]}";
        String comment = "do you know @billgates";
        mainViewModel.comment
                .postValue(comment);

        mainViewModel.mentionResponse
                .observeForever(resp ->
                        Assert.assertEquals(mentionExpected, resp)
                );
    }

    @Test
    public void mention_response_is_correct_json_format_with_two_mentions_when_comment_contain_two_mentions() {
        String mentionExpected = "{\"mentions\": [\n" +
                "    \"billgates\",\n" +
                "    \"nctai\"\n" +
                "]}";
        String comment = "@billgates do you know @nctai mobile developer";
        mainViewModel.comment
                .postValue(comment);

        mainViewModel.mentionResponse
                .observeForever(resp ->
                        Assert.assertEquals(mentionExpected, resp)
                );
    }

    @Test
    public void link_response_is_empty_when_comment_not_contain_any_url() {
        String expected = "";
        String comment = "Olympics 2020 is happening";
        mainViewModel.comment
                .postValue(comment);

        mainViewModel.linkResponse
                .observeForever(resp ->
                        Assert.assertEquals(expected, resp)
                );
    }

    @Test
    public void link_response_is_correct_json_format_when_comment_contain_url() {
        String expected = "{\"links\": [{\n" +
                "    \"title\": \"Tokyo 2020 Olympics\",\n" +
                "    \"url\": \"https://olympics.com/tokyo-2020/en/\"\n" +
                "}]}";
        String comment = "Olympics 2020 is happening; https://olympics.com/tokyo-2020/en/";

        Link linkMock = new Link("https://olympics.com/tokyo-2020/en/",
                "Tokyo 2020 Olympics");

        Mockito.when(getPageTitle.execute("https://olympics.com/tokyo-2020/en/"))
                .thenReturn(Flowable.just(linkMock));

        mainViewModel.comment
                .postValue(comment);

        mainViewModel.linkResponse
                .observeForever(resp ->
                        Assert.assertEquals(expected, resp)
                );
    }
}