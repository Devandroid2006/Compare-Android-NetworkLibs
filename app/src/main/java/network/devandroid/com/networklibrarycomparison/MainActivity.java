package network.devandroid.com.networklibrarycomparison;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import network.devandroid.com.networklibrarycomparison.internal.DataAdapter;
import network.devandroid.com.networklibrarycomparison.internal.INetworkManager;
import network.devandroid.com.networklibrarycomparison.internal.NetFactory;
import network.devandroid.com.networklibrarycomparison.internal.NetType;
import network.devandroid.com.networklibrarycomparison.models.DataModel;

public class MainActivity extends AppCompatActivity {

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

    @BindView(R.id.details_tview)
    AppCompatTextView mDetailsTview;

    @BindView(R.id.content_tview)
    AppCompatTextView mContentTview;

    //used to hold data model for each request
    private final List<DataModel> mList = Collections.synchronizedList(new ArrayList<DataModel>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);

        mSpinnerNetwork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
                //reset the list
                mList.clear();
                mContentTview.setText("");
                mDetailsTview.setText("");
                updateList();
                final NetType type = NetType.getNetType(position);
                switch (type) {
                    case ALL:
                        handleAllNetTypes();
                        break;
                    case VOLLEY:
                        for (int i = 0; i < getNumberOfIterations(); i++) {
                            handleVolleyNetType();
                        }
                        break;
                    case RETROFIT:
                        for (int i = 0; i < getNumberOfIterations(); i++) {
                            handleRetrofitNetType();
                        }
                        break;
                    case OK_HTTP:
                        for (int i = 0; i < getNumberOfIterations(); i++) {
                            handleOkHttpNetType();
                        }
                        break;
                    case FAST_NETWORK:
                        for (int i = 0; i < getNumberOfIterations(); i++) {
                            handleFastNetType();
                        }
                        break;
                    case ASYNC_TASK:
                        for (int i = 0; i < getNumberOfIterations(); i++) {
                            handleAsyncTaskNetType();
                        }
                        break;
                    case RXJAVA:
                        for (int i = 0; i < getNumberOfIterations(); i++) {
                            handleRxJavaNetType();
                        }
                        break;
                    default:
                        //do nothing
                        break;
                }
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
      /*  runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(mContentTview.getText())) {
                    mContentTview.setText(response.toString());
                }
            }
        });*/
        if (isAllNetworkApisFinished()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  /*  if (TextUtils.isEmpty(mContentTview.getText())) {
                        mContentTview.setText(response.toString());
                    }*/
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
                    //prepare the string
                    final Set<NetType> keys = avgTimeMap.keySet();
                    final StringBuilder sb = new StringBuilder();
                    for (NetType type : keys) {
                        sb.append(type.name()).append("       : ").append((avgTimeMap.get(type) / getNumberOfIterations())).append("(Avg Time)").append("\n\n");
                    }
                    mDetailsTview.setText(sb.toString());
                }
            });
        }

    }

    private synchronized boolean isAllNetworkApisFinished() {
        for (DataModel dataModel : mList) {
            if (dataModel.getmEndTime() == 0) {
                return false;
            }
        }
        return true;
    }

    private void handleAllNetTypes() {
        for (int i = 0; i < getNumberOfIterations(); i++) {
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

    private int getNumberOfIterations() {
        int value = 1;
        if (!TextUtils.isEmpty(mTestsEtext.getText())) {
            value = Integer.parseInt(mTestsEtext.getText().toString());
        }
        return value;
    }

    private void updateList() {
        if (!isFinishing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
                    if (null != adapter) {
                        DataAdapter dataAdapter = (DataAdapter) adapter;
                        dataAdapter.updateList(mList);
                    } else {
                        DataAdapter dataAdapter = new DataAdapter(mList);
                        mRecyclerView.setAdapter(dataAdapter);
                    }
                }
            });
        }

    }

}
