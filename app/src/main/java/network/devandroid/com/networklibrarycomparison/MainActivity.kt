package network.devandroid.com.networklibrarycomparison

import android.os.Bundle
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast

import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashMap

import butterknife.BindView
import network.devandroid.com.networklibrarycomparison.internal.DataAdapter
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager
import network.devandroid.com.networklibrarycomparison.internal.NetFactory
import network.devandroid.com.networklibrarycomparison.internal.NetType
import network.devandroid.com.networklibrarycomparison.internal.TestResultsAdapter
import network.devandroid.com.networklibrarycomparison.models.DataModel
import network.devandroid.com.networklibrarycomparison.utils.Validations

class MainActivity : BaseActivity() {

    @BindView(R.id.toolBar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.urlEtext)
    lateinit var mUrlEtext: AppCompatEditText

    @BindView(R.id.testsEtext)
    lateinit var mTestsEtext: AppCompatEditText

    @BindView(R.id.spinner_network)
    lateinit var mSpinnerNetwork: AppCompatSpinner

    @BindView(R.id.recyclerView)
    lateinit var mRecyclerView: RecyclerView

    @BindView(R.id.results_rcview)
    lateinit var mResultsRcView: RecyclerView

    //used to hold data model for each request
    private val mList = Collections.synchronizedList(ArrayList<DataModel>())

    protected override val layoutResource: Int
        get() = R.layout.activity_main

    override val getToolBar: Toolbar
        get() = toolbar

    private val isAllNetworkApisFinished: Boolean
        @Synchronized get() {
            for (dataModel in mList) {
                if (dataModel.getmEndTime() <= 0) {
                    return false
                }
            }
            return true
        }

    private val url: String
        get() = mUrlEtext!!.text.toString()

    private val numberOfTests: Int
        get() {
            var value = 1
            if (!TextUtils.isEmpty(mTestsEtext!!.text)) {
                value = Integer.parseInt(mTestsEtext!!.text.toString())
            }
            return value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //remove the navigation icon
        toolbar!!.navigationIcon = null

        mSpinnerNetwork!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.d(TAG, "onItemSelected() called with: parent = [$parent], view = [$view], position = [$position], id = [$id]")
                performSelectedNetworkTest(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d(TAG, "onNothingSelected() called with: parent = [$parent]")
            }
        }
        //set the default base url and tests
        mUrlEtext!!.setText(ICommonConstants.URL)
        mTestsEtext!!.setText(1.toString())

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_details ->
                //start new activity
                ShowDetailsActivity.launch(this)
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun performSelectedNetworkTest(position: Int) {
        if (!Validations.isValidUrl(mUrlEtext!!.text.toString())) {
            Toast.makeText(this@MainActivity, R.string.err_invalid_url, Toast.LENGTH_SHORT).show()
            return
        }
        //reset the list
        mList.clear()
        updateList()
        val type = NetType.getNetType(position)
        when (type) {
            NetType.ALL -> handleAllNetTypes()
            NetType.VOLLEY -> for (i in 0 until numberOfTests) {
                handleVolleyNetType()
            }
            NetType.RETROFIT -> for (i in 0 until numberOfTests) {
                handleRetrofitNetType()
            }
            NetType.OK_HTTP -> for (i in 0 until numberOfTests) {
                handleOkHttpNetType()
            }
            NetType.FAST_NETWORK -> for (i in 0 until numberOfTests) {
                handleFastNetType()
            }
            NetType.ASYNC_TASK -> for (i in 0 until numberOfTests) {
                handleAsyncTaskNetType()
            }
            NetType.RXJAVA -> for (i in 0 until numberOfTests) {
                handleRxJavaNetType()
            }
            else -> {
            }
        }//do nothing
    }

