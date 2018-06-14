package network.devandroid.com.networklibrarycomparison.internal;

import android.content.Context;

import network.devandroid.com.networklibrarycomparison.asyntask.AsyncNetworkManager;
import network.devandroid.com.networklibrarycomparison.fastnetworking.FastNetworkManager;
import network.devandroid.com.networklibrarycomparison.okhttp.OkHttpNetworkManager;
import network.devandroid.com.networklibrarycomparison.retrofit.RetrofitNetworkManager;
import network.devandroid.com.networklibrarycomparison.rxjava.RxJavaNetworkManager;
import network.devandroid.com.networklibrarycomparison.volley.VolleyNetworkManager;

public class NetFactory {

    public static BaseNetworkManager getNetworkManager(Context context, NetType type, INetworkManager.Callback callback) {
        switch (type) {
            case VOLLEY:
                return new VolleyNetworkManager(callback, context);
            case RETROFIT:
                return new RetrofitNetworkManager(callback);
            case OK_HTTP:
                return new OkHttpNetworkManager(callback);
            case FAST_NETWORK:
                return new FastNetworkManager(callback);
            case ASYNC_TASK:
                return new AsyncNetworkManager(callback);
            case RXJAVA:
                return new RxJavaNetworkManager(callback);
        }
        return null;
    }
}
