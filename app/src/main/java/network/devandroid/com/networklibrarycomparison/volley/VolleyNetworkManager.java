package network.devandroid.com.networklibrarycomparison.volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager;

public class VolleyNetworkManager extends BaseNetworkManager<String> {

    private Context context;

    public VolleyNetworkManager(Callback callback, Context context) {
        super(callback);
        this.context = context;
    }

    @Override
    public void send(String url) {
        final StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (null != mCallback) {
                    mCallback.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != mCallback) {
                    mCallback.onError(error.getLocalizedMessage());
                }
            }
        });
        //disable the cache and add request
        stringRequest.setShouldCache(false);
        VolleyNetwork volleyNetwork = VolleyNetwork.getInstance(context);
        volleyNetwork.getRequestQueue().getCache().clear();
        volleyNetwork.addToRequestQueue(stringRequest);
    }
}
