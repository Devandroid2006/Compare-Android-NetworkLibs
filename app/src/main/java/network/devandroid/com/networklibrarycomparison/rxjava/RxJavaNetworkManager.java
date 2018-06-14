package network.devandroid.com.networklibrarycomparison.rxjava;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import network.devandroid.com.networklibrarycomparison.internal.BaseNetworkManager;

public class RxJavaNetworkManager extends BaseNetworkManager<String> {

    private static final String TAG = "RxJavaNetworkManager";

    public RxJavaNetworkManager(Callback mCallback) {
        super(mCallback);
    }

    @Override
    public void send(String url) {
        getStringObservable(url)
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(final String response) {
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
                    public void onError(final Throwable e) {
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
                    public void onComplete() {

                    }
                });
    }

    private String getData(String srcUrl) throws IOException {
        Log.d(TAG, "doInBackground: START");
        HttpURLConnection urlConnection;
        String output = null;
        URL url = new URL(srcUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            output = readStream(in);
        } finally {
            urlConnection.disconnect();
        }

        Log.d(TAG, "doInBackground: END");
        return output;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    private Observable<String> getStringObservable(final String url) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(getData(url));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
