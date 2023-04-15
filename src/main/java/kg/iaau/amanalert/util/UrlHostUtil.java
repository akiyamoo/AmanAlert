package kg.iaau.amanalert.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlHostUtil {

    private static final String HOST_NAME;

    private static final String NEWS_IMAGE_URL;

    static {
        HOST_NAME = "http://localhost:3003";
        NEWS_IMAGE_URL = "/api/news/image/";
    }

    public static String getHostUrl() {
        return HOST_NAME;
    }

    public static String getUrlNewsImage() {
        return NEWS_IMAGE_URL;
    }
}
