package network.devandroid.com.networklibrarycomparison.internal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import network.devandroid.com.networklibrarycomparison.R;
import network.devandroid.com.networklibrarycomparison.models.DataModel;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<DataModel> mList;

    public DataAdapter(List<DataModel> mList) {
        this.mList = mList;
    }

    public void updateList(List<DataModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel dataModel = mList.get(position);
        holder.mTitle.setText(dataModel.getmNetType().name());
        holder.mResult.setText(dataModel.getmResult());
        holder.mValue.setText(String.valueOf((dataModel.getmEndTime() - dataModel.getmStartTime())));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.value)
        TextView mValue;

        @BindView(R.id.result)
        TextView mResult;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