    private fun handleRxJavaNetType() {
        val dataModel = DataModel()
        dataModel.setmStartTime(System.currentTimeMillis())
        dataModel.setmNetType(NetType.RXJAVA)
        NetFactory.getNetworkManager(this, NetType.RXJAVA, object : INetworkManager.Callback {
            override fun onResponse(response: String) {
                Log.d(TAG, "onResponse() called with: response = [$response]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("success")
                mList.add(dataModel)
                updateList()
                updateTheBottomSheeContent(response)
            }

            override fun onError(error: String) {
                Log.d(TAG, "onError() called with: error = [$error]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("fail")
                mList.add(dataModel)
                updateList()
            }
        })!!.send(url)
    }

    private fun handleAsyncTaskNetType() {
        val dataModel = DataModel()
        dataModel.setmStartTime(System.currentTimeMillis())
        dataModel.setmNetType(NetType.ASYNC_TASK)
        NetFactory.getNetworkManager(applicationContext, NetType.ASYNC_TASK, object : INetworkManager.Callback {
            override fun onResponse(response: String) {
                Log.d(TAG, "onResponse() called with: response = [$response]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("success")
                mList.add(dataModel)
                updateList()
                updateTheBottomSheeContent(response)
            }

            override fun onError(error: String) {
                Log.d(TAG, "onError() called with: error = [$error]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("fail")
                mList.add(dataModel)
                updateList()
            }
        })!!.send(url)
    }

    private fun handleFastNetType() {
        val dataModel = DataModel()
        dataModel.setmStartTime(System.currentTimeMillis())
        dataModel.setmNetType(NetType.FAST_NETWORK)
        NetFactory.getNetworkManager(this, NetType.FAST_NETWORK, object : INetworkManager.Callback {
            override fun onResponse(response: String) {
                Log.d(TAG, "onResponse() called with: response = [$response]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("success")
                mList.add(dataModel)
                updateList()
                updateTheBottomSheeContent(response)
            }

            override fun onError(error: String) {
                Log.d(TAG, "onError() called with: error = [$error]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("fail")
                mList.add(dataModel)
                updateList()
            }
        })!!.send(url)
    }

    private fun handleOkHttpNetType() {
        val dataModel = DataModel()
        dataModel.setmStartTime(System.currentTimeMillis())
        dataModel.setmNetType(NetType.OK_HTTP)
        NetFactory.getNetworkManager(this, NetType.OK_HTTP, object : INetworkManager.Callback {
            override fun onResponse(response: String) {
                Log.d(TAG, "onResponse() called with: response = [$response]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("success")
                mList.add(dataModel)
                updateList()
                updateTheBottomSheeContent(response)
            }

            override fun onError(error: String) {
                Log.d(TAG, "onError() called with: error = [$error]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("fail")
                mList.add(dataModel)
                updateList()

            }
        })!!.send(url)
    }

    private fun handleRetrofitNetType() {
        val dataModel = DataModel()
        dataModel.setmStartTime(System.currentTimeMillis())
        dataModel.setmNetType(NetType.RETROFIT)
        NetFactory.getNetworkManager(this, NetType.RETROFIT, object : INetworkManager.Callback {
            override fun onResponse(response: String) {
                Log.d(TAG, "onResponse() called with: response = [$response]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("success")
                mList.add(dataModel)
                updateList()
                updateTheBottomSheeContent(response)
            }

            override fun onError(error: String) {
                Log.d(TAG, "onError() called with: error = [$error]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("fail")
                mList.add(dataModel)
                updateList()
            }
        })!!.send(ICommonConstants.PHOTOS_URL)
    }

    private fun handleVolleyNetType() {
        val dataModel = DataModel()
        dataModel.setmStartTime(System.currentTimeMillis())
        dataModel.setmNetType(NetType.VOLLEY)
        NetFactory.getNetworkManager(this, NetType.VOLLEY, object : INetworkManager.Callback {

            override fun onResponse(response: String) {
                Log.d(TAG, "onResponse() called with: response = [$response]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("success")
                mList.add(dataModel)
                updateList()
                updateTheBottomSheeContent(response)
            }

            override fun onError(error: String) {
                Log.d(TAG, "onError() called with: error = [$error]")
                dataModel.setmEndTime(System.currentTimeMillis())
                dataModel.setmResult("fail")
                mList.add(dataModel)
                updateList()
            }
        })!!.send(url)
    }

    private fun updateTheBottomSheeContent(response: String) {
        if (isFinishing) {
            return
        }
        if (isAllNetworkApisFinished) {
            //used to calculate the avege time for each type
            val avgTimeMap = LinkedHashMap<NetType, Long>()
            for (dataModel in mList) {
                if (dataModel.getmEndTime() == 0L || "Fail".equals(dataModel.getmResult()!!, ignoreCase = true)) {
                    //skip if end time is zero
                    continue
                }
                val netType: NetType= dataModel.getmNetType()!!

                val timeTaken:Long = dataModel.getmEndTime() - dataModel.getmStartTime()
                if (!avgTimeMap.containsKey(netType)) {
                    avgTimeMap[netType] = timeTaken
                } else {
                    avgTimeMap[netType] = timeTaken + avgTimeMap[netType]!!.toLong()
                }
            }

            //setup results
            val adapter = TestResultsAdapter(avgTimeMap.entries, numberOfTests)
            mResultsRcView!!.adapter = adapter
        }

    }

    private fun handleAllNetTypes() {
        for (i in 0 until numberOfTests) {
            handleAsyncTaskNetType()
            handleFastNetType()
            handleOkHttpNetType()
            handleRetrofitNetType()
            handleRxJavaNetType()
            handleVolleyNetType()
        }
    }

    private fun updateList() {
        if (!isFinishing) {
            val adapter = mRecyclerView!!.adapter
            if (null != adapter) {
                val dataAdapter = adapter as DataAdapter
                val sorted=mList!!.sortedWith(compareBy({ (it.getmEndTime() - it.getmStartTime()) }))
                dataAdapter.updateList(sorted)
            } else {
                val dataAdapter = DataAdapter(mList)
                mRecyclerView!!.adapter = dataAdapter
            }
        }

    }

    companion object {

        private val TAG = "MainActivity"
    }

}
