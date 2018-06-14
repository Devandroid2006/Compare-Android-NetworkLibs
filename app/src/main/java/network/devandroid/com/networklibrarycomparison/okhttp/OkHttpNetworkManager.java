package network.devandroid.com.networklibrarycomparison.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpNetworkManager extends BaseNetworkManager<String> {

    public OkHttpNetworkManager(Callback mCallback) {
        super(mCallback);
    }

    @Override
    public void send(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(null)//disable cache
                .build();
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(new CacheControl.Builder().noCache().build())
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (null != mCallback) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onError(e.getLocalizedMessage());
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (null != mCallback) {
                    final String result = response.body().string().toString();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onResponse(result);
                        }
                    });
                }
            }
        });
    }


}
