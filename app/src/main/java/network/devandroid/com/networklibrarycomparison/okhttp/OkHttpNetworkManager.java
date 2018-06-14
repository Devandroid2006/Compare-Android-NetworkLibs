package network.devandroid.com.networklibrarycomparison.okhttp;

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
            public void onFailure(Call call, IOException e) {
                if (null != mCallback) {
                    mCallback.onError(e.getLocalizedMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != mCallback) {
                    mCallback.onResponse(response.body().string().toString());
                }
            }
        });
    }


}
