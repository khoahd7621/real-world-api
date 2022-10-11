package com.khoahd7621.realworldapi.utils;

import java.util.Date;

public class SlugUtil {
    public static String getSlug(String title) {
        return title.toLowerCase().replaceAll("\\s+", "-") + "-" + new Date().getTime();
    }
}
