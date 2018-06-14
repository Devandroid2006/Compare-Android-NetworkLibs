package network.devandroid.com.networklibrarycomparison.fastnetworking;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager;

public class FastNetworkManager extends BaseNetworkManager<String> {

    public FastNetworkManager(Callback callback) {
        super(callback);
    }

    @Override
    public void send(String url) {
        AndroidNetworking.get(url)
                .setTag("test")
                .doNotCacheResponse()
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        if (null != mCallback) {
                            mCallback.onResponse(response);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (null != anError) {
                            anError.getErrorBody();
                        }
                    }
                });
    }
}
