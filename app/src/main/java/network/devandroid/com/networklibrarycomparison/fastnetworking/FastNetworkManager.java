package network.devandroid.com.networklibrarycomparison.fastnetworking;

import android.os.Handler;
import android.os.Looper;

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
                    public void onResponse(final String response) {
                        if (null != mCallback) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    mCallback.onResponse(response);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(final ANError anError) {
                        if (null != mCallback) {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    mCallback.onError(anError.getLocalizedMessage());
                                }
                            });
                        }
                    }
                });
    }
}
