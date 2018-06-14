package network.devandroid.com.networklibrarycomparison;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        mUnbinder = ButterKnife.bind(this);

        //setup toolbar
        final Toolbar toolBar = getToolBar();
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            toolBar.setNavigationIcon(R.drawable.ic_nav_back);
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //default call backpress
                    onBackPressed();
                }
            });
        }

        //force orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract Toolbar getToolBar();
}