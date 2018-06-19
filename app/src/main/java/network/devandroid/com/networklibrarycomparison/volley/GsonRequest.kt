package network.devandroid.com.networklibrarycomparison.volley

import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

import java.io.UnsupportedEncodingException

class GsonRequest<T>
/**
 * Make a GET request and return a parsed object from JSON.
 *
 * @param url     URL of the request to make
 * @param clazz   Relevant class object, for Gson's reflection
 * @param headers Map of request headers
 */
(url: String, private val clazz: Class<T>, private val headers: Map<String, String>?,
 private val listener: Response.Listener<T>, errorListener: Response.ErrorListener) : Request<T>(Request.Method.GET, url, errorListener) {
    private val gson = Gson()

    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        return headers ?: super.getHeaders()
    }

    override fun deliverResponse(response: T) {
        listener.onResponse(response)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<T> {
        try {
            val json = String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers))
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            return Response.error(ParseError(e))
        }

    }
}
