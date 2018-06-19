package network.devandroid.com.networklibrarycomparison.utils

import android.webkit.URLUtil

object Validations {

    fun isValidUrl(url: String): Boolean {
        return URLUtil.isValidUrl(url)
    }
}
