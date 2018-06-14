package network.devandroid.com.networklibrarycomparison.internal;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import network.devandroid.com.networklibrarycomparison.R;

public class TestResultsAdapter extends RecyclerView.Adapter<TestResultsAdapter.ViewHolder> {

    private List<Map.Entry<NetType, Long>> mEntries;

    public TestResultsAdapter(Set<Map.Entry<NetType, Long>> entries) {
        this.mEntries = new ArrayList<>(entries);
        //sort
        Collections.sort(mEntries, new Comparator<Map.Entry<NetType, Long>>() {
            @Override
            public int compare(Map.Entry<NetType, Long> left, Map.Entry<NetType, Long> right) {
                if (left.getValue() > right.getValue()) {
                    return 1;
                } else if (left.getValue() < right.getValue()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_details_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Map.Entry<NetType, Long> entry = mEntries.get(position);
        if (position == 0) {
            holder.mRootView.setBackgroundColor(Color.GREEN);
        } else if (position % 2 == 0) {
            holder.mRootView.setBackgroundColor(Color.WHITE);
        } else {
            holder.mRootView.setBackgroundColor(Color.YELLOW);
        }
        holder.mResult.setText(":");
        holder.mTitle.setText(entry.getKey().name());
        holder.mValue.setText(String.valueOf(entry.getValue()));
    }

    @Override
    public int getItemCount() {
        return mEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.result)
        TextView mResult;

        @BindView(R.id.value)
        TextView mValue;


        @BindView(R.id.root_view)
        View mRootView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
