package network.devandroid.com.networklibrarycomparison;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import network.devandroid.com.networklibrarycomparison.internal.DataAdapter;
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager;
import network.devandroid.com.networklibrarycomparison.internal.NetFactory;
import network.devandroid.com.networklibrarycomparison.internal.NetType;
import network.devandroid.com.networklibrarycomparison.internal.TestResultsAdapter;
import network.devandroid.com.networklibrarycomparison.models.DataModel;
import network.devandroid.com.networklibrarycomparison.utils.Validations;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.urlEtext)
    AppCompatEditText mUrlEtext;

    @BindView(R.id.testsEtext)
    AppCompatEditText mTestsEtext;

    @BindView(R.id.spinner_network)
    AppCompatSpinner mSpinnerNetwork;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.results_rcview)
    RecyclerView mResultsRcView;

    //used to hold data model for each request
    private final List<DataModel> mList = Collections.synchronizedList(new ArrayList<DataModel>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove the navigation icon
        mToolBar.setNavigationIcon(null);

        mSpinnerNetwork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
                performSelectedNetworkTest(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected() called with: parent = [" + parent + "]");
            }
        });
        //set the default base url and tests
        mUrlEtext.setText(ICommonConstants.URL);
        mTestsEtext.setText(String.valueOf(1));

        checkForUpdates();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolBar() {
        return mToolBar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_details:
                //start new activity
                ShowDetailsActivity.launch(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performSelectedNetworkTest(final int position) {
        if (!Validations.isValidUrl(mUrlEtext.getText().toString())) {
            Toast.makeText(MainActivity.this, R.string.err_invalid_url, Toast.LENGTH_SHORT).show();
            return;
        }
        //reset the list
        mList.clear();
        updateList();
        final NetType type = NetType.getNetType(position);
        switch (type) {
            case ALL:
                handleAllNetTypes();
                break;
            case VOLLEY:
                for (int i = 0; i < getNumberOfTests(); i++) {
                    handleVolleyNetType();
                }
                break;
            case RETROFIT:
                for (int i = 0; i < getNumberOfTests(); i++) {
                    handleRetrofitNetType();
                }
                break;
            case OK_HTTP:
                for (int i = 0; i < getNumberOfTests(); i++) {
                    handleOkHttpNetType();
                }
                break;
            case FAST_NETWORK:
                for (int i = 0; i < getNumberOfTests(); i++) {
                    handleFastNetType();
                }
                break;
            case ASYNC_TASK:
                for (int i = 0; i < getNumberOfTests(); i++) {
                    handleAsyncTaskNetType();
                }
                break;
            case RXJAVA:
                for (int i = 0; i < getNumberOfTests(); i++) {
                    handleRxJavaNetType();
                }
                break;
            default:
                //do nothing
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void handleRxJavaNetType() {
        final DataModel dataModel = new DataModel();
        dataModel.setmStartTime(System.currentTimeMillis());
        dataModel.setmNetType(NetType.RXJAVA);
        NetFactory.getNetworkManager(this, NetType.RXJAVA, new INetworkManager.Callback() {
            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("success");
                mList.add(dataModel);
                updateList();
                updateTheBottomSheeContent(response);
            }

            @Override
            public void onError(Object error) {
                Log.d(TAG, "onError() called with: error = [" + error + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("fail");
                mList.add(dataModel);
                updateList();
            }
        }).send(getUrl());
    }

    private void handleAsyncTaskNetType() {
        final DataModel dataModel = new DataModel();
        dataModel.setmStartTime(System.currentTimeMillis());
        dataModel.setmNetType(NetType.ASYNC_TASK);
        NetFactory.getNetworkManager(getApplicationContext(), NetType.ASYNC_TASK, new INetworkManager.Callback() {
            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("success");
                mList.add(dataModel);
                updateList();
                updateTheBottomSheeContent(response);
            }

            @Override
            public void onError(Object error) {
                Log.d(TAG, "onError() called with: error = [" + error + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("fail");
                mList.add(dataModel);
                updateList();
            }
        }).send(getUrl());
    }

    private void handleFastNetType() {
        final DataModel dataModel = new DataModel();
        dataModel.setmStartTime(System.currentTimeMillis());
        dataModel.setmNetType(NetType.FAST_NETWORK);
        NetFactory.getNetworkManager(this, NetType.FAST_NETWORK, new INetworkManager.Callback() {
            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("success");
                mList.add(dataModel);
                updateList();
                updateTheBottomSheeContent(response);
            }

            @Override
            public void onError(Object error) {
                Log.d(TAG, "onError() called with: error = [" + error + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("fail");
                mList.add(dataModel);
                updateList();
            }
        }).send(getUrl());
    }

    private void handleOkHttpNetType() {
        final DataModel dataModel = new DataModel();
        dataModel.setmStartTime(System.currentTimeMillis());
        dataModel.setmNetType(NetType.OK_HTTP);
        NetFactory.getNetworkManager(this, NetType.OK_HTTP, new INetworkManager.Callback() {
            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("success");
                mList.add(dataModel);
                updateList();
                updateTheBottomSheeContent(response);
            }

            @Override
            public void onError(Object error) {
                Log.d(TAG, "onError() called with: error = [" + error + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("fail");
                mList.add(dataModel);
                updateList();

            }
        }).send(getUrl());
    }

    private void handleRetrofitNetType() {
        final DataModel dataModel = new DataModel();
        dataModel.setmStartTime(System.currentTimeMillis());
        dataModel.setmNetType(NetType.RETROFIT);
        NetFactory.getNetworkManager(this, NetType.RETROFIT, new INetworkManager.Callback() {
            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("success");
                mList.add(dataModel);
                updateList();
                updateTheBottomSheeContent(response);
            }

            @Override
            public void onError(Object error) {
                Log.d(TAG, "onError() called with: error = [" + error + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("fail");
                mList.add(dataModel);
                updateList();
            }
        }).send(ICommonConstants.PHOTOS_URL);
    }

    private void handleVolleyNetType() {
        final DataModel dataModel = new DataModel();
        dataModel.setmStartTime(System.currentTimeMillis());
        dataModel.setmNetType(NetType.VOLLEY);
        NetFactory.getNetworkManager(this, NetType.VOLLEY, new INetworkManager.Callback() {

            @Override
            public void onResponse(Object response) {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("success");
                mList.add(dataModel);
                updateList();
                updateTheBottomSheeContent(response);
            }

            @Override
            public void onError(Object error) {
                Log.d(TAG, "onError() called with: error = [" + error + "]");
                dataModel.setmEndTime(System.currentTimeMillis());
                dataModel.setmResult("fail");
                mList.add(dataModel);
                updateList();
            }
        }).send(getUrl());
    }

    private void updateTheBottomSheeContent(final Object response) {
        if (isFinishing()) {
            return;
        }
        if (isAllNetworkApisFinished()) {
            //used to calculate the avege time for each type
            final Map<NetType, Long> avgTimeMap = new LinkedHashMap<>();
            for (DataModel dataModel : mList) {
                if (dataModel.getmEndTime() == 0 || "Fail".equalsIgnoreCase(dataModel.getmResult())) {
                    //skip if end time is zero
                    continue;
                }
                final NetType netType = dataModel.getmNetType();
                final long timeTaken = dataModel.getmEndTime() - dataModel.getmStartTime();
                if (!avgTimeMap.containsKey(netType)) {
                    avgTimeMap.put(netType, timeTaken);
                } else {
                    avgTimeMap.put(netType, timeTaken + avgTimeMap.get(netType));
                }
            }

            //setup results
            final TestResultsAdapter adapter = new TestResultsAdapter(avgTimeMap.entrySet(), getNumberOfTests());
            mResultsRcView.setAdapter(adapter);
        }

    }

    private synchronized boolean isAllNetworkApisFinished() {
        for (DataModel dataModel : mList) {
            if (dataModel.getmEndTime() <= 0) {
                return false;
            }
        }
        return true;
    }

    private void handleAllNetTypes() {
        for (int i = 0; i < getNumberOfTests(); i++) {
            handleAsyncTaskNetType();
            handleFastNetType();
            handleOkHttpNetType();
            handleRetrofitNetType();
            handleRxJavaNetType();
            handleVolleyNetType();
        }
    }

    private String getUrl() {
        return mUrlEtext.getText().toString();
    }

    private int getNumberOfTests() {
        int value = 1;
        if (!TextUtils.isEmpty(mTestsEtext.getText())) {
            value = Integer.parseInt(mTestsEtext.getText().toString());
        }
        return value;
    }

    private void updateList() {
        if (!isFinishing()) {
            final RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            if (null != adapter) {
                DataAdapter dataAdapter = (DataAdapter) adapter;
                dataAdapter.updateList(mList);
            } else {
                DataAdapter dataAdapter = new DataAdapter(mList);
                mRecyclerView.setAdapter(dataAdapter);
            }
        }

    }

}
