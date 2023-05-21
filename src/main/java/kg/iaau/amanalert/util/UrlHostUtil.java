package kg.iaau.amanalert.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlHostUtil {

    private static final String HOST_NAME;

    private static final String NEWS_IMAGE_URL;
    private static final String AVATAR_IMAGE_URL;

    static {
        HOST_NAME = "https://aman-alert.herokuapp.com";
        NEWS_IMAGE_URL = "/api/news/image/";
        AVATAR_IMAGE_URL = "/api/user/avatar/";
    }

    public static String getHostUrl() {
        return HOST_NAME;
    }

    public static String getAvatarUrl() {
        return AVATAR_IMAGE_URL;
    }

    public static String getUrlNewsImage() {
        return NEWS_IMAGE_URL;
    }
}
