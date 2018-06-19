package network.devandroid.com.networklibrarycomparison.internal

import java.util.HashMap

enum class NetType {
    NONE,
    ALL,
    VOLLEY,
    RETROFIT,
    OK_HTTP,
    FAST_NETWORK,
    ASYNC_TASK,
    RXJAVA;


    companion object {

        private val mMap: MutableMap<Int, NetType>

        init {
            mMap = HashMap()
            for (type in NetType.values()) {
                mMap[type.ordinal] = type
            }
        }

        fun getNetType(position: Int): NetType {
            return mMap[position]
        }
    }

}
