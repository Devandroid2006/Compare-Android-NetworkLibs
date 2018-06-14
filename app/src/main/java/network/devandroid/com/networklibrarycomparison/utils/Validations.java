package network.devandroid.com.networklibrarycomparison.utils;

import android.webkit.URLUtil;

public class Validations {

    public static boolean isValidUrl(final String url) {
        return URLUtil.isValidUrl(url);
    }
}
