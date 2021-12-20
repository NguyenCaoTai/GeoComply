package com.sfg.geocomply.util;

import static org.junit.Assert.*;

import com.sfg.geocomply.ui.main.Constant;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UtilsTest {
    @Test
    public void return_empty_list_when_input_not_contain_mention_regex() {
        String regex = Constant.REGEX_MENTION;
        String input = "here you are";
        List<String> actual = Utils.parse(input, regex);

        assertEquals(new ArrayList<String>(), actual);
    }

    @Test
    public void return_empty_list_when_input_not_contain_link_regex() {
        String regex = Constant.REGEX_URL;
        String input = "https://google here you are";
        List<String> actual = Utils.parse(input, regex);

        assertEquals(new ArrayList<String>(), actual);
    }

    @Test
    public void return_mention_list_when_input_contain_corresponding_regex() {
        String regex = "@[a-z]+";
        String input = "@nctai here you are";
        List<String> expected = Collections.singletonList("@nctai");
        List<String> actual = Utils.parse(input, regex);

        assertEquals(expected, actual);
    }

    @Test
    public void return_url_list_when_input_contain_corresponding_regex() {
        String regex = Constant.REGEX_URL;
        String input = "https://google.com/ this is url";
        List<String> expected = Collections.singletonList("https://google.com/");
        List<String> actual = Utils.parse(input, regex);

        assertEquals(expected, actual);
    }

    @Test
    public void return_empty_string_when_input_is_not_json() {
        String input = "{\"key\"}";
        String actual = Utils.formatJson(input);

        assertEquals("", actual);
    }

    @Test
    public void return_json_be_formatted_when_input_input_json() {
        String expected = "{\"key\": \"value\"}";
        String input = "{\"key\":\"value\"}";
        String actual = Utils.formatJson(input);

        assertEquals(expected, actual);
    }
}