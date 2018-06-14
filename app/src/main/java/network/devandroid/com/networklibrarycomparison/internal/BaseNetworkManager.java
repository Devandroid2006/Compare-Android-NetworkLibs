package network.devandroid.com.networklibrarycomparison.internal;

public abstract class BaseNetworkManager<T> implements INetworkManager<T> {

    protected Callback mCallback;

    public BaseNetworkManager(Callback mCallback) {
        this.mCallback = mCallback;
    }
}
