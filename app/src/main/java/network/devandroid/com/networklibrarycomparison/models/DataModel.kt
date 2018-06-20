package network.devandroid.com.networklibrarycomparison.models

import network.devandroid.com.networklibrarycomparison.internal.NetType

class DataModel {

    private var mStartTime: Long = 0

    private var mEndTime: Long = 0

    private var mNetType: NetType? = null

    private var mUrl: String? = null

    private var mResult: String? = null

    fun getmResult(): String? {
        return mResult
    }

    fun setmResult(mResult: String) {
        this.mResult = mResult
    }

    fun getmStartTime(): Long {
        return mStartTime
    }

    fun setmStartTime(mStartTime: Long) {
        this.mStartTime = mStartTime
    }

    fun getmEndTime(): Long {
        return mEndTime
    }

    fun setmEndTime(mEndTime: Long) {
        this.mEndTime = mEndTime
    }

    fun getmNetType(): NetType? {
        return mNetType
    }

    fun setmNetType(mNetType: NetType) {
        this.mNetType = mNetType
    }

    fun getmUrl(): String? {
        return mUrl
    }

    fun setmUrl(mUrl: String) {
        this.mUrl = mUrl
    }

}
