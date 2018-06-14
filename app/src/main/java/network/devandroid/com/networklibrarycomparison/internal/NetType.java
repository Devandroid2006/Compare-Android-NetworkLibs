package network.devandroid.com.networklibrarycomparison.internal;

import java.util.HashMap;
import java.util.Map;

public enum NetType {
    NONE,
    ALL,
    VOLLEY,
    RETROFIT,
    OK_HTTP,
    FAST_NETWORK,
    ASYNC_TASK,
    RXJAVA;

    private final static Map<Integer, NetType> mMap;

    static {
        mMap = new HashMap<>();
        for (NetType type : NetType.values()) {
            mMap.put(type.ordinal(), type);
        }
    }

    public static NetType getNetType(int position) {
        return mMap.get(position);
    }

}
