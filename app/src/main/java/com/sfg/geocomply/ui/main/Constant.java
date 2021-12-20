package com.sfg.geocomply.ui.main;

public class Constant {
    public static final String REGEX_MENTION = "@[a-z]+";
    //    public static final String REGEX_URL = "(https?|http?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]+";
    public static final String REGEX_URL =
            "((http|https)://)(www.)?"
                    + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                    + "{2,256}\\.[a-z]"
                    + "{2,6}\\b([-a-zA-Z0-9@:%"
                    + "._\\+~#?&//=]*)"
                    + "/";
    public static final String SYMBOL_MENTION = "@";
}
