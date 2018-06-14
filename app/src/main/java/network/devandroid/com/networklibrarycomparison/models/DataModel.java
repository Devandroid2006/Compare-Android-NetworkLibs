package network.devandroid.com.networklibrarycomparison.models;

import network.devandroid.com.networklibrarycomparison.internal.NetType;

public class DataModel {

    private long mStartTime;

    private long mEndTime;

    private NetType mNetType;

    private String mUrl;

    private String mResult;

    public String getmResult() {
        return mResult;
    }

    public void setmResult(String mResult) {
        this.mResult = mResult;
    }

    public long getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public NetType getmNetType() {
        return mNetType;
    }

    public void setmNetType(NetType mNetType) {
        this.mNetType = mNetType;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

}
