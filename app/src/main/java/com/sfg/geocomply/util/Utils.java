package com.sfg.geocomply.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static List<String> parse(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }

    public static String formatJson(String jsonString) {
        try {
            return new JSONObject(jsonString)
                    .toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
